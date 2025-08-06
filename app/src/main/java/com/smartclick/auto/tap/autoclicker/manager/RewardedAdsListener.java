package com.smartclick.auto.tap.autoclicker.manager;

import com.google.android.gms.ads.rewarded.RewardedAd;

public interface RewardedAdsListener {
   void onLoadSuccess(RewardedAd ad);
   void onLoadFail();
}
