package com.smartclick.auto.tap.autoclicker.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;

public class InterstitialAdManager {

    private static final String TAG = "InterstitialAdManager";
    private final Context context;
    private final String adUnitId;

    private InterstitialAd interstitialAd;
    private boolean isLoading = false;
    private boolean isAdShowing = false;

    public interface InterstitialAdListener {
        void onAdClosed();

        void onUserEarnedReward(RewardItem reward);

        void onAdFailedToShow();
    }

    public InterstitialAdManager(Context context, String adUnitId) {
        this.context = context.getApplicationContext();
        this.adUnitId = adUnitId;
        MobileAds.initialize(context);
        loadAd();
    }

    void loadAd() {
        if (isLoading || interstitialAd != null) return;

        isLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, adUnitId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                interstitialAd = null;
                isLoading = false;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                super.onAdLoaded(interstitialAd);
                interstitialAd = ad;
                isLoading = false;
            }
        });
    }


    public boolean isAdAvailable() {
        return interstitialAd != null;
    }

    public void showAd(Activity activity, RewardedAdsManager.RewardAdListener listener) {
        if (interstitialAd == null || isAdShowing) {
            Log.d(TAG, "Ad not available to show.");
            listener.onAdFailedToShow();
            return;
        }

        isAdShowing = true;

        interstitialAd.show(activity);

        interstitialAd.setFullScreenContentCallback(new com.google.android.gms.ads.FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed.");
                isAdShowing = false;
                interstitialAd = null;
                listener.onAdClosed();
                loadAd(); // Load next ad
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                Log.e(TAG, "Ad failed to show: " + adError.getMessage());
                isAdShowing = false;
                interstitialAd = null;
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
