package kmg.tool.application.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.application.service.DynamicTemplateConversionService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換サービス
 */
public class DynamicTemplateConversionServiceImpl implements DynamicTemplateConversionService {

    /** 入力ファイルパス */
    private Path inputPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /** 出力ファイルパス */
    private Path outputPath;

    /**
     * 入力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 入力ファイルパス
     */
    @Override
    public Path getInputPath() {

        final Path result = this.inputPath;
        return result;

    }

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return 出力ファイルパス
     */
    @Override
    public Path getOutputPath() {

        final Path result = this.outputPath;
        return result;

    }

    /**
     * テンプレートファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return テンプレートファイルパス
     */
    @Override
    public Path getTemplatePath() {

        final Path result = this.templatePath;
        return result;

    }

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     * @param outputPath
     *                     出力ファイルパス
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) {

        final boolean result = true;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        return result;

    }

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        /* テンプレートの取得 */
        String                    template       = null;
        final Map<String, String> columnMappings = new HashMap<>();

        final Yaml yaml = new Yaml();

        String templateContent = null;

        try {

            // Pathオブジェクトから文字列内容を読み取る
            templateContent = Files.readString(this.getTemplatePath());

        } catch (final IOException e) {

            // 例外をスローする
            // TODO KenichiroArai 2025/03/06 メッセージ
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(msgType, messageArgs, e);

        }

        final Map<String, Object> yamlData = yaml.load(templateContent);

        @SuppressWarnings("unchecked")
        final List<Map<String, String>> columns = (List<Map<String, String>>) yamlData.get("columns");

        for (final Map<String, String> col : columns) {

            columnMappings.put(col.get("key"), col.get("placeholder"));

        }

        template = (String) yamlData.get("template");

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(this.inputPath);
            final BufferedWriter bw = Files.newBufferedWriter(this.outputPath);) {

            String output = template;
            String line   = null;

            while ((line = brInput.readLine()) != null) {

                final String[] keyArrays = columnMappings.values().toArray(new String[0]);

                line = line.replace("final", KmgString.EMPTY);
                line = line.replace("static", KmgString.EMPTY);
                final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)");
                final Matcher matcherComment = patternComment.matcher(line);

                if (matcherComment.find()) {

                    output = output.replace(keyArrays[0], matcherComment.group(1));
                    continue;

                }

                final Pattern patternSrc = Pattern.compile("private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);");
                final Matcher matcherSrc = patternSrc.matcher(line);

                if (!matcherSrc.find()) {

                    continue;

                }

                output = output.replace(keyArrays[1], matcherSrc.group(3));
                output = output.replace(keyArrays[2], matcherSrc.group(1));
                output = output.replace("{Item}",
                    matcherSrc.group(3).substring(0, 1).toUpperCase() + matcherSrc.group(3).substring(1));
                bw.write(output);
                bw.write(System.lineSeparator());
                output = template;

            }

            result = true;

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/06 メッセージ
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

        return result;

    }

}
