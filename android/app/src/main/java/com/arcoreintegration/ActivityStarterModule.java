package com.arcoreintegration;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nonnull;

public class ActivityStarterModule extends ReactContextBaseJavaModule {

    private static DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter = null;
    ReactApplicationContext reactContext;

    public ActivityStarterModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

    }

    @Override
    public void initialize() {
        super.initialize();
        eventEmitter = getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    @Override
    public String getName() {
        return "ActivityStarter";
    }


    @ReactMethod
    void launchArActivity(@Nonnull String assetId, @Nonnull String assetUrl) {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, ARActivity.class);
            intent.putExtra("ASSET_URL", assetUrl);
            activity.startActivity(intent);
        }
    }

    @ReactMethod
    void launchArFacesActivity() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, ARFacesActivity.class);
            activity.startActivity(intent);
        }
    }


    @ReactMethod
    void launchARAnimatedModelView() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, ARAnimation.class);

            activity.startActivity(intent);
        }
    }

    static void triggerAlert(@Nonnull String message) {
        eventEmitter.emit("MyEventValue", message);
    }
}

