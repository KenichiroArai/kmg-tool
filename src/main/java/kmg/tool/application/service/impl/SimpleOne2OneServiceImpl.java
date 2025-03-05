package kmg.tool.application.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.foundation.infrastructure.context.KmgMessageSource;
import kmg.tool.application.service.SimpleOne2OneService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * シンプル1入力ファイルから1出力ファイルへの変換ツールサービス<br>
 */
@Service
public class SimpleOne2OneServiceImpl implements SimpleOne2OneService {

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /** 入力ファイルパス */
    private Path inputPath;

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
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                   入力ファイルパス
     * @param outputPath
     *                   出力ファイルパス
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path inputPath, final Path outputPath) {

        final boolean result = true;

        this.inputPath = inputPath;
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

        /* 入力から出力の処理 */

        try (final BufferedReader brInput = Files.newBufferedReader(this.inputPath);
            final BufferedWriter bwOutput = Files.newBufferedWriter(this.outputPath);) {

            String line = null;

            while ((line = brInput.readLine()) != null) {

                bwOutput.write(line);

            }

        } catch (final IOException e) {

            // 例外をスローする
            final KmgToolGenMessageTypes msgType     = KmgToolGenMessageTypes.KMGTOOLGENI31000;
            final Object[]               messageArgs = {};
            throw new KmgToolException(msgType, messageArgs, e);

        }

        result = true;
        return result;

    }

}
