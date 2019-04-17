package iyegoroff.RNTextGradient.Linear;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import iyegoroff.RNTextGradient.RNVirtualTextGradientManager;
import java.lang.String;

@ReactModule(name = RNVirtualLinearTextGradientManager.REACT_CLASS)
public class RNVirtualLinearTextGradientManager extends RNVirtualTextGradientManager<RNVirtualShadowLinearTextGradient> {

  @VisibleForTesting
  static final String REACT_CLASS = "RNVirtualLinearTextGradient";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public RNVirtualShadowLinearTextGradient createShadowNodeInstance(ReactApplicationContext context) {
    return new RNVirtualShadowLinearTextGradient(context);
  }

  @Override
  public Class<? extends RNVirtualShadowLinearTextGradient> getShadowNodeClass() {
    return RNVirtualShadowLinearTextGradient.class;
  }
}
