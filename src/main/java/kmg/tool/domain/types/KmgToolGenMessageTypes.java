package kmg.tool.domain.types;

import java.util.HashMap;
import java.util.Map;

import kmg.tool.infrastructure.common.msg.KmgToolComExcMsgTypes;
import kmg.tool.infrastructure.common.msg.KmgToolComGenMsgTypes;

/**
 * KMGツール一般メッセージの種類<br>
 *
 * @author KenichiroArai
 *
 * @since 0.1.0
 *
 * @version 0.1.0
 */
public enum KmgToolGenMessageTypes implements KmgToolComGenMsgTypes, KmgToolComExcMsgTypes {

    /* 定義：開始 */

    /**
     * 指定無し
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    NONE("指定無し"),

    /**
     * 一時的なCSVファイルの作成に失敗しました。CSVファイル名のみ=[{0}]、サフィックスと拡張子=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12000("一時的なCSVファイルの作成に失敗しました。CSVファイル名のみ=[{0}]、サフィックスと拡張子=[{1}]"),

    /**
     * テンプレートファイルをYAML形式で読み込むことに失敗しました。テンプレートパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12001("テンプレートファイルをYAML形式で読み込むことに失敗しました。テンプレートパス=[{0}]"),

    /**
     * テンプレートの動的変換ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12002("テンプレートの動的変換ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 入力ファイルを処理し、テンプレートに基づいて出力を生成中にエラーが発生しました。入力ファイルパス=[{0}]、出力ファイルパス=[{1}]、テンプレートファイルパス=[{2}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12003("入力ファイルを処理し、テンプレートに基づいて出力を生成中にエラーが発生しました。入力ファイルパス=[{0}]、出力ファイルパス=[{1}]、テンプレートファイルパス=[{2}]"),

    /**
     * 入力ファイルパスがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12004("入力ファイルパスがnullです。"),

    /**
     * 入力パスファイルが存在しません。入力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12005("入力パスファイルが存在しません。入力ファイルパス=[{0}]"),

    /**
     * 入力ファイルの読み込みに失敗しました。入力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN12006("入力ファイルの読み込みに失敗しました。入力ファイルパス=[{0}]"),

    /**
     * 入力ファイルの読み込み中にエラーが発生しました。入力ファイルパス：[{0]}
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13000("入力ファイルの読み込み中にエラーが発生しました。入力ファイルパス：[{0]}"),

    /**
     * 出力バッファの書き込み中にエラーが発生しました。入力ファイルパス：[{0}]、 テンプレートパス：[{1}]、 出力ファイルパス=[{2}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13001("出力バッファの書き込み中にエラーが発生しました。入力ファイルパス：[{0}]、 テンプレートパス：[{1}]、 出力ファイルパス=[{2}]"),

    /**
     * 入力ファイルを開くことができませんでした。入力ファイルパス：[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13002("入力ファイルを開くことができませんでした。入力ファイルパス：[{0}]"),

    /**
     * 出力ファイルを開くことができませんでした。出力ファイルパス：[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13003("出力ファイルを開くことができませんでした。出力ファイルパス：[{0}]"),

    /**
     * CSVの列が不足しています。入力ファイルパス: [{0}]、 プレースホルダーキー: [{1}]、 列: [{2}] 番目
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13004("CSVの列が不足しています。入力ファイルパス: [{0}]、 プレースホルダーキー: [{1}]、 列: [{2}] 番目"),

    /**
     * CSVデータの書き込みに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13005("CSVデータの書き込みに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * ファイルのフラッシュに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13006("ファイルのフラッシュに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * 入力ファイルを開くのに失敗しました。入力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13007("入力ファイルを開くのに失敗しました。入力ファイルパス=[{0}]"),

    /**
     * 出力ファイルを開くのに失敗しました。出力ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN13008("出力ファイルを開くのに失敗しました。出力ファイルパス=[{0}]"),

    /**
     * ファイル処理に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31000("ファイル処理に失敗しました。"),

    /**
     * ファイル処理に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31001("ファイル処理に失敗しました。"),

    /**
     * テンプレートファイルの取得に失敗しました。テンプレートファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31002("テンプレートファイルの取得に失敗しました。テンプレートファイルパス=[{0}]"),

    /**
     * アクセサ作成ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31003("アクセサ作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 列挙型からcase文作成ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31004("列挙型からcase文作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * フィールド作成ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31005("フィールド作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * 定義ファイルの読み込みに失敗しました。定義ファイル=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31006("定義ファイルの読み込みに失敗しました。定義ファイル=[{0}]"),

    /**
     * 1行読み込みに失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31007("1行読み込みに失敗しました。"),

    /**
     * メッセージの種類作成ロジックをクローズ中にエラーが発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31010("メッセージの種類作成ロジックをクローズ中にエラーが発生しました。"),

    /**
     * タグ存在しないため、タグを追加しました。追加先の区分：[{0}]、 追加先の要素名：[{1}]、 追加したタグ：[{2}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31011("タグ存在しないため、タグを追加しました。追加先の区分：[{0}]、 追加先の要素名：[{1}]、 追加したタグ：[{2}]"),

    /**
     * タグを削除します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31012("タグを削除します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]"),

    /**
     * タグの位置を変更します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]、 変更後のタグの内容:[{6}]、 変更後のタグ:[{7}]、
     * 変更後の指定値:[{8}]、 変更後の説明:[{9}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31013(
        "タグの位置を変更します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]、 変更後のタグの内容:[{6}]、 変更後のタグ:[{7}]、 変更後の指定値:[{8}]、 変更後の説明:[{9}]"),

    /**
     * タグを置換します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]、 置換後のタグの内容:[{6}]、 置換後のタグ:[{7}]、
     * 置換後の指定値:[{8}]、 置換後の説明:[{9}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN31014(
        "タグを置換します。区分：[{0}]、 要素名：[{1}]、 元の対象行:[{2}]、 元のタグ:[{3}]、 元の指定値:[{4}]、 元の説明:[{5}]、 置換後のタグの内容:[{6}]、 置換後のタグ:[{7}]、 置換後の指定値:[{8}]、 置換後の説明:[{9}]"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32000("項目名がnullです。"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32001("項目名がnullです。"),

    /**
     * Javadocコメントがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32002("Javadocコメントがnullです。"),

    /**
     * 型情報がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32003("型情報がnullです。"),

    /**
     * 項目がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32004("項目がnullです。"),

    /**
     * 項目と項目名に分かれていません。「項目=項目名」の設定にしてください。行番号=[{0}]、行データ=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32005("項目と項目名に分かれていません。「項目=項目名」の設定にしてください。行番号=[{0}]、行データ=[{1}]"),

    /**
     * コメントがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32006("コメントがnullです。"),

    /**
     * フィールドがnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32007("フィールドがnullです。"),

    /**
     * 型がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32008("型がnullです。"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32009("項目名がnullです。"),

    /**
     * 項目名がnullです。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32010("項目名がnullです。"),

    /**
     * Javadocタグ設定で現在のファイルに内容を書き込み中に例外が発生しました。現在のファイルパス=[{0}]、書き込む内容=[{1}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32011("Javadocタグ設定で現在のファイルに内容を書き込み中に例外が発生しました。現在のファイルパス=[{0}]、書き込む内容=[{1}]"),

    /**
     * Javadocタグ設定で現在のファイルをロード中に例外が発生しました。現在のファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32012("Javadocタグ設定で現在のファイルをロード中に例外が発生しました。現在のファイルパス=[{0}]"),

    /**
     * Javadocタグ設定で対象ファイルをロード中に例外が発生しました。対象ファイルパス=[{0}]
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN32013("Javadocタグ設定で対象ファイルをロード中に例外が発生しました。対象ファイルパス=[{0}]"),

    /**
     * 失敗
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41000("失敗"),

    /**
     * 成功
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41001("成功"),

    /**
     * 例外発生
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41002("例外発生"),

    /**
     * 入力ファイルから対象パスを設定に失敗しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41003("入力ファイルから対象パスを設定に失敗しました。"),

    /**
     * 実行が成功しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41004("実行が成功しました。"),

    /**
     * 実行中に例外が発生しました。
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     */
    KMGTOOL_GEN41005("実行中に例外が発生しました。"),

