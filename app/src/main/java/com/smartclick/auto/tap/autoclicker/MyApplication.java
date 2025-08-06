package com.smartclick.auto.tap.autoclicker;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smartclick.auto.tap.autoclicker.manager.GoogleAds;
import com.smartclick.auto.tap.autoclicker.manager.OpenAppManager;
import com.smartclick.auto.tap.autoclicker.model.AdsItem;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public MyApplication instance = null;
    public static Activity currentActivity, googleActivity;
    public static boolean isAdShow, isAppOpenAdShow;
    public static Map<String, List<String>> adUnitsMapSplash = new HashMap<>();
    public static Map<String, List<String>> adUnitsMapLanguage = new HashMap<>();
    public static Map<String, List<String>> adUnitsMapIntro = new HashMap<>();
    public static Map<String, List<String>> adUnitsMapPermission = new HashMap<>();
    public static Map<String, List<String>> adUnitsMapHome = new HashMap<>();
    public static Map<String, List<String>> adUnitsMapSetting = new HashMap<>();
    public static Map<String, List<String>> adUnitsMapInstruction = new HashMap<>();
    public static GoogleAds googleAds;
    private final String TAG = MyApplication.class.getName();
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;
    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    @Override // demo.ads.AdsApplication
    public void onCreate() {
        super.onCreate();
        instance = this;
        googleAds = GoogleAds.getInstance();
        MobileAds.initialize(this);
        new OpenAppManager(this);
        FirebaseAnalytics.getInstance(this);
        registerActivityLifecycleCallbacks(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {}

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (++activityReferences == 1 && !isActivityChangingConfigurations) {
                    // App enters foreground
                    Log.d(TAG, "AppStatus ==> App in foreground");
                }
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                isActivityChangingConfigurations = activity.isChangingConfigurations();
                if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                    // App enters background
                    Log.d(TAG, "AppStatus ==> App in background");
                }
            }

            // other overrides can be empty
            public void onActivityResumed(@NonNull Activity activity) {
                Log.d(TAG, "AppStatus ==> App resumed");
            }
            public void onActivityPaused(@NonNull Activity activity) {
                Log.d(TAG, "AppStatus ==> App paused");
            }
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                Log.d(TAG, "AppStatus ==> App save state");
            }
            public void onActivityDestroyed(@NonNull Activity activity) {
                Log.d(TAG, "AppStatus ==> App destroyed");
            }
        });

    }

    public boolean isAppRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo process : processes) {
            if (process.processName.equals(context.getPackageName())) {
                return true; // App process is still running
            }
        }
        return false; // App process is dead
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: " + activity.getLocalClassName());
        googleActivity = activity;
        if (activity.getLocalClassName().equals("com.google.android.gms.ads.AdActivity")) return;
        if (currentActivity != null && currentActivity.getLocalClassName().equals(activity.getLocalClassName())) {
            Log.e(TAG, "onActivityCreated: " + currentActivity.getLocalClassName());
            return;
        }
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    public static int getIndex(String nameAds, @NonNull ArrayList<AdsItem> list) {
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(nameAds, list.get(i).name)) {
                return i;
            }
        }
        return 0;
    }
}
