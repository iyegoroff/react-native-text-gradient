package iyegoroff.RNTextGradient;

import android.content.Context;
import com.facebook.react.views.text.ReactTextView;
import android.util.Log;
import java.lang.String;
import com.facebook.react.common.ReactConstants;
import android.text.SpannableString;

public class RNTextGradient extends ReactTextView {

  public RNTextGradient(Context context) {
    super(context);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    // Log.d(ReactConstants.TAG, "view " + String.valueOf(w) + " " + String.valueOf(h));
    // Log.d(ReactConstants.TAG, String.valueOf(getText() instanceof SpannableString));
  }
}
