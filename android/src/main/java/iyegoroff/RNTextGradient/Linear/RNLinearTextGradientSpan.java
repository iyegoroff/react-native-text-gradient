package iyegoroff.RNTextGradient.Linear;

import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import java.lang.String;

import com.facebook.yoga.YogaConstants;

public class RNLinearTextGradientSpan extends CharacterStyle implements UpdateAppearance {

  private LinearGradient mGradient;

  RNLinearTextGradientSpan(
    float[] locations,
    int[] colors,
    float[] start,
    float[] end,
    boolean useViewFrame,
    Layout layout,
    int textStart,
    int textEnd,
    float maxWidth,
    float maxHeight,
    String text,
    boolean useAbsoluteSizes
  ) {
    if (
      start != null &&
      end != null &&
      colors != null &&
      locations != null &&
      text != null &&
      !YogaConstants.isUndefined(maxWidth) &&
      maxWidth != 0 &&
      maxHeight != 0 &&
      layout != null
    ) {
      Path path = new Path();
      RectF bounds = new RectF();
      layout.getSelectionPath(textStart, textEnd, path);
      path.computeBounds(bounds, true);

      float width = useViewFrame ? maxWidth : bounds.width();
      float height = useViewFrame ? maxHeight : bounds.height();
      float x0 = useAbsoluteSizes ? start[0] : (bounds.left + start[0] * width);
      float y0 = useAbsoluteSizes ? start[1] : (bounds.top + start[1] * height);
      float x1 = useAbsoluteSizes ? end[0] : (bounds.left + end[0] * width);
      float y1 = useAbsoluteSizes ? end[1] : (bounds.top + end[1] * height);

//      LogLayout.log(layout, textStart, textEnd, "'" + text.substring(textStart, textEnd) + "' " + textStart + " " + textEnd);

      mGradient = new LinearGradient(x0, y0, x1, y1, colors, locations, Shader.TileMode.CLAMP);
    }
  }

  @Override
  public void updateDrawState(TextPaint paint) {
    if (mGradient != null) {
      paint.setStyle(Paint.Style.FILL);
      paint.setShader(mGradient);
    }
  }
}
