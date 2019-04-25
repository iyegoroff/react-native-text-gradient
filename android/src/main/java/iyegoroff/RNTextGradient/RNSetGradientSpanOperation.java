package iyegoroff.RNTextGradient;

import android.text.Spannable;
import android.text.SpannableStringBuilder;

public class RNSetGradientSpanOperation {
  private int start, end;
  private Object what;

  public RNSetGradientSpanOperation(int start, int end, Object what) {
    this.start = start;
    this.end = end;
    this.what = what;
  }

  void execute(SpannableStringBuilder sb) {
    // All spans will automatically extend to the right of the text, but not the left - except
    // for spans that start at the beginning of the text.
    int spanFlags = Spannable.SPAN_EXCLUSIVE_INCLUSIVE;
    if (start == 0) {
      spanFlags = Spannable.SPAN_INCLUSIVE_INCLUSIVE;
    }

    if (end <= sb.length()) {
      sb.setSpan(what, start, end, spanFlags);
    }
  }
}
