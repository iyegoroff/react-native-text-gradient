package iyegoroff.RNTextGradient.Linear;

import android.text.Layout;
import android.text.SpannableStringBuilder;

import iyegoroff.RNTextGradient.RNShadowTextGradient;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import iyegoroff.RNTextGradient.RNSetGradientSpanOperation;

public class RNShadowLinearTextGradient extends RNShadowTextGradient {

  private float[] mStart;
  private float[] mEnd;

  RNShadowLinearTextGradient(ReactApplicationContext context) {
    super(context);
  }

  @SuppressWarnings("unused")
  @ReactProp(name = "gradientStart")
  public void setStart(ReadableArray start) {
    if (start != null) {
      mStart = new float[] {
        (float) start.getDouble(0),
        (float) start.getDouble(1)
      };

      markUpdated();
    }
  }

  @SuppressWarnings("unused")
  @ReactProp(name = "gradientEnd")
  public void setEnd(ReadableArray end) {
    if (end != null) {
      mEnd = new float[] {
        (float) end.getDouble(0),
        (float) end.getDouble(1)
      };

      markUpdated();
    }
  }

  @Override
  protected RNSetGradientSpanOperation createSpan(
    SpannableStringBuilder builder,
    int start,
    int end,
    float maxWidth,
    float maxHeight,
    Layout layout
  ) {
    RNLinearTextGradientSpan span = new RNLinearTextGradientSpan(
      mLocations,
      mColors,
      mStart,
      mEnd,
      mUseViewFrame,
      layout,
      start,
      end,
      maxWidth,
      maxHeight,
      builder.toString()
    );

    return new RNSetGradientSpanOperation(start, end, span);
  }
}
