package iyegoroff.RNTextGradient;

import android.content.Context;
import com.facebook.react.views.text.ReactTextView;
import android.util.Log;
import java.lang.String;
import com.facebook.react.common.ReactConstants;
import android.text.SpannableString;
import android.text.Layout;
import android.graphics.Canvas;

public class RNTextGradient extends ReactTextView {

  public RNTextGradient(Context context) {
    super(context);
  }

  @Override
  public void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Layout layout = getLayout();

    if (layout != null) {
      float width = layout.getLineWidth(0);
      Log.d(ReactConstants.TAG, "width " + String.valueOf(width));
    }
  }
}
