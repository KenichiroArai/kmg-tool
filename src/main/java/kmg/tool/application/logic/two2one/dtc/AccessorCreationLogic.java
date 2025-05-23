package kmg.tool.application.logic.two2one.dtc;

import kmg.tool.domain.logic.two2one.IctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * アクセサ作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 */
public interface AccessorCreationLogic extends IctoOneLinePatternLogic {

    /**
     * 項目名を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addItemToCsvRows() throws KmgToolMsgException;

    /**
     * Javadocコメントを書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addJavadocCommentToCsvRows() throws KmgToolMsgException;

    /**
     * 型情報を書き込み対象に追加する。
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addTypeToCsvRows() throws KmgToolMsgException;

    /**
     * フィールド宣言から型、項目名、先頭大文字項目に変換する。
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean convertFields() throws KmgToolMsgException;

    /**
     * Javadocの変換を行う。
     *
     * @return true：変換あり、false：変換なし
     */
    boolean convertJavadoc();

    /**
     * 項目名返す。
     *
     * @return 項目名
     */
    String getItem();

    /**
     * Javadocコメントを返す。
     *
     * @return Javadocコメント
     */
    String getJavadocComment();

    /**
     * 型を返す。
     *
     * @return 型
     */
    String getTyep();

    /**
     * Javadocの解析中かを返す。
     *
     * @return true：Javadoc解析中、false：Javadoc解析外
     */
    boolean isInJavadocParsing();

    /**
     * 不要な修飾子を削除する。
     *
     * @return true：成功、false：失敗
     */
    boolean removeModifier();

}
