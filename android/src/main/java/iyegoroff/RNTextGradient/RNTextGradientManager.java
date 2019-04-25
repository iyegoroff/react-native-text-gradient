package iyegoroff.RNTextGradient;

import com.facebook.react.views.text.ReactTextViewManager;
import com.facebook.react.views.text.ReactTextAnchorViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import javax.annotation.Nonnull;

public abstract class RNTextGradientManager extends ReactTextAnchorViewManager<
  RNTextGradient,
  RNShadowTextGradient
> {

  private ReactTextViewManager mManager = new ReactTextViewManager();

  @Override
  public @Nonnull RNTextGradient createViewInstance(
    @Nonnull ThemedReactContext context
  ) {
    return new RNTextGradient(context);
  }

  @Override
  public void updateExtraData(@Nonnull RNTextGradient view, Object extraData) {
    mManager.updateExtraData(view, extraData);
  }
}