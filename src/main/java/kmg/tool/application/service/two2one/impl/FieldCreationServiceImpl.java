package kmg.tool.application.service.two2one.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.two2one.dtc.FieldCreationLogic;
import kmg.tool.application.service.two2one.FieldCreationService;
import kmg.tool.domain.service.io.AbstractIctoProcessorService;
import kmg.tool.domain.types.KmgToolGenMessageTypes;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolMsgException;

/**
 * フィールド作成サービス実装クラス
 *
 * @author KenichiroArai
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Service
public class FieldCreationServiceImpl extends AbstractIctoProcessorService implements FieldCreationService {

    /**
     * ロガー
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     */
    @Autowired
    private KmgMessageSource messageSource;

    /** フィールド作成ロジック */
    @Autowired
    private FieldCreationLogic fieldCreationLogic;

    /**
     * デフォルトコンストラクタ
     */
    public FieldCreationServiceImpl() {

        this(LoggerFactory.getLogger(FieldCreationServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して初期化するコンストラクタ
     *
     * @param logger
     *               ロガー
     */
    protected FieldCreationServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean writeCsvFile() throws KmgToolMsgException {

        boolean result = false;

        try {

            /* フィールド作成ロジックの初期化 */
            this.fieldCreationLogic.initialize(this.getInputPath(), this.getCsvPath());

            /* 書き込み対象に行を追加する */
            this.fieldCreationLogic.addOneLineOfDataToCsvRows();

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

        } finally {

            /* フィールド作成ロジックのクローズ処理 */
            this.closeFieldCreationLogic();

        }

        return result;

    }

    /**
     * データをクリアして次の行の準備をする
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void clearAndPrepareNextLine() throws KmgToolMsgException {

        try {

            // 書き込み対象のCSVデータのリストをクリアする
            this.fieldCreationLogic.clearCsvRows();

            // 処理中のデータをクリアする
            this.fieldCreationLogic.clearProcessingData();

            /* 書き込み対象に行を追加する */
            this.fieldCreationLogic.addOneLineOfDataToCsvRows();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31000;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

    }

    /**
     * フィールド作成ロジックをクローズする
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void closeFieldCreationLogic() throws KmgToolMsgException {

        try {

            this.fieldCreationLogic.close();

        } catch (final IOException e) {

            final KmgToolGenMessageTypes genMsgTypes = KmgToolGenMessageTypes.KMGTOOL_GEN31005;
            final Object[]               genMsgArgs  = {};
            throw new KmgToolMsgException(genMsgTypes, genMsgArgs, e);

        }

    }

    /**
     * カラムを処理する
     *
     * @return true：処理成功、false：処理スキップ
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean processColumns() throws KmgToolMsgException {

        boolean result = false;

        try {

            /* フィールドデータを変換する */
            final boolean isConverted = this.fieldCreationLogic.convertFields();

            if (!isConverted) {

                return false;

            }

            /* 各カラムを追加する */
            this.fieldCreationLogic.addCommentToCsvRows();
            this.fieldCreationLogic.addFieldToCsvRows();
            this.fieldCreationLogic.addTypeToCsvRows();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31008;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        result = true;
        return result;

    }

    /**
     * 1行データを読み込む
     *
     * @return true：読み込み成功、false：読み込み終了
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private boolean readOneLineData() throws KmgToolMsgException {

        boolean result = false;

        try {

            result = this.fieldCreationLogic.readOneLineOfData();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31009;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }

        return result;

    }

    /**
     * CSVファイルに行を書き込む
     *
     * @throws KmgToolMsgException
     *                             KMGツールメッセージ例外
     */
    private void writeCsvFileLine() throws KmgToolMsgException {

        try {

            this.fieldCreationLogic.writeCsvFile();

        } catch (final KmgToolMsgException e) {

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31012;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);
            throw e;

        }

        final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31018;
        final Object[]               logMsgArgs  = {
            this.fieldCreationLogic.getComment(),
        };
        final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
        this.logger.debug(logMsg);

    }
}
