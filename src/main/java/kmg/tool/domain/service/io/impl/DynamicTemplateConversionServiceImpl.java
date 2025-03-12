package kmg.tool.domain.service.io.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.domain.service.io.DynamicTemplateConversionService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換サービス
 */
@Service
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

        // テンプレートの読み込み
        String template = null;

        try {

            template = Files.readString(this.getTemplatePath());

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/06 例外メッセージ
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(msgType, messageArgs, e);

        }

        // YAML形式に変換
        final Yaml                yaml     = new Yaml();
        final Map<String, Object> yamlData = yaml.load(template);

        // プレースホルダー定義を取得する
        @SuppressWarnings("unchecked")
        final List<Map<String, String>> placeholderDefinitions
            = (List<Map<String, String>>) yamlData.get("placeholderDefinitions");

        final Map<String, String> columnMappings = new LinkedHashMap<>();

        for (final Map<String, String> placeholderMap : placeholderDefinitions) {

            // 表示名をキーとして、置換パターンを値として設定
            columnMappings.put(placeholderMap.get("displayName"), placeholderMap.get("replacementPattern"));

        }

        // テンプレート内容を取得する
        final String templateContent = (String) yamlData.get("templateContent");

        /* テンプレート内容をプレースホルダー定義に基づいて変換し、出力する */

        String line = null;

        try (final BufferedReader brInput = Files.newBufferedReader(this.getInputPath());
            final BufferedWriter bwOutput = Files.newBufferedWriter(this.getOutputPath());) {

            while ((line = brInput.readLine()) != null) {

                String out = templateContent;

                final String[] csvLine = KmgDelimiterTypes.COMMA.split(line);

                final String[] keyArrays = columnMappings.values().toArray(new String[0]);

                for (int i = 0; i < csvLine.length; i++) {

                    final String key   = keyArrays[i];
                    final String value = csvLine[i];

                    out = out.replace(key, value);

                }

                bwOutput.write(out);
                bwOutput.newLine();

            }

        } catch (final IOException e) {

            // 例外をスローする
            // TODO KenichiroArai 2025/03/07 メッセージ
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.NONE;
            final Object[]               messageArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolException(msgType, messageArgs, e);

        }

        result = true;

        return result;

    }

}
