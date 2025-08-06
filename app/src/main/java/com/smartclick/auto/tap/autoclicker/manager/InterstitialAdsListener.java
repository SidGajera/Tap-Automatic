package com.smartclick.auto.tap.autoclicker.manager;

import com.google.android.gms.ads.interstitial.InterstitialAd;

public interface InterstitialAdsListener {
   void onLoadSuccess(InterstitialAd ad);
   void onLoadFail();
   void onLoading();
}
