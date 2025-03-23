package kmg.tool.application.service.io.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.io.AccessorCreationLogic;
import kmg.tool.application.service.io.AccessorCreationService;
import kmg.tool.domain.service.io.AbstractIctoProcessorService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * アクセサ作成サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class AccessorCreationServiceImpl extends AbstractIctoProcessorService implements AccessorCreationService {

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

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

        boolean result = false;

        final KmgToolLogMessageTypes startLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31000;
        final Object[]               startLogMsgArgs  = {};
        final String                 startLogMsg      = this.messageSource.getLogMessage(startLogMsgTypes,
            startLogMsgArgs);
        this.logger.debug(startLogMsg);

        try {

            /* アクセサ作成ロジックの初期化 */
            this.accessorCreationLogic.initialize(this.getInputPath(), this.getCsvPath());

            /* 書き込み対象に行を追加する */
            this.accessorCreationLogic.addOneLineOfDataToCsvRows();

            do {

                /* 1行データを読み込む */
                final boolean isRead = this.readOneLineData();

                if (!isRead) {

                    break;

                }

                /* カラムを追加する */
                final boolean isProcessed = this.processColumns();

                if (!isProcessed) {

                    continue;

                }

                /* CSVファイルに行を書き込む */
                this.writeCsvFileLine();

                /* クリア処理 */
                this.clearAndPrepareNextLine();

            } while (true);

            result = true;

        } catch (final KmgToolException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31007;
            final Object[]               logMsgArgs  = {
                this.getOutputPath().toString(),
            };
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        } finally {

            try {

                /* アクセサ作成ロジックのクローズ処理 */
                this.closeAccessorCreationLogic();

            } finally {

                final KmgToolLogMessageTypes endLogMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31006;
                final Object[]               endLogMsgArgs  = {};
                final String                 endLogMsg      = this.messageSource.getLogMessage(endLogMsgTypes,
                    endLogMsgArgs);
                this.logger.debug(endLogMsg);

            }

        }
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
        final boolean isConvertJavadocComment = this.accessorCreationLogic.convertJavadoc();

        if (!isConvertJavadocComment) {

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

        // フィールド宣言から型、項目名に変換する。
        final boolean isConvertFields = this.accessorCreationLogic.convertFields();

        if (!isConvertFields) {

            return result;

        }

        // テンプレートの各カラムに対応する値をを書き込み対象に追加する
        // カラム2：型
        this.accessorCreationLogic.addTypeToCsvRows();
        // カラム3：項目
        this.accessorCreationLogic.addItemToCsvRows();

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

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31003;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * アクセサ作成ロジックをクローズする。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void closeAccessorCreationLogic() throws KmgToolException {

        try {

            this.accessorCreationLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.KMGTOOL_GEN31003;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolException(genMsgTypes, genMsgArgs, e);

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

        boolean result = false;

        try {

            // カラム1：名称を追加する
            this.addNameColumn();

            final String javadocComment = this.accessorCreationLogic.getJavadocComment();

            // Javadocコメントが設定されていないか
            if (javadocComment == null) {
                // 設定されていない場合

                // Javadocコメントが先に設定されていないと残りのカラム情報は読み込めないため、処理をスキップさせる
                return result;

            }

            // 残りのカラムを追加する
            final boolean isAddRemainingColumns = this.addRemainingColumns();

            if (!isAddRemainingColumns) {

                return result;

            }

        } catch (final KmgToolException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31004;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        result = true;
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

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31005;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        return result;

    }

    /**
     * CSVファイルに行を書き込む。
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    private void writeCsvFileLine() throws KmgToolException {

        try {

            this.accessorCreationLogic.writeCsvFile();

        } catch (final KmgToolException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31001;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);
            throw e;

        }

        final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31002;
        final Object[]               logMsgArgs  = {
            this.accessorCreationLogic.getJavadocComment(), this.accessorCreationLogic.getItem(),
        };
        final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }
}
