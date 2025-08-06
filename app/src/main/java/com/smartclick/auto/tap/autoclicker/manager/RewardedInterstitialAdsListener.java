package com.smartclick.auto.tap.autoclicker.manager;

import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;

public interface RewardedInterstitialAdsListener {
    void onLoadSuccess(RewardedInterstitialAd ad);

    void onLoadFail();

    void onLoading();
}
