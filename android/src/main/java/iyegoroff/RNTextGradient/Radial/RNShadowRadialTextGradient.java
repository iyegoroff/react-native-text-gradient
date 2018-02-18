package iyegoroff.RNTextGradient.Radial;

import iyegoroff.RNTextGradient.RNShadowTextGradient;
import iyegoroff.RNTextGradient.RNSetGradientSpanOperation;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

public class RNShadowRadialTextGradient extends RNShadowTextGradient {

  private float[] mCenter;
  private float mRadius;

  @ReactProp(name = "center")
  public void setCenter(ReadableArray center) {
    if (center != null) {
      mCenter = new float[] {
        (float) center.getDouble(0),
        (float) center.getDouble(1)
      };

      markUpdated();
    }
  }

  @ReactProp(name = "radius")
  public void setRadius(float radius) {
    mRadius = radius;

    markUpdated();
  }

  @Override
  protected RNSetGradientSpanOperation createSpan(
    SpannableStringBuilder builder,
    int start,
    int end,
    float maxWidth
  ) {
    return null;
  }
}
