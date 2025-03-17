package kmg.tool.presentation.ui.cli.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgDelimiterTypes;

/**
 * アクセサインタフェース作成ツール
 *
 * @author KenichiroArai
 */
public class AccessorInterfaceCreationlTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io"));

    /** テンプレートファイルパス */
    private static final Path TEMPLATE_PATH
        = Paths.get(AccessorInterfaceCreationlTool.BASE_PATH.toString(), "template/AccessorInterfaceCreationlTool.xml");

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(AccessorInterfaceCreationlTool.BASE_PATH.toString(), "input.txt");

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH
        = Paths.get(AccessorInterfaceCreationlTool.BASE_PATH.toString(), "output.txt");

    /**
     * エントリポイント
     *
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        final Class<AccessorInterfaceCreationlTool> clasz = AccessorInterfaceCreationlTool.class;

        try {

            final AccessorInterfaceCreationlTool main = new AccessorInterfaceCreationlTool();

            if (main.run()) {

                System.out.println(String.format("%s：失敗", clasz.toString()));

            }

        } catch (final Exception e) {

            e.printStackTrace();

        } finally {

            System.out.println(String.format("%s：成功", clasz.toString()));
            System.out.println(String.format("%s：終了", clasz.toString()));

        }

    }

    /**
     * 走る
     *
     * @return TRUE：成功、FLASE：失敗
     *
     * @throws FileNotFoundException
     *                               ファイルが存在しない例外
     * @throws IOException
     *                               入出力例外
     */
    @SuppressWarnings("static-method")
    public Boolean run() throws FileNotFoundException, IOException {

        final Boolean result = Boolean.FALSE;

        /* テンプレートの取得 */
        String template = null;

        try {

            template = Files.readAllLines(AccessorInterfaceCreationlTool.TEMPLATE_PATH).stream()
                .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

        } catch (final IOException e) {

            throw e;

        }

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(AccessorInterfaceCreationlTool.INPUT_PATH);
            final BufferedWriter bw = Files.newBufferedWriter(AccessorInterfaceCreationlTool.OUTPUT_PATH);) {

            String output = template;
            String line   = null;

            while ((line = brInput.readLine()) != null) {

                line = line.replace("final", KmgString.EMPTY);
                line = line.replace("static", KmgString.EMPTY);
                final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)");
                final Matcher matcherComment = patternComment.matcher(line);

                if (matcherComment.find()) {

                    output = output.replace("$name", matcherComment.group(1));
                    continue;

                }

                Pattern patternSrc = Pattern.compile("private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);");
                Matcher matcherSrc = patternSrc.matcher(line);

                if (matcherSrc.find()) {

                    output = output.replace("$type", matcherSrc.group(1));
                    output = output.replace("$item", matcherSrc.group(3));
                    output = output.replace("$Item",
                        matcherSrc.group(3).substring(0, 1).toUpperCase() + matcherSrc.group(3).substring(1));
                    bw.write(output);
                    bw.write(System.lineSeparator());
                    output = template;

                } else {

                    patternSrc = Pattern.compile("private\\s+(Map<\\w+,\\s+\\w+>)\\s+(\\w+);");
                    matcherSrc = patternSrc.matcher(line);

                    if (matcherSrc.find()) {

                        output = output.replace("$type", matcherSrc.group(1));
                        output = output.replace("$item", matcherSrc.group(2));
                        output = output.replace("$Item",
                            matcherSrc.group(2).substring(0, 1).toUpperCase() + matcherSrc.group(2).substring(1));
                        bw.write(output);
                        bw.write(System.lineSeparator());
                        output = template;

                    }

                }

            }

        } catch (final IOException e) {

            throw e;

        }

        return result;

    }

}
