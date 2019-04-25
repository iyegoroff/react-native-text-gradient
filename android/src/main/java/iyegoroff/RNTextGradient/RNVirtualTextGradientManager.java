package iyegoroff.RNTextGradient;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.BaseViewManager;
import android.view.View;

import javax.annotation.Nonnull;

public abstract class RNVirtualTextGradientManager<
  T extends RNShadowTextGradient
> extends BaseViewManager<View, T> {

  @Override
  public @Nonnull RNTextGradient createViewInstance(@Nonnull ThemedReactContext context) {
    throw new IllegalStateException("RNVirtualTextGradientManager doesn't have a native view");
  }

  @Override
  public void updateExtraData(@Nonnull View view, Object extraData) {
  }
}
