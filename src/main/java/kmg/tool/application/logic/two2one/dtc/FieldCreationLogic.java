package kmg.tool.application.logic.two2one.dtc;

import kmg.tool.domain.logic.two2one.IctoOneLinePatternLogic;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * フィールド作成ロジックインターフェース
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public interface FieldCreationLogic extends IctoOneLinePatternLogic {

    /**
     * コメントを書き込み対象に追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addCommentToCsvRows() throws KmgToolMsgException;

    /**
     * フィールドを書き込み対象に追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addFieldToCsvRows() throws KmgToolMsgException;

    /**
     * 型を書き込み対象に追加する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean addTypeToCsvRows() throws KmgToolMsgException;

    /**
     * フィールドデータを変換する
     *
     * @return true：変換あり、false：変換なし
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    boolean convertFields() throws KmgToolMsgException;

    /**
     * コメントを返す
     *
     * @return コメント
     */
    String getComment();

    /**
     * フィールドを返す
     *
     * @return フィールド
     */
    String getField();

    /**
     * 型を返す
     *
     * @return 型
     */
    String getType();
}
