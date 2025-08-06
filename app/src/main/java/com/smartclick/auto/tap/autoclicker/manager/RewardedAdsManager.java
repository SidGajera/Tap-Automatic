package com.smartclick.auto.tap.autoclicker.manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.smartclick.auto.tap.autoclicker.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Objects;

public class RewardedAdsManager {

    private static final String TAG = "RewardAdManager";
    private final Context context;
    private final String adUnitId;
    private RewardedAd rewardedAd;
    private boolean isLoading = false;
    private boolean isAdShowing = false;

    public interface RewardAdListener {
        void onAdClosed();
        void onUserEarnedReward(RewardItem reward);
        void onAdFailedToShow();
        void onAdLoaded();
        void onFailedToLoad();
    }

    public RewardedAdsManager(Context context, String adUnitId) {
        this.context = context.getApplicationContext();
        this.adUnitId = adUnitId;
        MobileAds.initialize(context);
        loadAd();
    }

    public void loadAd() {
        if (isLoading || rewardedAd != null) return;

        isLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, adUnitId, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                Log.d(TAG, "Rewarded ad loaded.");
                rewardedAd = ad;
                isLoading = false;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                Log.e(TAG, "Failed to load rewarded ad: " + adError.getMessage());
                rewardedAd = null;
                isLoading = false;
            }
        });
    }

    public boolean isAdAvailable() {
        return rewardedAd != null;
    }

    public void showAd(Activity activity, RewardAdListener listener) {
        if (rewardedAd == null || isAdShowing) {
            Log.d(TAG, "Ad not available to show.");
            listener.onAdFailedToShow();
            return;
        }

        isAdShowing = true;

        rewardedAd.show(activity, rewardItem -> {
            Log.d(TAG, "User earned reward.");
            listener.onUserEarnedReward(rewardItem);
        });

        rewardedAd.setFullScreenContentCallback(new com.google.android.gms.ads.FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed.");
                isAdShowing = false;
                rewardedAd = null;
                listener.onAdClosed();
                loadAd(); // Load next ad
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                Log.e(TAG, "Ad failed to show: " + adError.getMessage());
                isAdShowing = false;
                rewardedAd = null;
                listener.onAdFailedToShow();
                loadAd();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad shown.");
            }
        });
    }
}

