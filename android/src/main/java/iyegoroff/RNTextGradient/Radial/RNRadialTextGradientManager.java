package iyegoroff.RNTextGradient.Radial;

import iyegoroff.RNTextGradient.RNTextGradientManager;
import iyegoroff.RNTextGradient.RNShadowTextGradient;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = RNRadialTextGradientManager.REACT_CLASS)
public class RNRadialTextGradientManager extends RNTextGradientManager {

    @VisibleForTesting
    public static final String REACT_CLASS = "RNRadialTextGradient";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public RNShadowTextGradient createShadowNodeInstance() {
        return new RNShadowRadialTextGradient();
    }

    @Override
    public Class<? extends RNShadowTextGradient> getShadowNodeClass() {
        return RNShadowRadialTextGradient.class;
    }

}
