package kmg.tool.application.model.jdts.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import kmg.core.infrastructure.exception.KmgMsgException;
import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.tool.application.model.jdts.JdtsBlockModel;
import kmg.tool.domain.model.JavadocModel;
import kmg.tool.domain.model.impl.JavadocModelImpl;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

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
public class JdtsBlockModelImpl implements JdtsBlockModel {

    /** Javadocブロック終了文字列 */
    private static final String JAVADOC_END = "*/";

    /** アノテーション開始文字 */
    private static final String ANNOTATION_START = "@";

    /** 改行文字の正規表現 */
    private static final String LINE_SEPARATOR_REGEX = "\\R";

    /** Javadocブロック分割用の正規表現 */
    private static final String JAVADOC_BLOCK_SPLIT_REGEX
        = String.format("%s\\s+", Pattern.quote(JdtsBlockModelImpl.JAVADOC_END));

    /** 識別子 */
    private final UUID id;

    /** オリジナルブロック */
    private final String orgBlock;

    /** Javadocモデル */
    private JavadocModel javadocModel;

    /** アノテーションリスト */
    private final List<String> annotations;

    /** コードブロック */
    private String codeBlock;

    /** 区分 */
    private JavaClassificationTypes classification;

    /** 要素名 */
    private String elementName;

    /**
     * コンストラクタ
     *
     * @param block
     *              ブロック。「/**」で分割されたブロック。
     */
    public JdtsBlockModelImpl(final String block) {

        this.orgBlock = block;

        this.id = UUID.randomUUID();

        this.annotations = new ArrayList<>();

        this.classification = JavaClassificationTypes.NONE;

    }

    /**
     * アノテーションリストを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return アノテーションリスト
     */
    @Override
    public List<String> getAnnotations() {

        final List<String> result = this.annotations;
        return result;

    }

    /**
     * 区分を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 区分
     */
    @Override
    public JavaClassificationTypes getClassification() {

        final JavaClassificationTypes result = this.classification;
        return result;

    }

    /**
     * 要素名を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 要素名
     */
    @Override
    public String getElementName() {

        final String result = this.elementName;
        return result;

    }

    /**
     * 識別子を返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return 識別子
     */
    @Override
    public UUID getId() {

        final UUID result = this.id;
        return result;

    }

    /**
     * Javadocモデルを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return Javadocモデル
     */
    @Override
    public JavadocModel getJavadocModel() {

        final JavadocModel result = this.javadocModel;
        return result;

    }

    /**
     * オリジナルブロックを返す<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return オリジナルブロック
     */
    @Override
    public String getOrgBlock() {

        final String result = this.orgBlock;
        return result;

    }

    /**
     * 解析する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    @Override
    public boolean parse() throws KmgToolMsgException {

        boolean result = false;

        /* オリジナルブロックをJavadocとコードブロックに分ける */

        // 「*/」でJavadocとCodeのブロックに分ける
        final String[] javadocCodeBlock = this.orgBlock.split(JdtsBlockModelImpl.JAVADOC_BLOCK_SPLIT_REGEX, 2);

        /* Javadoc部分をJavadocモデルに変換する */
        this.javadocModel = new JavadocModelImpl(javadocCodeBlock[0]);

        /* コード行の配列にする */
        final String[] codeLines = javadocCodeBlock[1].split(JdtsBlockModelImpl.LINE_SEPARATOR_REGEX);

        /* アノテーションを設定する */

        // コードセクションの開始
        int codeSectionStartIdx = 0;

        for (int i = 0; i < codeLines.length; i++) {

            final String line = codeLines[i];

            if (KmgString.isBlank(line)) {

                continue;

            }

            final String trimmedLine = line.trim();

            if (!trimmedLine.startsWith(JdtsBlockModelImpl.ANNOTATION_START)) {

                codeSectionStartIdx = i;

                break;

            }

            this.annotations.add(trimmedLine);

        }

        /* コードブロックを設定する */
        final StringBuilder wkCodeBlock = new StringBuilder();

        for (int i = codeSectionStartIdx; i < codeLines.length; i++) {

            final String line = codeLines[i];

            if (KmgString.isBlank(line)) {

                continue;

            }

            final String trimmedLine = line.trim();
            wkCodeBlock.append(trimmedLine).append(System.lineSeparator());

        }

        this.codeBlock = wkCodeBlock.toString();

        /* 区分を特定する */
        this.specifyClassification();

        result = true;
        return result;

    }

    /**
     * 区分を特定する<br>
     *
     * @author KenichiroArai
     *
     * @sine 0.1.0
     *
     * @return true：区分が特定できた、false：区分がNONE
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean specifyClassification() throws KmgToolMsgException {

        boolean result = false;

        // コードを行ごとに取得する。空行は除外する。
        final String[] codeLines = Arrays.stream(KmgDelimiterTypes.LINE_SEPARATOR.split(this.codeBlock))
            .filter(KmgString::isNotBlank).toArray(String[]::new);

        for (final String codeLine : codeLines) {

            // Java区分を判別
            this.classification = JavaClassificationTypes.identify(codeLine);

            // Javadoc対象外か
            if (this.classification.isNotJavadocTarget()) {
                // 対象外の場合

                continue;

            }

            // 要素名を取得
            try {

                this.elementName = this.classification.getElementName(codeLine);

            } catch (final KmgMsgException e) {

                // TODO KenichiroArai 2025/05/08 例外処理
                final KmgToolGenMessageTypes msgTypes = KmgToolGenMessageTypes.NONE;
                final Object[]               msgArgs  = {};

                throw new KmgToolMsgException(msgTypes, msgArgs, e);

            }

            result = true;
            break;

        }

        return result;

    }

}
