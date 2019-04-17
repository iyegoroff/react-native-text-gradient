package iyegoroff.RNTextGradient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import iyegoroff.RNTextGradient.Linear.RNLinearTextGradientManager;
import iyegoroff.RNTextGradient.Linear.RNVirtualLinearTextGradientManager;

public class RNTextGradientPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
      return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
      return Arrays.<ViewManager>asList(
        new RNLinearTextGradientManager(),
        new RNVirtualLinearTextGradientManager()
      );
    }
}