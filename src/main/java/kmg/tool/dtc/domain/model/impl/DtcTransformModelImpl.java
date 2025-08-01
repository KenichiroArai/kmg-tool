package kmg.tool.dtc.domain.model.impl;

import kmg.core.infrastructure.type.KmgString;
import kmg.tool.dtc.domain.model.DtcTransformModel;
import kmg.tool.dtc.domain.types.DtcTransformTypes;

/**
 * テンプレートの動的変換モデル実装<br>
 *
 * @author KenichiroArai
 *
 * @since 1.0.0
 */
public class DtcTransformModelImpl implements DtcTransformModel {

    /** 元の値 */
    private final String originalValue;

    /** 変換処理の種類 */
    private DtcTransformTypes dtcTransformTypes;

    /** 変換後の値 */
    private String transformedValue;

    /**
     * コンストラクタ<br>
     *
     * @param value
     *                          元の値
     * @param dtcTransformTypes
     *                          変換処理の種類
     */
    public DtcTransformModelImpl(final String value, final DtcTransformTypes dtcTransformTypes) {

        this.dtcTransformTypes = dtcTransformTypes;

        if (value == null) {

            this.originalValue = KmgString.EMPTY;
            this.transformedValue = KmgString.EMPTY;
            return;

        }

        this.originalValue = value;
        this.transformedValue = value;

    }

    /**
     * 指定された変換処理を適用する<br>
     */
    @Override
    public void apply() {

        this.transformedValue = this.dtcTransformTypes.transform(this.originalValue);

    }

    /**
     * 元の値を返す<br>
     *
     * @return 元の値
     */
    @Override
    public String getOriginalValue() {

        String result;

        result = this.originalValue;
        return result;

    }

    /**
     * 変換後の値を返す<br>
     *
     * @return 変換後の値
     */
    @Override
    public String getTransformedValue() {

        String result;

        result = this.transformedValue;
        return result;

    }
}
