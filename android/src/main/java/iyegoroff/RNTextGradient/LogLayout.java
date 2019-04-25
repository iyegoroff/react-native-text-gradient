package iyegoroff.RNTextGradient;

import android.graphics.Path;
import android.graphics.RectF;
import android.text.Layout;
import android.util.Log;

import com.facebook.react.common.ReactConstants;

public class LogLayout {

    public static void log(Layout layout, int start, int end, String pref) {
        if (layout != null) {
            Path path = new Path();
            RectF bounds = new RectF();
            layout.getSelectionPath(start, end, path);
            path.computeBounds(bounds, true);
            Log.d(ReactConstants.TAG, "GRD LAYOUT " + pref + " " + bounds.toString());
        } else {
            Log.d(ReactConstants.TAG, "GRD LAYOUT " + pref + " NULL");
        }
    }
}
