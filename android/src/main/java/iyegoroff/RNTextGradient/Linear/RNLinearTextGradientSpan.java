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
import android.text.StaticLayout;
import android.text.Layout;

public class RNLinearTextGradientSpan extends CharacterStyle implements UpdateAppearance {
  private int[] mColors;
  private float[] mLocations;
  private float[] mStart;
  private float[] mEnd;
  private int mMaxWidth;
  private int mTextStart;
  private int mTextEnd;
  private String mText;

  public RNLinearTextGradientSpan(
    float[] locations,
    int[] colors,
    float[] start,
    float[] end,
    float maxWidth,
    int textStart,
    int textEnd,
    String text
  ) {
    mLocations = locations;
    mColors = colors;
    mStart = start;
    mEnd = end;
    mMaxWidth = (int)maxWidth;
    mTextStart = textStart;
    mTextEnd = textEnd;
    mText = text;
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
      // Rect textRect = new Rect();
      // paint.getTextBounds(mText, 0, mTextStart, textRect);
      // paint.getTextBounds(mText, mTextStart, mTextEnd, textRect);

      // StaticLayout layout = new StaticLayout(mText, paint, (int) Math.ceil(mMaxWidth), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
      // float width = Math.min(textRect.width(), layout.getWidth());
      // float height = layout.getHeight();
      // Log.d(ReactConstants.TAG, mText + " w-" + String.valueOf(width) + " h-" + String.valueOf(height));
      // // Log.d(ReactConstants.TAG, "s " + String.valueOf(mTextStart) + "e " + String.valueOf(mTextEnd) + "multi " + String.valueOf(isMultiline(paint)));
      // if (isMultiline(paint)) {
      //   Log.d(ReactConstants.TAG, "multi " + String.valueOf(multiLineTextBounds(paint)));
      // } else {
      //   Log.d(ReactConstants.TAG, "single " + String.valueOf(singleLineTextBounds(paint)));
      // }

      Rect rect = isMultiline(paint) ? multiLineTextBounds(paint) : singleLineTextBounds(paint);

      Log.d(ReactConstants.TAG, mText.substring(mTextStart, mTextEnd) + ": "
      + String.valueOf(rect) + " "
      + String.valueOf(rect.width()) + " "
      + String.valueOf(rect.height()));

      LinearGradient gradient = new LinearGradient(
        rect.left + mStart[0] * rect.width(),
        rect.top + mStart[1] * rect.height(),
        rect.left + mEnd[0] * rect.width(),
        rect.top + mEnd[1] * rect.height(),
        mColors,
        mLocations,
        Shader.TileMode.CLAMP
      );

      paint.setStyle(Paint.Style.FILL);
      paint.setShader(gradient);
    }
  }

  private boolean isMultiline(TextPaint paint) {
    float x = rawPreviousTextBounds(paint).width() % mMaxWidth;

    return (rawTextBounds(paint).width() + x) > mMaxWidth;
  }

  private Rect singleLineTextBounds(TextPaint paint) {
    Rect rawBounds = rawTextBounds(paint);
    Rect rawPrevBounds = rawPreviousTextBounds(paint);
    int lineHeight = rawBounds.height();

    int left = rawPrevBounds.width();
    int top = (left / mMaxWidth) * lineHeight;
    int right = (left + rawBounds.width());
    int bottom = (int)Math.ceil((float)right / (float)mMaxWidth) * lineHeight;

    return new Rect(
      left % mMaxWidth,
      top,
      right % mMaxWidth == 0 ? right : right % mMaxWidth,
      bottom
    );
  }

  private Rect multiLineTextBounds(TextPaint paint) {
    Rect rawBounds = rawTextBounds(paint);
    Rect rawPrevBounds = rawPreviousTextBounds(paint);
    int lineHeight = rawBounds.height();

    int left = 0;
    int top = (rawPrevBounds.width() / mMaxWidth) * lineHeight;
    int right = mMaxWidth;
    int bottom = (int)Math.ceil((float)(rawPrevBounds.width() + rawBounds.width()) / (float)mMaxWidth) * lineHeight;

    return new Rect(left, top, right, bottom);
  }

  private Rect rawPreviousTextBounds(TextPaint paint) {
    Rect textRect = new Rect();
    paint.getTextBounds(mText, 0, mTextStart, textRect);

    return textRect;
  }

  private Rect rawTextBounds(TextPaint paint) {
    Rect textRect = new Rect();
    paint.getTextBounds(mText, mTextStart, mTextEnd, textRect);

    return textRect;
  }
}
