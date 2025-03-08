package kmg.tool.application.service.io.impl;

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

    /** アクセサ作成ロジック */
    @Autowired
    private AccessorCreationLogic accessorCreationLogic;

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

        /* アクセサ作成ロジックの初期化 */
        this.accessorCreationLogic.initialize(this.getInputPath(), this.getCsvPath());

        while (this.accessorCreationLogic.readOneLineOfData()) {

            /* カラム1：名称を追加する */
            final boolean addNameColumnFlg = this.addNameColumn();

            if (addNameColumnFlg) {

                continue;

            }

            /* 残りのカラムを追加する */
            final boolean addRemainingColumnsFlg = this.addRemainingColumns();

            if (!addRemainingColumnsFlg) {

                continue;

            }

            /* CSVファイルに行を書き込む */
            this.accessorCreationLogic.writeCsvFile();

            /* クリア処理 */

            // 書き込み対象のCSVデータのリストをクリアする
            this.accessorCreationLogic.clearCsvRows();

            // 処理中のデータをクリアする
            this.accessorCreationLogic.clearProcessingData();

        }

        return result;

    }

    /**
     * 1行分のCSVを格納するリストにカラム1：名称を追加する。
     *
     * @return true：追加した、false：追加していない
     */
    private boolean addNameColumn() {

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

        // 1行データを読み込む
        final boolean readResult = this.accessorCreationLogic.readOneLineOfData();

        if (!readResult) {

            return result;

        }

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
}
