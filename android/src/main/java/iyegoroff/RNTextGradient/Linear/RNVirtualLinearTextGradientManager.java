package iyegoroff.RNTextGradient.Linear;

import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import iyegoroff.RNTextGradient.RNVirtualTextGradientManager;
import iyegoroff.RNTextGradient.RNShadowTextGradient;
import android.util.Log;
import java.lang.String;
import com.facebook.react.common.ReactConstants;
import android.util.Log;
import com.facebook.react.common.ReactConstants;

@ReactModule(name = RNVirtualLinearTextGradientManager.REACT_CLASS)
public class RNVirtualLinearTextGradientManager extends RNVirtualTextGradientManager<RNVirtualShadowLinearTextGradient> {

  @VisibleForTesting
  public static final String REACT_CLASS = "RNVirtualLinearTextGradient";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public RNVirtualShadowLinearTextGradient createShadowNodeInstance() {  
    return new RNVirtualShadowLinearTextGradient();
  }

  @Override
  public Class<? extends RNVirtualShadowLinearTextGradient> getShadowNodeClass() {
    return RNVirtualShadowLinearTextGradient.class;
  }
}
