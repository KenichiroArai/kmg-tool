package kmg.tool.application.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.utils.KmgPathUtils;
import kmg.tool.domain.service.Two2OneService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * 入力、CSV、テンプレート、出力の処理サービス抽象クラス
 */
public abstract class AbstractInputCsvTemplateOutputProcessorService implements Two2OneService {

    /** 入力ファイルパス */
    private Path inputPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /** 出力ファイルパス */
    private Path outputPath;

    /** CSVファイルパス */
    private Path csvPath;

    /** テンプレートの動的変換サービス */
    @Autowired
    private DynamicTemplateConversionService dynamicTemplateConversionService;

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
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath)
        throws KmgToolException {

        final boolean result = true;

        this.inputPath = inputPath;
        this.templatePath = templatePath;
        this.outputPath = outputPath;

        /* 一時ファイルの作成 */
        try {

            final String fileNameOnly = KmgPathUtils.getFileNameOnly(this.getInputPath());
            this.csvPath = Files.createTempFile(fileNameOnly, "Temp.csv");
            this.csvPath.toFile().deleteOnExit();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/06 メッセージ
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

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

        /* 入力から出力の処理 */
        final List<List<String>> csv = new ArrayList<>();

        /* 入力ファイルからCSV形式に変換してCSVファイルに出力する */
        try (final BufferedReader brInput = Files.newBufferedReader(this.getInputPath());
            final BufferedWriter brOutput = Files.newBufferedWriter(this.getCsvPath());) {

            String line = null;

            while ((line = brInput.readLine()) != null) {

                line = line.replace("final", KmgString.EMPTY);
                line = line.replace("static", KmgString.EMPTY);
                final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)");
                final Matcher matcherComment = patternComment.matcher(line);

                if (matcherComment.find()) {

                    final List<String> csvLine = new ArrayList<>();
                    csvLine.add(matcherComment.group(1));
                    csv.add(csvLine);

                    continue;

                }

                final Pattern patternSrc = Pattern.compile("private\\s+((\\w|\\[\\]|<|>)+)\\s+(\\w+);");
                final Matcher matcherSrc = patternSrc.matcher(line);

                if (!matcherSrc.find()) {

                    continue;

                }

                final List<String> csvLine = csv.getLast();
                csvLine.add(matcherSrc.group(1));
                csvLine.add(matcherSrc.group(3));
                csvLine.add(matcherSrc.group(3).substring(0, 1).toUpperCase() + matcherSrc.group(3).substring(1));

                brOutput.write(String.join(",", csvLine));
                brOutput.write(System.lineSeparator());

            }

            result = true;

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/06 メッセージ
            final KmgToolGenMessageTypes msgType = KmgToolGenMessageTypes.NONE;
            throw new KmgToolException(msgType, e);

        }

        this.dynamicTemplateConversionService.initialize(this.getCsvPath(), this.templatePath, this.outputPath);
        this.dynamicTemplateConversionService.process();

        return result;

    }

    /**
     * CSVファイルパスを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @version 1.0.0
     *
     * @return CSVファイルパス
     */
    protected Path getCsvPath() {

        final Path result = this.csvPath;
        return result;

    }

}
