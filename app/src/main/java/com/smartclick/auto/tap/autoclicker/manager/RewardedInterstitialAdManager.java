package com.smartclick.auto.tap.autoclicker.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class RewardedInterstitialAdManager {

    private static final String TAG = "RewardedInterstitialAdManager";
    private final Context context;
    private final String adUnitId;

    private RewardedInterstitialAd rewardedInterstitialAd;
    private boolean isLoading = false;
    private boolean isAdShowing = false;

    public interface RewardInterstitialAdListener {
        void onAdClosed();

        void onUserEarnedReward(RewardItem reward);

        void onAdFailedToShow();
    }

    public RewardedInterstitialAdManager(Context context, String adUnitId) {
        this.context = context.getApplicationContext();
        this.adUnitId = adUnitId;
        MobileAds.initialize(context);
        loadAd();
    }

    void loadAd() {
        if (isLoading || rewardedInterstitialAd != null) return;

        isLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedInterstitialAd.load(context, adUnitId, adRequest, new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                rewardedInterstitialAd = null;
                isLoading = false;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd ad) {
                super.onAdLoaded(rewardedInterstitialAd);
                rewardedInterstitialAd = ad;
                isLoading = false;
            }
        });
    }


    public boolean isAdAvailable() {
        return rewardedInterstitialAd != null;
    }

    public void showAd(Activity activity, RewardedAdsManager.RewardAdListener listener) {
        if (rewardedInterstitialAd == null || isAdShowing) {
            Log.d(TAG, "Ad not available to show.");
            listener.onAdFailedToShow();
            return;
        }

        isAdShowing = true;

        rewardedInterstitialAd.show(activity, rewardItem -> {
            Log.d(TAG, "User earned reward.");
            listener.onUserEarnedReward(rewardItem);
        });

        rewardedInterstitialAd.setFullScreenContentCallback(new com.google.android.gms.ads.FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed.");
                isAdShowing = false;
                rewardedInterstitialAd = null;
                listener.onAdClosed();
                loadAd(); // Load next ad
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                Log.e(TAG, "Ad failed to show: " + adError.getMessage());
                isAdShowing = false;
                rewardedInterstitialAd = null;
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
