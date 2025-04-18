package kmg.tool.domain.logic.two2one.dtc;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * テンプレートの動的変換ロジックインタフェース<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * </p>
 *
 * @author KenichiroArai
 */
public interface DtcLogic extends Closeable {

    /**
     * 出力バッファに追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean addOutputBufferContent() throws KmgToolException;

    /**
     * 入力ファイルからテンプレートに基づいて変換する。
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @throws KmgToolException
     *                          入出力処理に失敗した場合
     */
    void applyTemplateToInputFile() throws KmgToolException;

    /**
     * 出力バッファコンテンツをクリアする
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    void clearOutputBufferContent() throws KmgToolException;

    /**
     * 読み込み中のデータをクリアする。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean clearReadingData() throws KmgToolException;

    /**
     * リソースをクローズする。
     *
     * @throws IOException
     *                     入出力例外
     */
    @Override
    void close() throws IOException;

    /**
     * 1件分の内容を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 1件分の内容
     */
    String getContentsOfOneItem();

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
    Path getInputPath();

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
    Path getOutputPath();

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
    Path getTemplatePath();

    /**
     * 初期化する
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
     *
     * @return true：成功、false：失敗
     */
    boolean initialize(final Path inputPath, final Path templatePath, final Path outputPath) throws KmgToolException;

    /**
     * テンプレートファイルを読み込む<br>
     *
     * @author KenichiroArai
     *
     * @sine 1.0.0
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          テンプレートの読み込みに失敗した場合
     */
    boolean loadTemplate() throws KmgToolException;

    /**
     * 1行データを読み込む。
     *
     * @return true：データあり、false：データなし
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean readOneLineOfData() throws KmgToolException;

    /**
     * 出力バッファを書き込む
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean writeOutputBuffer() throws KmgToolException;
}
