package iyegoroff.RNTextGradient;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;
import android.text.style.LineHeightSpan;
import android.util.Log;

import com.facebook.react.common.ReactConstants;

public class RNMeasureSizeSpan extends ReplacementSpan implements LineHeightSpan {
    @Override
    public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        return (int) paint.measureText(text, start, end);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        Log.d(ReactConstants.TAG, "x " + String.valueOf(x) + ", y " + String.valueOf(y) + ", top " + String.valueOf(top) + ", bottom " + String.valueOf(bottom));
        canvas.drawText(text, start, end, x, y, paint);
        Log.d(ReactConstants.TAG, String.valueOf(paint.breakText(text.toString())))
    }
}
