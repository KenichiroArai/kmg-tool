package kmg.tool.application.model.jda;

import java.util.List;

import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定のコードモデルインタフェース<br>
 * <p>
 * Jdtsは、JavadocTagSetterの略。<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JdtsCodeModel {

    /**
     * Javadocタグ設定のブロックモデルリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグ設定のブロックモデルリスト
     */
    List<JdtsBlockModel> getJdtsBlockModels();

    /**
     * オリジナルコードを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return オリジナルコード
     */
    String getOrgCode();

    /**
     * 解析する
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    void parse() throws KmgToolException;

}
