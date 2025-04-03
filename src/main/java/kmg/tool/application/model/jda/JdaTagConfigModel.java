package kmg.tool.application.model.jda;

import java.util.Map;

import kmg.core.infrastructure.types.KmgJavadocTagTypes;

/**
 * Javadoc追加のタグ設定モデル<br>
 * <p>
 * Jdaは、JavadocAppenderの略。
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JdaTagConfigModel {

    /**
     * タグの挿入位置を返す<br>
     *
     * @return タグの挿入位置
     */
    String getInsertPosition();

    /**
     * 配置場所の設定を返す<br>
     *
     * @return 配置場所の設定
     */
    Map<String, Object> getLocation();

    /**
     * 上書き設定を返す<br>
     *
     * @return 上書き設定
     */
    String getOverwrite();

    /**
     * タグを返す<br>
     *
     * @return タグ
     */
    KmgJavadocTagTypes getTag();

    /**
     * タグの値を返す<br>
     *
     * @return タグの値
     */
    String getText();
}
