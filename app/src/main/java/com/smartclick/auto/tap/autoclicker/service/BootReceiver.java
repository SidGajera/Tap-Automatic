package com.smartclick.auto.tap.autoclicker.service;

import static com.smartclick.auto.tap.autoclicker.utils.StorageUtils.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootReceiver", "BOOT_COMPLETED received");

        if (Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) != null && Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ).contains(context.getPackageName() + "/.service.AutoClicker")) {

            Log.d("BootReceiver", "AccessibilityService already enabled");
            // Optionally start something like overlay or foreground service if needed
        } else {
            Log.d("BootReceiver", "AccessibilityService NOT enabled yet");
        }
    }
}
