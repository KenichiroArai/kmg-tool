package kmg.tool.application.model.jdts.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import kmg.core.infrastructure.model.val.KmgValDataModel;
import kmg.core.infrastructure.model.val.KmgValsModel;
import kmg.core.infrastructure.model.val.impl.KmgValDataModelImpl;
import kmg.core.infrastructure.model.val.impl.KmgValsModelImpl;
import kmg.core.infrastructure.types.JavaClassificationTypes;
import kmg.tool.application.model.jdts.JdtsLocationConfigModel;
import kmg.tool.application.types.jdts.JdtsConfigKeyTypes;
import kmg.tool.application.types.jdts.JdtsLocationModeTypes;
import kmg.tool.domain.types.KmgToolValMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolValException;

/**
 * Javadocタグ設定の配置場所設定<br>
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
public class JdtsLocationConfigModelImpl implements JdtsLocationConfigModel {

    /** 配置方法 */
    private final JdtsLocationModeTypes mode;

    /** 誤配置時に削除するかどうか */
    private final boolean removeIfMisplaced;

    /** 対象要素の種類（手動モード時のみ使用） */
    private final List<JavaClassificationTypes> targetElements;

    /**
     * コンストラクタ<br>
     *
     * @param locationMap
     *                    配置場所の設定マップ
     *
     * @throws KmgToolValException
     *                             KMGツールバリデーション例外
     */
    public JdtsLocationConfigModelImpl(final Map<String, Object> locationMap) throws KmgToolValException {

        final KmgValsModel valsModel = new KmgValsModelImpl();

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        /* 配置方法の設定 */
        this.mode = JdtsLocationModeTypes.getEnum((String) locationMap.get(JdtsConfigKeyTypes.MODE.get()));

        /* 誤配置時に削除するかどうかの設定 */
        this.removeIfMisplaced
            = Boolean.parseBoolean(String.valueOf(locationMap.get(JdtsConfigKeyTypes.REMOVE_IF_MISPLACED.get())));

        /* 対象要素の種類の設定 */
        final List<String> targetElementsKeys
            = mapper.convertValue(locationMap.get(JdtsConfigKeyTypes.TARGET_ELEMENTS.get()), List.class);
        this.targetElements = new ArrayList<>();

        if (targetElementsKeys != null) {

            if (this.mode != JdtsLocationModeTypes.MANUAL) {

                // TODO KenichiroArai 2025/05/08 例外処理
                final KmgToolValMessageTypes valMsgTypes  = KmgToolValMessageTypes.NONE;
                final Object[]               valMsgArgs   = {};
                final KmgValDataModel        valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
                valsModel.addData(valDataModel);

                throw new KmgToolValException(valsModel);

            }

            for (final String key : targetElementsKeys) {

                final JavaClassificationTypes type = JavaClassificationTypes.getEnum(key);

                this.targetElements.add(type);

            }

        } else if (this.mode == JdtsLocationModeTypes.MANUAL) {

            // TODO KenichiroArai 2025/05/08 例外処理
            final KmgToolValMessageTypes valMsgTypes  = KmgToolValMessageTypes.NONE;
            final Object[]               valMsgArgs   = {};
            final KmgValDataModel        valDataModel = new KmgValDataModelImpl(valMsgTypes, valMsgArgs);
            valsModel.addData(valDataModel);

            throw new KmgToolValException(valsModel);

        }

    }

    /**
     * 配置方法を返す<br>
     *
     * @return 配置方法
     */
    @Override
    public JdtsLocationModeTypes getMode() {

        final JdtsLocationModeTypes result = this.mode;
        return result;

    }

    /**
     * 対象要素の種類を返す<br>
     *
     * @return 対象要素の種類
     */
    @Override
    public List<JavaClassificationTypes> getTargetElements() {

        final List<JavaClassificationTypes> result = this.targetElements;
        return result;

    }

    /**
     * 誤配置時に削除するかどうかを返す<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @return true：削除する、false：削除しない
     */
    @Override
    public boolean isRemoveIfMisplaced() {

        final boolean result = this.removeIfMisplaced;
        return result;

    }
}
