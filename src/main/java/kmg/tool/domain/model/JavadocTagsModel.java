package kmg.tool.domain.model;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Javadocタグ一覧情報<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public interface JavadocTagsModel {

    /** タグ全体のグループインデックス */
    int GROUP_INDEX_WHOLE = 0;

    /** タグ名のグループインデックス */
    int GROUP_INDEX_TAG_NAME = 1;

    /** タグ値のグループインデックス */
    int GROUP_INDEX_VALUE = 2;

    /** タグ説明のグループインデックス */
    int GROUP_INDEX_DESCRIPTION = 3;

    /**
     * Javadocタグを抽出する正規表現パターン
     *
     * @since 0.1.0
     */
    String TAG_PATTERN = "\\s+\\*\\s+(@\\w+)\\s+([^\\s\\n]+)(?:\\s+([^@\\n]+))?";

    /**
     * コンパイル済みのJavadocタグパターン
     *
     * @since 0.1.0
     */
    Pattern COMPILED_TAG_PATTERN = Pattern.compile(JavadocTagsModel.TAG_PATTERN);

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @return Javadocタグモデルのリスト
     */
    List<JavadocTagModel> getJavadocTagModelList();

}