    /* 定義：終了 */

    ;

    /**
     * 種類のマップ
     *
     * @since 0.1.0
     */
    private static final Map<String, KmgToolGenMessageTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final KmgToolGenMessageTypes type : KmgToolGenMessageTypes.values()) {

            KmgToolGenMessageTypes.VALUES_MAP.put(type.get(), type);

        }

    }

    /**
     * 表示名
     *
     * @since 0.1.0
     */
    private final String displayName;

    /**
     * メッセージのキー
     *
     * @since 0.1.0
     */
    private final String key;

    /**
     * メッセージの値
     *
     * @since 0.1.0
     */
    private final String value;

    /**
     * 詳細情報
     *
     * @since 0.1.0
     */
    private final String detail;

    /**
     * デフォルトの種類を返す<br>
     *
     * @since 0.1.0
     *
     * @return デフォルト値
     */
    public static KmgToolGenMessageTypes getDefault() {

        final KmgToolGenMessageTypes result = NONE;
        return result;

    }

    /**
     * キーに該当する種類を返す<br>
     * <p>
     * 但し、キーが存在しない場合は、指定無し（NONE）を返す。
     * </p>
     *
     * @since 0.1.0
     *
     * @param key
     *            キー
     *
     * @return 種類。指定無し（NONE）：キーが存在しない場合。
     */
    public static KmgToolGenMessageTypes getEnum(final String key) {

        KmgToolGenMessageTypes result = KmgToolGenMessageTypes.VALUES_MAP.get(key);

        if (result == null) {

            result = NONE;

        }
        return result;

    }

    /**
     * 初期値の種類を返す<br>
     *
     * @since 0.1.0
     *
     * @return 初期値
     */
    public static KmgToolGenMessageTypes getInitValue() {

        final KmgToolGenMessageTypes result = NONE;
        return result;

    }

    /**
     * コンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param displayName
     *                    表示名
     */
    KmgToolGenMessageTypes(final String displayName) {

        this.displayName = displayName;
        this.key = super.name();
        this.value = displayName;
        this.detail = displayName;

    }

    /**
     * メッセージのキーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return メッセージのキー
     *
     * @see #getKey()
     */
    @Override
    public String get() {

        final String result = this.getKey();
        return result;

    }

    /**
     * メッセージのキーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return メッセージのキー
     *
     * @see #getKey()
     */
    @Override
    public String getCode() {

        final String result = this.getKey();
        return result;

    }

    /**
     * 詳細情報を返す。<br>
     *
     * @since 0.1.0
     *
     * @return 詳細情報
     */
    @Override
    public String getDetail() {

        final String result = this.detail;
        return result;

    }

    /**
     * 表示名を返す。<br>
     * <p>
     * 識別するための表示名を返す。
     * </p>
     *
     * @since 0.1.0
     *
     * @return 表示名
     */
    @Override
    public String getDisplayName() {

        final String result = this.displayName;
        return result;

    }

    /**
     * メッセージのキーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return メッセージのキー
     */
    @Override
    public String getKey() {

        final String result = this.key;
        return result;

    }

    /**
     * メッセージの値を返す。
     *
     * @since 0.1.0
     *
     * @return メッセージの値
     */
    @Override
    public String getValue() {

        final String result = this.value;
        return result;

    }

    /**
     * メッセージのキーを返す。<br>
     *
     * @since 0.1.0
     *
     * @return メッセージのキー
     *
     * @see #getKey()
     */
    @Override
    public String toString() {

        final String result = this.getKey();
        return result;

    }

}
