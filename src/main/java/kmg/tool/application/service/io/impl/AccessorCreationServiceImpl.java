package kmg.tool.application.service.io.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.tool.application.logic.io.AccessorCreationLogic;
import kmg.tool.application.service.io.AccessorCreationService;
import kmg.tool.domain.service.io.AbstractInputCsvTemplateOutputProcessorService;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationServiceImpl extends AbstractInputCsvTemplateOutputProcessorService
    implements AccessorCreationService {

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /** アクセサ作成ロジック */
    @Autowired
    private AccessorCreationLogic accessorCreationLogic;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public AccessorCreationServiceImpl() {

        this(LoggerFactory.getLogger(AccessorCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected AccessorCreationServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * CSVファイルに書き込む。<br>
     * <p>
     * 入力ファイルからCSV形式に変換してCSVファイルに出力する。
     * </p>
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    protected boolean writeCsvFile() throws KmgToolException {

        final boolean result = false;

        // TODO KenichiroArai 2025/03/09 ログ
        this.logger.debug("CSVファイルに書き込む開始");

        /* アクセサ作成ロジックの初期化 */
        this.accessorCreationLogic.initialize(this.getInputPath(), this.getCsvPath());

        /* 書き込み対象に行を追加する */
        this.accessorCreationLogic.addOneLineOfDataToCsvRows();

        do {

            /* 1行データを読み込む */
            final boolean readFlg = this.readOneLineData();

            if (!readFlg) {

                break;

            }

            /* カラムを追加する */
            final boolean processedFlg = this.processColumns();

            if (!processedFlg) {

                continue;

            }

            /* CSVファイルに行を書き込む */
            try {

                this.accessorCreationLogic.writeCsvFile();

            } catch (final KmgToolException e) {

                // TODO KenichiroArai 2025/03/09 ログ
                this.logger.error("CSVファイルに書き込み中にエラーが発生しました", e);
                throw e;

            }

            // TODO KenichiroArai 2025/03/09 ログ
            this.logger.debug(String.format("書き込み完了。名称=[%s], 項目名=[%s]", this.accessorCreationLogic.getJavadocComment(),
                this.accessorCreationLogic.getItem()));

            /* クリア処理 */
            this.clearAndPrepareNextLine();

        } while (true);

        return result;

    }

    /**
     * 1行分のCSVを格納するリストにカラム1：名称を追加する。
     *
     * @return true：追加した、false：追加していない
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean addNameColumn() throws KmgToolException {

        boolean result = false;

        // Javadocコメントに変換
        final boolean convertJavadocCommentFlg = this.accessorCreationLogic.convertJavadocComment();

        if (!convertJavadocCommentFlg) {

            return result;

        }

        // カラム1：名称を書き込み対象に追加する。
        this.accessorCreationLogic.addJavadocCommentToCsvRows();

        result = true;
        return result;

    }

    /**
     * 1行分のCSVを格納するリストに残りのカラムを追加する。
     *
     * @return true：追加した、false：追加していない
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean addRemainingColumns() throws KmgToolException {

        boolean result = false;

        /* 不要な修飾子を削除する */
        this.accessorCreationLogic.removeModifier();

        /* 型、項目名、先頭大文字項目に追加する */

        // フィールド宣言から型、項目名、先頭大文字項目に変換する。
        final boolean convertFieldsFlg = this.accessorCreationLogic.convertFields();

        if (!convertFieldsFlg) {

            return result;

        }

        // テンプレートの各カラムに対応する値をを書き込み対象に追加する
        // カラム2：型
        this.accessorCreationLogic.addTypeToCsvRows();
        // カラム3：項目
        this.accessorCreationLogic.addItemToCsvRows();
        // カラム4：先頭大文字項目
        this.accessorCreationLogic.addCapitalizedItemToCsvRows();

        result = true;

        return result;

    }

    /**
     * データをクリアして次の行の準備をする。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void clearAndPrepareNextLine() throws KmgToolException {

        try {

            // 書き込み対象のCSVデータのリストをクリアする
            this.accessorCreationLogic.clearCsvRows();

            // 処理中のデータをクリアする
            this.accessorCreationLogic.clearProcessingData();

            /* 書き込み対象に行を追加する */
            this.accessorCreationLogic.addOneLineOfDataToCsvRows();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/09 ログ
            this.logger.error("クリア処理中にエラーが発生しました", e);
            throw e;

        }

    }

    /**
     * カラムを処理する。
     *
     * @return true：処理成功、false：処理スキップ
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean processColumns() throws KmgToolException {

        final boolean result = true;

        try {

            // カラム1：名称を追加する
            final boolean addNameColumnFlg = this.addNameColumn();

            if (addNameColumnFlg) {

                return false;

            }

            // 残りのカラムを追加する
            final boolean addRemainingColumnsFlg = this.addRemainingColumns();

            if (!addRemainingColumnsFlg) {

                return false;

            }

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/09 ログ
            this.logger.error("カラムの追加中にエラーが発生しました", e);
            throw e;

        }

        return result;

    }

    /**
     * 1行データを読み込む。
     *
     * @return true：読み込み成功、false：読み込み終了
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private boolean readOneLineData() throws KmgToolException {

        boolean result = false;

        try {

            result = this.accessorCreationLogic.readOneLineOfData();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/09 ログ
            this.logger.error("1行データの読み込み中にエラーが発生しました", e);
            throw e;

        }

        return result;

    }
}
