package iyegoroff.RNTextGradient;

import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.BaseViewManager;
import android.view.View;

public abstract class RNVirtualTextGradientManager<T extends RNShadowTextGradient> extends BaseViewManager<View, T> {

  @Override
  public RNTextGradient createViewInstance(ThemedReactContext context) {
    throw new IllegalStateException("RNVirtualTextGradientManager doesn't have a native view");
  }

  @Override
  public void updateExtraData(View view, Object extraData) {
  }
}
