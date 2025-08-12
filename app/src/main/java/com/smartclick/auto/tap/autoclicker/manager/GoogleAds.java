package com.smartclick.auto.tap.autoclicker.manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;

import java.util.Objects;

public class GoogleAds {
    private static final String TAG = "Google Ads => ";
    private static volatile GoogleAds instance;
    private Dialog dialog;
    private GoogleAds() {
    }
    public static boolean checkConnection(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return true;
        }
        if (activeNetworkInfo.getType() == 1) {
            return false;
        }
        return activeNetworkInfo.getType() != 0;
    }

    public static GoogleAds getInstance() {
        if (instance == null) {
            synchronized (GoogleAds.class) {
                if (instance == null) {
                    instance = new GoogleAds();
                }
            }
        }
        return instance;
    }

    public void admobBanner(Context context, final View view, final String bannerId, CustomAdsListener listener) {
        if (checkConnection(context)) {
            return;
        }
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(bannerId);
        listener.onLoading();
        adView.loadAd(new AdRequest.Builder().build());
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            linearLayout.removeAllViews();
            linearLayout.addView(adView);
        } else if (view instanceof RelativeLayout) {
            RelativeLayout relativeLayout = (RelativeLayout) view;
            relativeLayout.removeAllViews();
            relativeLayout.addView(adView);
        } else if (view instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) view;
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        }
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                view.setVisibility(View.VISIBLE);
                listener.adShow();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                listener.onError();
                Toast.makeText(context, "bannerAd fails to load for unitID: "
                        +bannerId+" for error: "+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean admobBanner90(Context context, final View view, final String bannerId) {
        if (checkConnection(context)) {
            return false;
        }
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.LARGE_BANNER);
        adView.setAdUnitId(bannerId);
        adView.loadAd(new AdRequest.Builder().build());
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            linearLayout.removeAllViews();
            linearLayout.addView(adView);
        } else if (view instanceof RelativeLayout) {
            RelativeLayout relativeLayout = (RelativeLayout) view;
            relativeLayout.removeAllViews();
            relativeLayout.addView(adView);
        } else if (view instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) view;
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        }
        adView.setAdListener(new AdListener() {

            @Override // com.google.android.gms.ads.AdListener
            public void onAdLoaded() {
                view.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                view.setVisibility(View.GONE);
                Toast.makeText(context, "Banner Ad fails to load for unitID: "
                        +bannerId+" for error: "+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    public void showCounterInterstitialAd(final Activity activity, final String interstitialId, AdRequest adRequest, CustomAdsListener customAdsListener) {
        if (checkConnection(activity)) {
            customAdsListener.onFinish();
            return;
        }
        customAdsListener.onLoading();
        InterstitialAd.load(activity, interstitialId, adRequest, new InterstitialAdLoadCallback() {
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        customAdsListener.adShow();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        customAdsListener.onFinish();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        customAdsListener.onError();
                        Toast.makeText(activity, "rewardedInterstitialVideo fails to load for unitID: "
                                +interstitialId+" for error: "+adError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                interstitialAd.show(activity);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.e(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                customAdsListener.onError();
            }
        });
    }

    public void showRewardedInterstitialAd(final Activity activity, String rewardedId, AdRequest adRequest, CustomAdsListener customAdsListener) {
        if (checkConnection(activity)) {
            customAdsListener.onFinish();
            return;
        }
        customAdsListener.onLoading();
        new Thread(
                () -> activity.runOnUiThread(
                        () -> RewardedInterstitialAd.load(activity, rewardedId, adRequest, new RewardedInterstitialAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                customAdsListener.onError();
                                Log.e(GoogleAds.TAG, loadAdError.getMessage());
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedInterstitialAd ad) {
                                super.onAdLoaded(ad);
                                ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdClicked() {
                                        super.onAdClicked();
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        customAdsListener.onFinish();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                        super.onAdFailedToShowFullScreenContent(adError);
                                        customAdsListener.onError();
                                        Toast.makeText(activity, "rewardedInterstitialVideo fails to load for unitID: "
                                                +rewardedId+" for error: "+adError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onAdImpression() {
                                        super.onAdImpression();
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        super.onAdShowedFullScreenContent();
                                        customAdsListener.adShow();
                                    }
                                });
                                ad.show(activity, rewardItem -> Log.d(TAG, "onUserEarnedReward: " + rewardItem.getAmount()));
                            }
                        }))
        ).start();

    }

    public void showRewardedAd(final Activity activity, String rewardedId, CustomAdsListener customAdsListener) {
        if (checkConnection(activity)) {
            customAdsListener.onFinish();
            return;
        }
        customAdsListener.onLoading();
        RewardedAd.load(activity, rewardedId, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                customAdsListener.onError();
                Log.e(GoogleAds.TAG, loadAdError.getMessage());
            }

            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                customAdsListener.adShow();
                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        customAdsListener.onError();
                        Toast.makeText(activity, "rewardVideo fails to load for unitID: "
                                +rewardedId+" for error: "+adError.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        customAdsListener.onFinish();
                    }
                });

                rewardedAd.show(activity, rewardItem -> {
                    Log.d(TAG, "onAdLoaded: " + rewardItem.getAmount());
                });
            }
        });
    }

    public void showLoading(Activity activity, boolean z) {
        if (isLoadingShowing()) {
            return;
        }
        Dialog dialog2 = new Dialog(activity);
        this.dialog = dialog2;
        Objects.requireNonNull(dialog2.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.loading_dialog);
        this.dialog.setCancelable(z);
        if (!this.dialog.isShowing() && !activity.isFinishing()) {
            this.dialog.show();
        }
    }

    public void hideLoading() {
        Dialog dialog2 = this.dialog;
        if (dialog2 != null && dialog2.isShowing()) {
            this.dialog.cancel();
        }
    }

    public boolean isLoadingShowing() {
        return this.dialog != null && this.dialog.isShowing();
    }
}
