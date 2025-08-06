package com.smartclick.auto.tap.autoclicker.service;

import static com.smartclick.auto.tap.autoclicker.utils.StorageUtils.TAG;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class AutoClicker extends AccessibilityService {
    public static AutoClicker instance;
    String TAG = AutoClicker.class.getName();

    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    public void onInterrupt() {
    }

    /* access modifiers changed from: protected */
    public void onServiceConnected() {
        super.onServiceConnected();
        setAutoClickService(this);
        Log.d(TAG, "Accessibility Service connected");

        new Handler().postDelayed(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(this, SingleModeService.class));
            } else {
                startService(new Intent(this, SingleModeService.class));
            }
        }, 300); // 300ms delay
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "Service App removed from recent. Stopping service...");
        stopSelf(); // stop the service
        super.onTaskRemoved(rootIntent);
    }

    //Implemented Accessibility Service
    //Accessibility Service permission only once, and keep it remembered across app launches — like other stable auto clicker apps.
    public void onDestroy() {
        if (instance != null) {
            stopService(new Intent(getApplicationContext(), AutoClicker.class));
        }
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        if (instance != null) {
            instance = null;
        }
        return super.onUnbind(intent);
    }

    public static AutoClicker getAutoClickService() {
        return instance;
    }

    public static void setAutoClickService(AutoClicker autoClicker) {
        instance = autoClicker;
    }

    public final void clickAt(float f, float f2) {
        Log.d("TAG", "clickAt: " + f + "  -  " + f2);
        Path path = new Path();
        path.moveTo(f, f2);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 100));
        dispatchGesture(builder.build(), null, null);
    }

    public final void swipe(int i, int i2, int i3, int i4, int i5) {
        Log.d("TAG", "swipe To = X1.Y1  : " + i + " : " + i2 + "   |  X2.Y2 : " + i3 + " : " + i4);
        Path path = new Path();
        path.moveTo((float) i, (float) i2);
        path.lineTo((float) i3, (float) i4);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, (long) i5));
        dispatchGesture(builder.build(), null, null);
    }
}
