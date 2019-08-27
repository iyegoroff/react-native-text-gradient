package iyegoroff.RNTextGradient;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.Spacing;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.views.text.ReactTextShadowNode;
import com.facebook.react.views.text.ReactBaseTextShadowNode;
import com.facebook.react.views.text.ReactRawTextShadowNode;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.Layout;
import java.lang.String;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import java.util.ArrayList;
import java.util.List;

import com.facebook.react.uimanager.UIViewOperationQueue;

import android.text.TextWatcher;
import android.view.View;

import com.facebook.react.views.text.ReactTextUpdate;

public abstract class RNShadowTextGradient extends ReactTextShadowNode {

  private static class TextChangeListener extends OneOffListener implements TextWatcher {

    TextChangeListener(Runnable update) {
      super(update);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
      trigger();
    }
  }

  private static class LayoutChangeListener extends OneOffListener implements View.OnLayoutChangeListener {

    LayoutChangeListener(Runnable update) {
      super(update);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
      trigger();
    }
  }

  private final static String sSpannableField = "mPreparedSpannableText";

  protected float[] mLocations;
  protected int[] mColors;
  protected boolean mUseViewFrame;
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

  @SuppressWarnings("unused")
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

  @SuppressWarnings("unused")
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

  @SuppressWarnings("unused")
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

  private Spannable updatedSpannable(Layout layout) {
    return spannableWithGradient(
      getSpannable(),
      this,
      getLayoutWidth(),
      getLayoutHeight(),
      layout
    );
  }

  private Spannable getSpannable() {
    return ReflectUtils.getFieldValue(this, sSpannableField, ReactTextShadowNode.class);
  }

  @Override
  public void onCollectExtraUpdates(final UIViewOperationQueue uiViewOperationQueue) {
    final RNShadowTextGradient that = this;

    uiViewOperationQueue.enqueueUIBlock(new UIBlock() {
      @Override
      public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
        final RNTextGradient view = (RNTextGradient) that.resolveView(getReactTag());

        if (view != null) {
          Layout layout = view.getLayout();

          if (layout != null) {
            ReactTextUpdate textUpdate = new ReactTextUpdate(
              that.updatedSpannable(layout),
              UNSET,
              that.mContainsImages,
              that.getPadding(Spacing.START),
              that.getPadding(Spacing.TOP),
              that.getPadding(Spacing.END),
              that.getPadding(Spacing.BOTTOM),
              (int) ReflectUtils.invokeMethod(that, "getTextAlign", ReactTextShadowNode.class),
              that.mTextBreakStrategy,
              that.mJustificationMode
            );

            view.setText(textUpdate);
          }

          Runnable update = new Runnable() {
            @Override
            public void run() {
              Runnable exec = new Runnable() {
                @Override
                public void run() {
                  Layout layout = view.getLayout();

                  ReactTextUpdate textUpdate = new ReactTextUpdate(
                    that.updatedSpannable(layout),
                    UNSET,
                    that.mContainsImages,
                    that.getPadding(Spacing.START),
                    that.getPadding(Spacing.TOP),
                    that.getPadding(Spacing.END),
                    that.getPadding(Spacing.BOTTOM),
                    (int) ReflectUtils.invokeMethod(
                      that,
                      "getTextAlign",
                      ReactTextShadowNode.class
                    ),
                    that.mTextBreakStrategy,
                    that.mJustificationMode
                  );

                  view.setText(textUpdate);
                }
              };

              if (view.getLayout() != null) {
                exec.run();
              } else {
                UiThreadUtil.runOnUiThread(exec);
              }
            }
          };

          final LayoutChangeListener layoutListener = new LayoutChangeListener(update);

          view.addOnLayoutChangeListener(layoutListener);

          layoutListener.addRemoval(new Runnable() {
            @Override
            public void run() {
              view.removeOnLayoutChangeListener(layoutListener);
            }
          });

          final TextChangeListener textListener = new TextChangeListener(update);

          view.addTextChangedListener(textListener);

          textListener.addRemoval(new Runnable() {
            @Override
            public void run() {
              view.removeTextChangedListener(textListener);
            }
          });
        }
      }
    });

    super.onCollectExtraUpdates(uiViewOperationQueue);
  }

  protected abstract RNSetGradientSpanOperation createSpan(
    SpannableStringBuilder builder,
    int start,
    int end,
    float maxWidth,
    float maxHeight,
    Layout layout
  );

  private static Spannable spannableWithGradient(
    Spannable spannable,
    RNShadowTextGradient textCSSNode,
    float maxWidth,
    float maxHeight,
    Layout layout
  ) {
    List<RNSetGradientSpanOperation> ops = new ArrayList<>();
    SpannableStringBuilder gradientBuilder = new SpannableStringBuilder();
    buildSpannedGradientFromTextCSSNode(
      textCSSNode,
      gradientBuilder,
      ops,
      maxWidth,
      maxHeight,
      layout
    );

    for (int i = ops.size() - 1; i >= 0; i--) {
      ops.get(i).execute((SpannableStringBuilder) spannable);
    }

    return spannable;
  }

  private static void buildSpannedGradientFromTextCSSNode(
    ReactBaseTextShadowNode textGradientShadowNode,
    SpannableStringBuilder builder,
    List<RNSetGradientSpanOperation> ops,
    float maxWidth,
    float maxHeight,
    Layout layout
  ) {
    int start = builder.length();

    for (int i = 0; i < textGradientShadowNode.getChildCount(); i++) {
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
          layout
        );

      } else if (child instanceof ReactTextInlineImageShadowNode) {
        builder.append(
          (String) ReflectUtils.getFieldValue(
            textGradientShadowNode,
            "INLINE_IMAGE_PLACEHOLDER",
            ReactTextShadowNode.class
          )
        );
      }

      child.markUpdateSeen();
    }

    int end = builder.length();

    if (end >= start && textGradientShadowNode instanceof RNShadowTextGradient) {
      RNSetGradientSpanOperation spanOp = ((RNShadowTextGradient) textGradientShadowNode)
        .createSpan(builder, start, end, maxWidth, maxHeight, layout);

      ops.add(spanOp);
    }
  }
}
