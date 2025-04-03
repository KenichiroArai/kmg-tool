package kmg.tool.domain.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.KmgJavadocTagTypes;
import kmg.tool.domain.model.JavadocModel;
import kmg.tool.domain.model.JavadocTagModel;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadocモデル<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public class JavadocModelImpl implements JavadocModel {

    /** 元のJavadoc */
    private final String sourceJavadoc;

    /** Javadocタグモデルのリスト */
    private final List<JavadocTagModel> javadocTagModelList;

    /**
     * コンストラクタ<br>
     *
     * @param sourceJavadoc
     *                      Javadoc
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    public JavadocModelImpl(final String sourceJavadoc) throws KmgToolException {

        this.javadocTagModelList = new ArrayList<>();
        this.sourceJavadoc = sourceJavadoc;

        // @タグを抽出する正規表現パターン
        // グループ1: タグ名
        // グループ2: 値
        // グループ3: 説明（オプション）
        // TODO KenichiroArai 2025/04/03 ハードコード
        final String  pattern = "(@\\w+)\\s+([^\\s\\n]+)(?:\\s+([^@\\n]+))?";
        final Pattern p       = java.util.regex.Pattern.compile(pattern);
        final Matcher m       = p.matcher(sourceJavadoc);

        // TODO KenichiroArai 2025/04/03 デバッグ
        System.out.println("----- 元のJavadoc -----");

        while (m.find()) {

            // TODO KenichiroArai 2025/04/03 ハードコード
            final KmgJavadocTagTypes tag   = KmgJavadocTagTypes.getEnum(m.group(1));
            final String             value = m.group(2);

            // 説明取得
            final String description = Optional.ofNullable(m.group(3))
                .map(s -> s.trim().replaceFirst("^\\*", KmgString.EMPTY)).orElse(KmgString.EMPTY).trim();

            final JavadocTagModel javadocTagMode = new JavadocTagModelImpl(tag, value, description);
            this.javadocTagModelList.add(javadocTagMode);

            // TODO KenichiroArai 2025/04/03 デバッグ
            System.out.println(String.format("タグ: %s\n指定値: %s\n説明: %s", javadocTagMode.getTag(),
                javadocTagMode.getValue(), javadocTagMode.getDescription()));

        }

    }

    /**
     * Javadocタグモデルのリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocタグモデルのリスト
     */
    @Override
    public List<JavadocTagModel> getJavadocTagModelList() {

        final List<JavadocTagModel> result = this.javadocTagModelList;
        return result;

    }

    /**
     * 元のJavadocを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 元のJavadoc
     */
    @Override
    public String getSourceJavadoc() {

        final String result = this.sourceJavadoc;
        return result;

    }

}
