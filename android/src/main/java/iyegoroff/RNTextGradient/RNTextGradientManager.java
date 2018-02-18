package iyegoroff.RNTextGradient;

import com.facebook.react.views.text.ReactTextViewManager;
import com.facebook.react.views.text.ReactTextView;
import com.facebook.react.views.text.ReactTextAnchorViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public abstract class RNTextGradientManager extends ReactTextAnchorViewManager<RNTextGradient, RNShadowTextGradient> {

  private ReactTextViewManager mManager = new ReactTextViewManager();

  @Override
  public RNTextGradient createViewInstance(ThemedReactContext context) {
    return new RNTextGradient(context);
  }

  @Override
  public void updateExtraData(RNTextGradient view, Object extraData) {
    mManager.updateExtraData(view, extraData);
  }
}