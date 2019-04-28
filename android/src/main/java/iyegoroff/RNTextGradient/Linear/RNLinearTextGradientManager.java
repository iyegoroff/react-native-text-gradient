package iyegoroff.RNTextGradient.Linear;

import iyegoroff.RNTextGradient.RNTextGradientManager;
import iyegoroff.RNTextGradient.RNShadowTextGradient;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;

import javax.annotation.Nonnull;

@ReactModule(name = RNLinearTextGradientManager.REACT_CLASS)
public class RNLinearTextGradientManager extends RNTextGradientManager {

    @VisibleForTesting
    static final String REACT_CLASS = "RNLinearTextGradient";

    @Override
    public @Nonnull String getName() {
        return REACT_CLASS;
    }

    @Override
    public @Nonnull RNShadowTextGradient createShadowNodeInstance(
      @Nonnull ReactApplicationContext context
    ) {
        return new RNShadowLinearTextGradient(context);
    }

    @Override
    public Class<? extends RNShadowTextGradient> getShadowNodeClass() {
        return RNShadowLinearTextGradient.class;
    }

}
