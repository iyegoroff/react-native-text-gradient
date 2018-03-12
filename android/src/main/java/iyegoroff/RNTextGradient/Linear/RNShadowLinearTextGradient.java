package iyegoroff.RNTextGradient.Linear;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import iyegoroff.RNTextGradient.RNShadowTextGradient;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextShadowNode;
import android.util.Log;
import com.facebook.react.common.ReactConstants;
import java.lang.Exception;
import java.lang.reflect.Field;
import java.lang.String;
import iyegoroff.RNTextGradient.RNSetGradientSpanOperation;
import android.text.style.ForegroundColorSpan;

public class RNShadowLinearTextGradient extends RNShadowTextGradient {

  private float[] mStart;
  private float[] mEnd;

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
    float maxHeight
  ) {
    RNLinearTextGradientSpan span = new RNLinearTextGradientSpan(
      mLocations,
      mColors,
      mStart,
      mEnd,
      mUseViewFrame,
      maxWidth,
      maxHeight,
      start,
      end,
      builder.toString()
    );

    return new RNSetGradientSpanOperation(start, end, span);
  }
}
