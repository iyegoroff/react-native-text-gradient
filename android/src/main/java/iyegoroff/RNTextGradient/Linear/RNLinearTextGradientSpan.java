package iyegoroff.RNTextGradient.Linear;

import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint;
import android.os.Build;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import java.lang.String;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.text.FontMetricsUtil;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;

import java.lang.Math;
import java.util.Locale;

import android.graphics.Paint.FontMetrics;
import android.util.Log;
import android.view.Gravity;

public class RNLinearTextGradientSpan extends CharacterStyle implements UpdateAppearance {
  class TextBounds {
    float top;
    float bottom;
    float left;
    float right;
    float width;
    float height;
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
  private boolean mUseAbsoluteSizes;
  private int mTextBreakStrategy;
  private boolean mIncludeFontPadding;

  RNLinearTextGradientSpan(
    float[] locations,
    int[] colors,
    float[] start,
    float[] end,
    boolean useViewFrame,
    float maxWidth,
    float maxHeight,
    int textBreakStrategy,
    boolean includeFontPadding,
    int textStart,
    int textEnd,
    String text,
    boolean useAbsoluteSizes
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
    mUseAbsoluteSizes = useAbsoluteSizes;
    mTextBreakStrategy = textBreakStrategy;
    mIncludeFontPadding = includeFontPadding;
  }

//  private Layout layoutDuplicate(TextPaint textPaint) {
//    int width = mMaxWidth;
//    Layout layout;
//    BoringLayout.Metrics boring = BoringLayout.isBoring(mText, textPaint);
//    float desiredWidth = boring == null ? Layout.getDesiredWidth(mText, textPaint) : Float.NaN;
//
//    Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
//    switch (mAlignment) {
//      case Gravity.LEFT:
//        alignment = Layout.Alignment.ALIGN_NORMAL;
//        break;
//      case Gravity.RIGHT:
//        alignment = Layout.Alignment.ALIGN_OPPOSITE;
//        break;
//      case Gravity.CENTER_HORIZONTAL:
//        alignment = Layout.Alignment.ALIGN_CENTER;
//        break;
//    }
//
//    if (boring == null
//            && ((!YogaConstants.isUndefined(desiredWidth) && desiredWidth <= width))) {
//      // Is used when the width is not known and the text is not boring, ie. if it contains
//      // unicode characters.
//
//      int hintWidth = (int) Math.ceil(desiredWidth);
//      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//        layout =
//                new StaticLayout(
//                        mText, textPaint, hintWidth, alignment, 1.f, 0.f, mIncludeFontPadding);
//      } else {
//        layout =
//                StaticLayout.Builder.obtain(mText, 0, mText.length(), textPaint, hintWidth)
//                        .setAlignment(alignment)
//                        .setLineSpacing(0.f, 1.f)
//                        .setIncludePad(mIncludeFontPadding)
//                        .setBreakStrategy(mTextBreakStrategy)
//                        .setHyphenationFrequency(Layout.HYPHENATION_FREQUENCY_NORMAL)
//                        .build();
//      }
//
//    } else if (boring != null && (boring.width <= width)) {
//      // Is used for single-line, boring text when the width is either unknown or bigger
//      // than the width of the text.
//      layout =
//              BoringLayout.make(
//                      mText,
//                      textPaint,
//                      boring.width,
//                      alignment,
//                      1.f,
//                      0.f,
//                      boring,
//                      mIncludeFontPadding);
//    } else {
//      // Is used for multiline, boring text and the width is known.
//
//      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//        layout =
//                new StaticLayout(
//                        mText, textPaint, (int) width, alignment, 1.f, 0.f, mIncludeFontPadding);
//      } else {
//        layout =
//                StaticLayout.Builder.obtain(mText, 0, mText.length(), textPaint, (int) width)
//                        .setAlignment(alignment)
//                        .setLineSpacing(0.f, 1.f)
//                        .setIncludePad(mIncludeFontPadding)
//                        .setBreakStrategy(mTextBreakStrategy)
//                        .setHyphenationFrequency(Layout.HYPHENATION_FREQUENCY_NORMAL)
//                        .build();
//      }
//    }
//
//    return layout;
//  }

  @Override
  public void updateDrawState(TextPaint paint) {
    if (mStart != null &&
      mEnd != null &&
      mColors != null &&
      mLocations != null &&
      mText != null &&
      !YogaConstants.isUndefined(mMaxWidth) &&
      mMaxWidth != 0 &&
      mMaxHeight != 0
    ) {
      Layout layout = this.layoutDuplicate(paint);
      Path path = new Path();
      RectF bounds = new RectF();
      layout.getSelectionPath(mTextStart, mTextEnd, path);
      path.computeBounds(bounds, true);

      float width = mUseViewFrame ? mMaxWidth : bounds.width();
      float height = mUseViewFrame ? mMaxHeight : bounds.height();
      float x0 = mUseAbsoluteSizes ? mStart[0] : (bounds.left + mStart[0] * width);
      float y0 = mUseAbsoluteSizes ? mStart[1] : (bounds.top + mStart[1] * height);
      float x1 = mUseAbsoluteSizes ? mEnd[0] : (bounds.left + mEnd[0] * width);
      float y1 = mUseAbsoluteSizes ? mEnd[1] : (bounds.top + mEnd[1] * height);

      Log.d(ReactConstants.TAG, "GRD " + mText.substring(mTextStart, mTextEnd) + " " + bounds.toString() + " " + String.valueOf(bounds.width()) + " " + String.valueOf(bounds.height()) + String.format(Locale.ROOT, " w: %f h: %f x0: %f y0: %f x1: %f y1: %f", width, height, x0, y0, x1, y1));
      
      LinearGradient gradient = new LinearGradient(
        x0,
        y0,
        x1,
        y1,
        mColors,
        mLocations,
        Shader.TileMode.CLAMP
      );

      paint.setStyle(Paint.Style.FILL);
      paint.setShader(gradient);
    }
  }

//  private String textBoundsAsString(TextBounds bounds) {
//    return "TextBounds(t: " + bounds.top + " l: " + bounds.left + " b: " + bounds.bottom + " r: " + bounds.right + " h: " + bounds.height + " w: " + bounds.width + " )";
//  }

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
      float lineWidth = paint.measureText(text, lineStart, lineEnd) + lineOffset;
  
      if (lineWidth > mMaxWidth) {  
        if (lineEnd == 1) {
          bounds.top += lineHeight;
          bounds.left = 0;
        } else {
          bounds.width = Math.max(lineWidth, bounds.width);
          bounds.height += lineHeight;
        }

        lineStart = lineEnd - 1;
        lineOffset = 0;
        bounds.bottom += lineHeight;
        bounds.right = 0;
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
