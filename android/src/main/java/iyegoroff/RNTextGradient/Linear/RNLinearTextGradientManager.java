package iyegoroff.RNTextGradient.Linear;

import iyegoroff.RNTextGradient.RNTextGradientManager;
import iyegoroff.RNTextGradient.RNShadowTextGradient;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = RNLinearTextGradientManager.REACT_CLASS)
public class RNLinearTextGradientManager extends RNTextGradientManager {

    @VisibleForTesting
    static final String REACT_CLASS = "RNLinearTextGradient";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public RNShadowTextGradient createShadowNodeInstance(ReactApplicationContext context) {
        return new RNShadowLinearTextGradient(context);
    }

    @Override
    public Class<? extends RNShadowTextGradient> getShadowNodeClass() {
        return RNShadowLinearTextGradient.class;
    }

}
