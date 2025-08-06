package com.smartclick.auto.tap.autoclicker.utils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;

import androidx.core.content.ContextCompat;

import java.util.List;

public class Common {
    public static String[] permissions;
    public static final String ACTION_UPDATE_UI = "ACTION_UPDATE_UI";

    public static boolean isAccessibilitySettingsOn(Context context) {
        int i;
        String string;
        try {
            i = Settings.Secure.getInt(context.getContentResolver(), "accessibility_enabled");
        } catch (Settings.SettingNotFoundException unused) {
            i = 0;
        }
        if (i != 1 || (string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services")) == null) {
            return false;
        }
        return string.toLowerCase().contains(context.getPackageName().toLowerCase());
    }

    public static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        ComponentName expectedComponentName = new ComponentName(context, service);

        String enabledServices = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        );

        if (enabledServices == null) return false;

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');
        colonSplitter.setString(enabledServices);

        while (colonSplitter.hasNext()) {
            String componentName = colonSplitter.next();
            if (componentName.equalsIgnoreCase(expectedComponentName.flattenToString())) {
                return true;
            }
        }
        return false;
    }


    public static boolean checkStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= 33) {
            permissions = new String[]{"android.permission.POST_NOTIFICATIONS", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_AUDIO"};
        } else {
            permissions = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
        }
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    public static void getPermissionAccessibilty(Activity activity, int i) {
        activity.startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
    }

    public static boolean isMyServiceRunning(Context context, Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void getOverlayPermission(Activity activity, int i) {
        Boolean.valueOf(true);
        Context applicationContext = activity.getApplicationContext();
        if (!Settings.canDrawOverlays(applicationContext)) {
            Boolean.valueOf(false);
            activity.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + applicationContext.getPackageName())), i);
            return;
        }
        Boolean.valueOf(true);
    }

    public static Boolean checkOverlay(Activity activity) {
        Boolean.valueOf(true);
        return Boolean.valueOf(Settings.canDrawOverlays(activity.getApplicationContext()));
    }
}
