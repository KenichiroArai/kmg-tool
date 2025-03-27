package kmg.tool.application.service;

import java.nio.file.Path;

import kmg.tool.domain.service.io.IoService;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加サービスインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface JavadocAppenderService extends IoService {

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param inputPath
     *                     入力ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean initialize(final Path inputPath, final Path templatePath) throws KmgToolException;

}
