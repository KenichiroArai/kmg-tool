package kmg.tool.application.model.jda;

import java.util.List;
import java.util.UUID;

import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.domain.model.JavadocModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocタグ設定のブロックモデルインタフェース<br>
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
public interface JdtsBlockModel {

    /**
     * アノテーションリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return アノテーションリスト
     */
    List<String> getAnnotations();

    /**
     * 識別子を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 識別子
     */
    UUID getId();

    /**
     * Java区分を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Java区分
     */
    JavaClassificationTypes getJavaClassification();

    /**
     * Javadocモデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocモデル
     */
    JavadocModel getJavadocModel();

    /**
     * オリジナルブロックを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return オリジナルブロック
     */
    String getOrgBlock();

    /**
     * 解析する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    boolean parse() throws KmgToolException;

}
