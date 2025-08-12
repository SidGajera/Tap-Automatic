package com.smartclick.auto.tap.autoclicker.manager;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.smartclick.auto.tap.autoclicker.MyApplication;
import com.smartclick.auto.tap.autoclicker.model.AdType;

import java.util.ArrayList;
import java.util.List;

public class OpenAppManager implements DefaultLifecycleObserver {
    private final Application application;
    private AppOpenAd appOpenAd = null;
    private boolean isShowingAd = false;
    public List<String> appOpenIds;
    public int appOpenIndex = 0;

    String TAG = OpenAppManager.class.getName()+"_Log";

    public OpenAppManager(Application application) {
        this.application = application;
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        Log.d(TAG, "setShowingAd OpenAppManager");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "setShowingAd onStart");
        if (MyApplication.isAdShow
                && !MyApplication.googleActivity.getLocalClassName().equals("com.google.android.gms.ads.AdActivity")) {
            showAdIfAvailable();
        }
    }

    public void showAdIfAvailable() {
        if (MyApplication.getCurrentActivity() == null) {
            MyApplication.isAppOpenAdShow = false;
            return;
        }
        if (MyApplication.googleAds.isLoadingShowing()) {
            MyApplication.isAppOpenAdShow = false;
            return;
        }
        appOpenIndex = 0;
        String localClassName = MyApplication.getCurrentActivity().getLocalClassName();
        Log.e(TAG, "showAdIfAvailable: localClassName = "+localClassName);
        switch (localClassName) {
            case "activity.SplashActivity":
//                MyApplication.isAppOpenAdShow = false;
//                appOpenIds = new ArrayList<>();
                 appOpenIds = MyApplication.adUnitsMapSplash.get(AdType.APP_OPEN);
                break;
            case "activity.IntroActivity":
                appOpenIds = MyApplication.adUnitsMapIntro.get(AdType.APP_OPEN);
                break;
            case "activity.LanguageActivity":
                appOpenIds = MyApplication.adUnitsMapLanguage.get(AdType.APP_OPEN);
                break;
            case "activity.PermissionActivity":
                appOpenIds = MyApplication.adUnitsMapPermission.get(AdType.APP_OPEN);
                break;
            case "activity.StartActivity":
                appOpenIds = MyApplication.adUnitsMapHome.get(AdType.APP_OPEN);
                break;
            case "activity.SingleModeInstructionActivity":
            case "activity.MultiModeInstructionActivity":
                appOpenIds = MyApplication.adUnitsMapInstruction.get(AdType.APP_OPEN);
                break;
            default:
                appOpenIds = new ArrayList<>();
                break;
        }
        setShowingAd();
    }

    public void setShowingAd() {
        if (!isShowingAd && isAdAvailable()) {
            appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    appOpenAd = null;
                    isShowingAd = false;
                    MyApplication.isAppOpenAdShow = false;
                    Log.d(TAG, "setShowingAd onAdDismissedFullScreenContent");
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    isShowingAd = false;
                    MyApplication.isAppOpenAdShow = false;
                    Log.d(TAG, "setShowingAd onAdFailedToShowFullScreenContent");
                    Toast.makeText(application, "appOpen fails to load for unitID: "
                            +appOpenAd.getAdUnitId()+" for error: "+adError.getMessage() , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                    Log.d(TAG, "setShowingAd onAdShowedFullScreenContent");
                }
            });
            GoogleAds.getInstance().hideLoading();
            appOpenAd.show(MyApplication.getCurrentActivity());
        } else {
            loadAd();
        }
    }

    private void loadAd() {
        if (isAdAvailable()) return;
        MyApplication.isAppOpenAdShow = true;
        if (appOpenIds == null || appOpenIds.isEmpty()) {
            Log.d(TAG, "loadAd loadAd: No App Open Ads Available");
            MyApplication.isAppOpenAdShow = false;
            return;
        }

        if (appOpenIndex >= appOpenIds.size()) {
            Log.d(TAG, "loadAd loadAd: All App Open Ads Failed");
            MyApplication.isAppOpenAdShow = false;
            return;
        }

        String adUnitId = appOpenIds.get(appOpenIndex);
        Log.d(TAG, "OpenAppManager loadAd appOpenIndex = "+appOpenIndex + " Trying Ad Unit: " + adUnitId);

        GoogleAds.getInstance().showLoading(MyApplication.getCurrentActivity(), false);
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(application, adUnitId, request, new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                appOpenAd = ad;
                setShowingAd();
                Log.d(TAG, "OpenAppManager loadAd onAdLoaded ad = " + ad);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError error) {
                Log.d(TAG, "OpenAppManager loadAd onAdFailedToLoad " + error.getMessage());
                Toast.makeText(application, "appOpen fails to load for unitID: "
                        +appOpenAd.getAdUnitId()+" for error: "+error.getMessage() , Toast.LENGTH_SHORT).show();
                appOpenAd = null;
                appOpenIndex++;
                loadAd();
            }
        });
    }

    private boolean isAdAvailable() {
        Log.d(TAG, "OpenAppManager isAdAvailable");
        return appOpenAd != null;
    }
}
