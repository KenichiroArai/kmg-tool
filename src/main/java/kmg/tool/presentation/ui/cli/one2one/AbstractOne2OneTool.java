package kmg.tool.presentation.ui.cli.one2one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.domain.service.io.One2OneService;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolMsgException;
import kmg.tool.presentation.ui.cli.AbstractIoTool;

/**
 * 1入力ファイルから1出力ファイルへの変換ツールサービス抽象クラス
 */
public abstract class AbstractOne2OneTool extends AbstractIoTool {

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /** メッセージソース */
    @Autowired
    private KmgMessageSource messageSource;

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @param toolName
     *                 ツール名
     *
     * @since 0.1.0
     */
    public AbstractOne2OneTool(final String toolName) {

        this(LoggerFactory.getLogger(AbstractOne2OneTool.class), toolName);

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *                 ロガー
     * @param toolName
     *                 ツール名
     */
    protected AbstractOne2OneTool(final Logger logger, final String toolName) {

        super(toolName);
        this.logger = logger;

    }

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     */
    public boolean initialize() {

        final boolean result = false;

        boolean initializeResult = false;

        try {

            initializeResult
                = this.getIoService().initialize(AbstractIoTool.getInputPath(), AbstractIoTool.getOutputPath());

        } catch (final KmgToolMsgException e) {

            // ログの出力
            final KmgToolLogMessageTypes logType     = KmgToolLogMessageTypes.KMGTOOL_LOG41002;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg, e);

        }

        if (!initializeResult) {

            // ログの出力
            final KmgToolLogMessageTypes logType     = KmgToolLogMessageTypes.KMGTOOL_LOG41000;
            final Object[]               messageArgs = {};
            final String                 msg         = this.messageSource.getLogMessage(logType, messageArgs);
            this.logger.error(msg);

        }

        return result;

    }

    /**
     * 1入力ファイルから1出力ファイルへの変換ツールサービスを返す。
     *
     * @return 1入力ファイルから1出力ファイルへの変換ツールサービス
     */
    @Override
    protected abstract One2OneService getIoService();

}
