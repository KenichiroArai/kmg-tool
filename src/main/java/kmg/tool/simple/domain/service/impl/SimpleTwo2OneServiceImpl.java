package kmg.tool.simple.domain.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.cmn.infrastructure.exception.KmgToolMsgException;
import kmg.tool.cmn.infrastructure.types.KmgToolGenMsgTypes;
import kmg.tool.simple.application.service.SimpleTwo2OneService;

/**
 * シンプル2入力ファイルから1出力ファイルへの変換ツールサービス<br>
 */
@Service
public class SimpleTwo2OneServiceImpl implements SimpleTwo2OneService {

    /** テンプレート置換用プレースホルダー */
    private static final String TEMPLATE_NAME_PLACEHOLDER = "{ name }"; //$NON-NLS-1$

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
     * @since 1.0.0
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
     * @since 1.0.0
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
     * @since 1.0.0
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
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean process() throws KmgToolMsgException {

        boolean result = false;

        /* テンプレートの取得 */
        String template = null;

        try {

            template = Files.readAllLines(this.templatePath).stream()
                .collect(Collectors.joining(KmgDelimiterTypes.LINE_SEPARATOR.get()));

        } catch (final IOException e) {

            // 例外をスローする
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN16001;
            final Object[]           messageArgs = {
                this.templatePath.toString()
            };
            throw new KmgToolMsgException(msgType, messageArgs, e);

        }

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(this.inputPath);
            final BufferedWriter bw = Files.newBufferedWriter(this.outputPath);) {

            final StringBuilder output = new StringBuilder();

            String line = null;

            while ((line = brInput.readLine()) != null) {

                final String wk = template.replace(SimpleTwo2OneServiceImpl.TEMPLATE_NAME_PLACEHOLDER, line);
                output.append(wk);
                output.append(KmgDelimiterTypes.LINE_SEPARATOR.get());

            }

            bw.write(output.toString());

        } catch (final IOException e) {

            // 例外をスローする
            final KmgToolGenMsgTypes msgType     = KmgToolGenMsgTypes.KMGTOOL_GEN16000;
            final Object[]           messageArgs = {};
            throw new KmgToolMsgException(msgType, messageArgs, e);

        }

        result = true;
        return result;

    }

}
