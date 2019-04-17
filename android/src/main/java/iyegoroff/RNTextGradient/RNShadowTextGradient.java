package iyegoroff.RNTextGradient;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.views.text.ReactTextShadowNode;
import com.facebook.react.views.text.ReactBaseTextShadowNode;
import com.facebook.react.views.text.ReactRawTextShadowNode;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.lang.Exception;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.react.common.ReactConstants;
import java.lang.String;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import java.util.ArrayList;
import java.util.List;
import com.facebook.react.uimanager.UIViewOperationQueue;
import android.util.Log;
import android.view.View;

import com.facebook.react.common.ReactConstants;

public abstract class RNShadowTextGradient extends ReactTextShadowNode {

  protected float[] mLocations;
  protected int[] mColors;
  protected boolean mUseViewFrame;
  protected boolean mUseAbsoluteSizes;
  private WeakReference<ReactApplicationContext> mContext;

  public RNShadowTextGradient(ReactApplicationContext context) {
    super();

    mContext = new WeakReference<>(context);
  }

  private @Nullable View resolveView(int tag) {
    UiThreadUtil.assertOnUiThread();
    ReactApplicationContext context = mContext.get();

    if (context != null) {
      UIManagerModule uiManager = context.getNativeModule(UIManagerModule.class);

      NativeViewHierarchyManager manager = ReflectUtils.getFieldValue(
        ReflectUtils.getFieldValue(
          uiManager.getUIImplementation(),
          "mOperationsQueue",
          null
        ),
        "mNativeViewHierarchyManager",
        null
      );

      if (manager != null) {
        return manager.resolveView(tag);
      }
    }

    return null;
  }

  @ReactProp(name = "locations")
  public void setLocations(ReadableArray locations) {
    if (locations != null) {
      float[] _locations = new float[locations.size()];

      for (int i = 0; i < _locations.length; i++) {
        _locations[i] = (float) locations.getDouble(i);
      }

      mLocations = _locations;
      
    } else {
      mLocations = null;
    }

    markUpdated();
  }

  @ReactProp(name = "colors")
  public void setColors(ReadableArray colors) {
    if (colors != null) {
      int[] _colors = new int[colors.size()];

      for (int i = 0; i < _colors.length; i++) {
        _colors[i] = colors.getInt(i);
      }

      mColors = _colors;

    } else {
      mColors = null;
    }

    markUpdated();    
  }

  @ReactProp(name = "useViewFrame")
  public void setUseViewFrame(boolean useViewFrame) {
    mUseViewFrame = useViewFrame;

    markUpdated();
  }

  @ReactProp(name = "useAbsoluteSizes")
  public void setUseAbsoluteSizes(boolean useAbsoluteSizes) {
    mUseAbsoluteSizes = useAbsoluteSizes;

    markUpdated();
  }

  @Override
  public boolean dispatchUpdates(
    float absoluteX,
    float absoluteY,
    UIViewOperationQueue uiViewOperationQueue,
    NativeViewHierarchyOptimizer nativeViewHierarchyOptimizer
  ) {
    boolean layoutHasChanged = super.dispatchUpdates(
      absoluteX,
      absoluteY,
      uiViewOperationQueue,
      nativeViewHierarchyOptimizer
    );

    if (layoutHasChanged) {
      markUpdated();
    }

    return layoutHasChanged;
  }

  @Override
  public void onCollectExtraUpdates(UIViewOperationQueue uiViewOperationQueue) {
    updateGradient();

    super.onCollectExtraUpdates(uiViewOperationQueue);
  }

  private void updateGradient() {
    if (!isVirtual()) {
      final RNShadowTextGradient that = this;
      UiThreadUtil.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          String fieldName = "mPreparedSpannableText";

          Log.d(ReactConstants.TAG, "GRD view " + String.valueOf(that.resolveView(getReactTag())));

          ReflectUtils.setFieldValue(
                  this,
                  fieldName,
                  spannableWithGradient(
                          (Spannable) ReflectUtils.getFieldValue(this, fieldName, ReactTextShadowNode.class),
                          that,
                          getLayoutWidth(),
                          getLayoutHeight(),
                          (int) ReflectUtils.invokeMethod(this, "getTextAlign", ReactTextShadowNode.class),
                          mTextBreakStrategy,
                          mIncludeFontPadding
                  ),
                  ReactTextShadowNode.class
          );
        }
      });
    }
  }

  protected abstract RNSetGradientSpanOperation createSpan(
    SpannableStringBuilder builder,
    int start,
    int end,
    float maxWidth,
    float maxHeight,
    int alignment,
    int textBreakStrategy,
    boolean includeFontPadding
  );

  protected static Spannable spannableWithGradient(
    Spannable spannable,
    RNShadowTextGradient textCSSNode,
    float maxWidth,
    float maxHeight,
    int alignment,
    int textBreakStrategy,
    boolean includeFontPadding
  ) {
    List<RNSetGradientSpanOperation> ops = new ArrayList<>();
    SpannableStringBuilder gradientBuilder = new SpannableStringBuilder();
    buildSpannedGradientFromTextCSSNode(
      textCSSNode,
      gradientBuilder,
      ops,
      maxWidth,
      maxHeight,
      alignment,
      textBreakStrategy,
      includeFontPadding
    );

    for (int i = ops.size() - 1; i >= 0; i--) {
    //for (int i = 0; i < ops.size(); i++) {
      ops.get(i).execute((SpannableStringBuilder) spannable);
    }

    return spannable;
  }

  protected static void buildSpannedGradientFromTextCSSNode(
    ReactBaseTextShadowNode textGradientShadowNode,
    SpannableStringBuilder builder,
    List<RNSetGradientSpanOperation> ops,
    float maxWidth,
    float maxHeight,
    int alignment,
    int textBreakStrategy,
    boolean includeFontPadding
  ) {
    int start = builder.length();

    for (int i = 0, length = textGradientShadowNode.getChildCount(); i < length; i++) {
      ReactShadowNode child = textGradientShadowNode.getChildAt(i);

      if (child instanceof ReactRawTextShadowNode) {
        builder.append(((ReactRawTextShadowNode) child).getText());

      } else if (child instanceof ReactBaseTextShadowNode) {
        buildSpannedGradientFromTextCSSNode(
          (ReactBaseTextShadowNode) child,
          builder,
          ops,
          maxWidth,
          maxHeight,
          alignment,
          textBreakStrategy,
          includeFontPadding
        );

      } else if (child instanceof ReactTextInlineImageShadowNode) {
        builder.append(
          (String) ReflectUtils.getFieldValue(textGradientShadowNode, "INLINE_IMAGE_PLACEHOLDER", ReactTextShadowNode.class)
        );
      }

      child.markUpdateSeen();
    }

    int end = builder.length();

    if (end >= start && textGradientShadowNode instanceof RNShadowTextGradient) {
      RNSetGradientSpanOperation spanOp = ((RNShadowTextGradient) textGradientShadowNode)
        .createSpan(
          builder,
          start,
          end,
          maxWidth,
          maxHeight,
          alignment,
          textBreakStrategy,
          includeFontPadding
        );
      // Log.d(ReactConstants.TAG, "ADD SPAN " + String.valueOf(start) + " - " + String.valueOf(end));

      ops.add(spanOp);
    }
  }
}
