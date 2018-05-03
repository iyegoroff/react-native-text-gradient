package iyegoroff.RNTextGradient.Linear;

// based on this: https://github.com/chiuki/advanced-textview/blob/master/app/src/main/java/com/sqisland/android/advanced_textview/RainbowSpanActivity.java

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import java.lang.String;
import android.util.Log;
import com.facebook.react.common.ReactConstants;
import com.facebook.yoga.YogaConstants;
import android.graphics.Rect;
import java.lang.Math;
import android.graphics.Paint.FontMetrics;

public class RNLinearTextGradientSpan extends CharacterStyle implements UpdateAppearance {
  class TextBounds {
    public float top;
    public float bottom;
    public float left;
    public float right;
    public float width;
    public float height;
  }

  private int[] mColors;
  private float[] mLocations;
  private float[] mStart;
  private float[] mEnd;
  private boolean mUseViewFrame;
  private int mMaxWidth;
  private int mMaxHeight;
  private int mTextStart;
  private int mTextEnd;
  private String mText;
  private float mLineHeight;

  public RNLinearTextGradientSpan(
    float[] locations,
    int[] colors,
    float[] start,
    float[] end,
    boolean useViewFrame,
    float maxWidth,
    float maxHeight,
    int textStart,
    int textEnd,
    String text,
    float lineHeight
  ) {
    mLocations = locations;
    mColors = colors;
    mStart = start;
    mEnd = end;
    mUseViewFrame = useViewFrame;
    mMaxWidth = (int)maxWidth;
    mMaxHeight = (int)maxHeight;
    mTextStart = textStart;
    mTextEnd = textEnd;
    mText = text;
    mLineHeight = lineHeight;
  }

  @Override
  public void updateDrawState(TextPaint paint) {
    if (mStart != null &&
      mEnd != null &&
      mColors != null &&
      mLocations != null &&
      mText != null &&
      !YogaConstants.isUndefined(mMaxWidth)
    ) {
      float lineHeight = (int) Math.ceil(Float.isNaN(mLineHeight) ? paint.getFontSpacing() : mLineHeight);

      TextBounds rectBeforeGradient = textBounds(
        mText.substring(0, mTextStart),
        paint,
        0,
        0,
        lineHeight
      );

      TextBounds gradientRect = textBounds(
        mText.substring(mTextStart, mTextEnd),
        paint,
        rectBeforeGradient.right,
        rectBeforeGradient.bottom - lineHeight,
        lineHeight
      );

      float width = mUseViewFrame ? mMaxWidth : gradientRect.width;
      float height = mUseViewFrame ? mMaxHeight : gradientRect.height;

      // Log.d(ReactConstants.TAG, "before: 0 - " + String.valueOf(mTextStart) + " " + textBoundsAsString(rectBeforeGradient));
      // Log.d(ReactConstants.TAG, "gradient: " + String.valueOf(mTextStart) + " - " + String.valueOf(mTextEnd) + " " + textBoundsAsString(gradientRect));
      // Log.d(ReactConstants.TAG, "width " + String.valueOf(width) + " height " + String.valueOf(height));
      // Log.d(ReactConstants.TAG, "lineHeight " + String.valueOf(lineHeight) + " spacing " + String.valueOf(paint.getFontSpacing()));
      // Log.d(ReactConstants.TAG, "text " + mText);

      LinearGradient gradient = new LinearGradient(
        gradientRect.left + mStart[0] * width,
        gradientRect.top + mStart[1] * height,
        gradientRect.left + mEnd[0] * width,
        gradientRect.top + mEnd[1] * height,
        mColors,
        mLocations,
        Shader.TileMode.CLAMP
      );

      paint.setStyle(Paint.Style.FILL);
      paint.setShader(gradient);
    }
  }

  // private String textBoundsAsString(TextBounds bounds) {
  //   return "TextBounds(t: " + bounds.top + " l: " + bounds.left + " b: " + bounds.bottom + " r: " + bounds.right + " h: " + bounds.height + " w: " + bounds.width + " )";
  // }

  private TextBounds textBounds(String text, Paint paint, float startX, float startY, float lineHeight) {
    TextBounds bounds = new TextBounds();
    bounds.left = startX;
    bounds.right = startX;
    bounds.top = startY;
    bounds.bottom = startY + lineHeight;
    bounds.width = 0;
    bounds.height = lineHeight;

    int lineStart = 0;
    int lineEnd = 1;
    float lineOffset = startX;

    while (lineEnd <= text.length()) {
      float lineWidth = paint.measureText(mText, lineStart, lineEnd) + lineOffset;
  
      if (lineWidth > mMaxWidth) {  
        if (lineEnd == 1) {
          bounds.top += lineHeight;
          bounds.left = 0;
        } else {
          bounds.width = Math.max(lineWidth, bounds.width);
        }

        lineStart = lineEnd - 1;
        lineOffset = 0;
        bounds.bottom += lineHeight;
        bounds.right = 0;
        bounds.height += lineHeight;
        bounds.left = 0;
      } else {
        lineEnd++;
        bounds.right = lineWidth;
        bounds.width = Math.max(lineWidth - lineOffset, bounds.width);
      }
    }

    return bounds;
  }
}
