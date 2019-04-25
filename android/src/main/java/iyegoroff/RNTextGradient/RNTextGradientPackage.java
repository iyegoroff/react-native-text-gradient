package iyegoroff.RNTextGradient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import javax.annotation.Nonnull;

import iyegoroff.RNTextGradient.Linear.RNLinearTextGradientManager;
import iyegoroff.RNTextGradient.Linear.RNVirtualLinearTextGradientManager;

public class RNTextGradientPackage implements ReactPackage {
    @Override
    public @Nonnull List<NativeModule> createNativeModules(
      @Nonnull ReactApplicationContext reactContext
    ) {
      return Collections.emptyList();
    }

    @Override
    public @Nonnull List<ViewManager> createViewManagers(
      @Nonnull ReactApplicationContext reactContext
    ) {
      return Arrays.<ViewManager>asList(
        new RNLinearTextGradientManager(),
        new RNVirtualLinearTextGradientManager()
      );
    }
}