package iyegoroff.RNTextGradient;

import com.facebook.react.views.text.ReactTextShadowNode;
import com.facebook.react.views.text.ReactBaseTextShadowNode;
import com.facebook.react.views.text.ReactRawTextShadowNode;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.lang.Exception;
import java.lang.reflect.Field;
import android.util.Log;
import com.facebook.react.common.ReactConstants;
import java.lang.String;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import java.util.ArrayList;
import java.util.List;
import com.facebook.react.uimanager.UIViewOperationQueue;

public abstract class RNShadowTextGradient extends ReactTextShadowNode {

  protected float[] mLocations;
  protected int[] mColors;
  protected boolean mUseViewFrame;

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
      String fieldName = "mPreparedSpannableText";
        
      setParentFieldValue(
        this,
        fieldName,
        spannableWithGradient(
          (Spannable) getParentFieldValue(this, fieldName),
          this,
          getLayoutWidth(),
          getLayoutHeight()
        )
      );  
    }
  }

  protected abstract RNSetGradientSpanOperation createSpan(
    SpannableStringBuilder builder,
    int start,
    int end,
    float maxWidth,
    float maxHeight,
    float lineHeight
  );

  protected static Spannable spannableWithGradient(
    Spannable spannable,
    RNShadowTextGradient textCSSNode,
    float maxWidth,
    float maxHeight
  ) {
    List<RNSetGradientSpanOperation> ops = new ArrayList<>();
    SpannableStringBuilder gradientBuilder = new SpannableStringBuilder();
    buildSpannedGradientFromTextCSSNode(textCSSNode, gradientBuilder, ops, maxWidth, maxHeight);

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
    float maxHeight
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
          maxHeight
        );

      } else if (child instanceof ReactTextInlineImageShadowNode) {
        builder.append(
          (String) getParentFieldValue(textGradientShadowNode, "INLINE_IMAGE_PLACEHOLDER")
        );
      }

      child.markUpdateSeen();
    }

    int end = builder.length();

    if (end >= start && textGradientShadowNode instanceof RNShadowTextGradient) {
      float lineHeight = textGradientShadowNode.getEffectiveLineHeight();
      RNSetGradientSpanOperation spanOp = ((RNShadowTextGradient) textGradientShadowNode)
        .createSpan(builder, start, end, maxWidth, maxHeight, lineHeight);

     ops.add(spanOp);
    }
  }

  private static <T> T getParentFieldValue(Object target, String name) {
    try {
      Field field = ReactTextShadowNode.class.getDeclaredField(name);
      field.setAccessible(true);

      return (T) field.get(target);

    } catch (Exception e) {
      Log.d(ReactConstants.TAG, "Can't get ReactTextShadowNode field " + name);
      Log.d(ReactConstants.TAG, e.getMessage());
    }

    return null;
  }


  private static <T> void setParentFieldValue(Object target, String name, T value) {
    try {
      Field field = ReactTextShadowNode.class.getDeclaredField(name);
      field.setAccessible(true);
      field.set(target, value);

    } catch (Exception e) {
      Log.d(ReactConstants.TAG, "Can't set ReactTextShadowNode field " + name);
      Log.d(ReactConstants.TAG, e.getMessage());
    }
  }
}
