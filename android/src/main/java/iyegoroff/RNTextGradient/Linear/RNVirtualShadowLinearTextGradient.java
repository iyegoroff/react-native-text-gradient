package iyegoroff.RNTextGradient.Linear;

import com.facebook.react.bridge.ReactApplicationContext;

public class RNVirtualShadowLinearTextGradient extends RNShadowLinearTextGradient {

  RNVirtualShadowLinearTextGradient(ReactApplicationContext context) {
    super(context);
  }

  @Override
  public boolean isVirtual() {
    return true;
  }
}
