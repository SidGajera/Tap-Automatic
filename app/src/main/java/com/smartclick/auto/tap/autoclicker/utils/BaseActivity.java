package com.smartclick.auto.tap.autoclicker.utils;

import static com.smartclick.auto.tap.autoclicker.utils.StorageUtils.TAG;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.smartclick.auto.tap.autoclicker.MyApplication;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.manager.CustomAdsListener;
import com.smartclick.auto.tap.autoclicker.model.AdType;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public abstract class BaseActivity extends AppCompatActivity implements CustomAdsListener {
    public List<String> interstitialAds, interstitialVideoAds, bannerAds, rewardVideoAds, nativeAds, nativeVideoAds;
    public int interstitialIndex = 0, interstitialVideoIndex = 0, bannerIndex = 0, rewardVideoIndex = 0, nativeAdsIndex = 0, nativeVideoIndex = 0;

    @Override
    public void attachBaseContext(Context context) {
        super.attachBaseContext(setLocale(context, getSelectedLanguage(context)));
    }

    public Context setLocale(@NonNull Context context, String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    public String getSelectedLanguage(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        SpManager.initializingSharedPreference(context);
        SpManager.setIndian(timeZone.getDisplayName().equals("India Standard Time"));
        return SpManager.getLanguageCode();
    }

    public void isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean isNetworkConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        Log.d(TAG, "isNetworkAvailable: " + isNetworkConnected);
    }

    public void setupLoadAds(@NonNull Map<String, List<String>> adUnitsMap) {
        bannerIndex = 0;
        nativeAdsIndex = 0;
        nativeVideoIndex = 0;
        rewardVideoIndex = 0;
        interstitialIndex = 0;
        interstitialVideoIndex = 0;

        if (adUnitsMap.containsKey(AdType.BANNER)) {
            bannerAds = adUnitsMap.get(AdType.BANNER);
        }

        if (adUnitsMap.containsKey(AdType.NATIVE)) {
            nativeAds = adUnitsMap.get(AdType.NATIVE);
        }

        if (adUnitsMap.containsKey(AdType.NATIVE_VIDEO)) {
            nativeVideoAds = adUnitsMap.get(AdType.NATIVE_VIDEO);
        }

        if (adUnitsMap.containsKey(AdType.INTERSTITIAL)) {
            interstitialAds = adUnitsMap.get(AdType.INTERSTITIAL);
        }

        if (adUnitsMap.containsKey(AdType.INTERSTITIAL_VIDEO)) {
            interstitialVideoAds = adUnitsMap.get(AdType.INTERSTITIAL_VIDEO);
        }

        if (adUnitsMap.containsKey(AdType.REWARD_VIDEO)) {
            rewardVideoAds = adUnitsMap.get(AdType.REWARD_VIDEO);
        }
    }

    public void loadRewardVideoAd() {
        if (rewardVideoAds == null || rewardVideoAds.isEmpty()) {
            Log.d(TAG, "loadRewardVideoAd: No Reward Video Ads Available");
            MyApplication.googleAds.hideLoading();
            loadInterstitialVideoAd();
            return;
        }

        if (rewardVideoIndex >= rewardVideoAds.size()) {
            Log.d(TAG, "loadRewardVideoAd: All Reward Video Ads Loaded");
            MyApplication.googleAds.hideLoading();
            loadInterstitialVideoAd();
            return;
        }

        String adUnitId = rewardVideoAds.get(rewardVideoIndex);
        Log.d(TAG, rewardVideoIndex + " Trying  Ad Unit: " + adUnitId);

        MyApplication.googleAds.showRewardedAd(this, adUnitId, new CustomAdsListener() {
            @Override
            public void onFinish() {
                BaseActivity.this.onFinish();
            }

            @Override
            public void onLoading() {
                MyApplication.googleAds.showLoading(MyApplication.getCurrentActivity(), false);
            }

            @Override
            public void onError() {
                rewardVideoIndex++;
                loadRewardVideoAd();
            }

            @Override
            public void adShow() {
                MyApplication.googleAds.hideLoading();
            }
        });
    }

    public void loadBannerAd(FrameLayout adViewContainer) {
        if (bannerAds == null || bannerAds.isEmpty()) {
            Log.d(TAG, "loadBannerAd: No Banner Ads Available");
            adViewContainer.setVisibility(View.GONE);
            return;
        }

        if (bannerIndex >= bannerAds.size()) {
            Log.d(TAG, "loadBannerAd: All Banner Ads Failed");
            adViewContainer.setVisibility(View.GONE);
            return;
        }
        String adUnitId = bannerAds.get(bannerIndex);
        Log.d(TAG, bannerIndex + " Trying Ad Unit: " + adUnitId);

        MyApplication.googleAds.admobBanner(this, adViewContainer, adUnitId, new CustomAdsListener() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onLoading() {
            }

            @Override
            public void onError() {
                bannerIndex++;
                loadBannerAd(adViewContainer);
            }

            @Override
            public void adShow() {
                adViewContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    public void loadInterstitialAd() {
        if (interstitialAds == null || interstitialAds.isEmpty()) {
            Log.d(TAG, "loadInterstitialAd: No Interstitial Ads Available");
            BaseActivity.this.onFinish();
            return;
        }

        if (interstitialIndex >= interstitialAds.size()) {
            Log.d(TAG, "loadInterstitialAd: All Interstitial Ads Loaded");
            MyApplication.googleAds.hideLoading();
            BaseActivity.this.onFinish();
            return;
        }

        String adUnitId = interstitialAds.get(interstitialIndex);
        Log.d(TAG, interstitialIndex + " Trying Ad Unit: " + adUnitId);

        AdRequest adRequest = new AdRequest.Builder().build();

        MyApplication.googleAds.showCounterInterstitialAd(this, adUnitId, adRequest, new CustomAdsListener() {

            @Override
            public void onFinish() {
                BaseActivity.this.onFinish();
            }

            @Override
            public void onLoading() {
                MyApplication.googleAds.showLoading(MyApplication.getCurrentActivity(), false);
            }

            @Override
            public void onError() {
                interstitialIndex++;
                loadInterstitialAd();
            }

            @Override
            public void adShow() {
                MyApplication.googleAds.hideLoading();
            }
        });
    }

    public void loadInterstitialVideoAd() {
        if (interstitialVideoAds == null || interstitialVideoAds.isEmpty()) {
            Log.d("AdLoad", "loadInterstitialVideoAd: No Interstitial Video Ads Available");
            loadInterstitialAd();
            return;
        }

        if (interstitialVideoIndex >= interstitialVideoAds.size()) {
            Log.d(TAG, "loadInterstitialVideoAd: All Interstitial Video Ads Loaded");
            loadInterstitialAd();
            return;
        }

        String adUnitId = interstitialVideoAds.get(interstitialVideoIndex);
        Log.d(TAG, interstitialVideoIndex + " Trying Ad Unit: " + adUnitId);


        AdRequest adRequest = new AdRequest.Builder().build();

        MyApplication.googleAds.showRewardedInterstitialAd(this, adUnitId, adRequest, new CustomAdsListener() {

            @Override
            public void onFinish() {
                BaseActivity.this.onFinish();
            }

            @Override
            public void onLoading() {
                MyApplication.googleAds.showLoading(MyApplication.getCurrentActivity(), false);
            }

            @Override
            public void onError() {
                interstitialVideoIndex++;
                loadInterstitialVideoAd();
            }

            @Override
            public void adShow() {
                MyApplication.googleAds.hideLoading();
            }
        });
    }

    public void loadNativeAd(@NonNull FrameLayout adContainer, List<String> adsId, int index, boolean isVideo) {
        FrameLayout adLayout = (FrameLayout) getLayoutInflater()
                .inflate(R.layout.native_ad_layout, (ViewGroup) null);
        ShimmerFrameLayout shimmerFrameLayout = adLayout.findViewById(R.id.shimmer_view);
        NativeAdView adView = adLayout.findViewById(R.id.native_ad_view);
        adContainer.removeAllViews();
        adContainer.addView(adLayout);
        if (adsId == null || adsId.isEmpty()) {
            Log.d(TAG, "loadNativeAd: No Native Ads Available");
            if (isVideo) {
                loadNativeAd(adContainer, nativeAds, 0, false);
            } else {
                adLayout.setVisibility(View.GONE);
            }
            return;
        }

        if (index >= adsId.size()) {
            Log.d(TAG, "loadNativeAd: All Native Ads Loaded");
            if (isVideo) {
                loadNativeAd(adContainer, nativeAds, 0, false);
            } else {
                adLayout.setVisibility(View.GONE);
            }
            return;
        }

        String adUnitId = adsId.get(index);
        Log.d(TAG, index + " Trying Ad Unit: " + adUnitId + " " + isVideo);

        AdLoader adLoader = new AdLoader.Builder(this, adUnitId)
                .forNativeAd(nativeAd -> {

                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                    adView.setVisibility(View.VISIBLE);

                    populateNativeAdView(nativeAd, adView);

                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        loadNativeAd(adContainer, adsId, index + 1, isVideo);
                        Log.e(TAG, "Failed to load native ad: " + adError.getMessage());
                    }
                })
                .build();

        shimmerFrameLayout.startShimmer();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void populateNativeAdView(@NonNull NativeAd nativeAd, @NonNull NativeAdView adView) {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStoreView(adView.findViewById(R.id.ad_store));

        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        ((TextView) Objects.requireNonNull(adView.getStoreView())).setText(nativeAd.getStore());
        if (nativeAd.getBody() != null) {
            ((TextView) Objects.requireNonNull(adView.getBodyView())).setText(nativeAd.getBody());
            adView.getBodyView().setVisibility(View.VISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        }

        if (nativeAd.getIcon() != null) {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        } else {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        }

        ((Button) adView.findViewById(R.id.ad_call_to_action)).setText(nativeAd.getCallToAction());

        // Set MediaView
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Optional: track video
        VideoController vc = Objects.requireNonNull(nativeAd.getMediaContent()).getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    Log.d(TAG, "Video has finished playing.");
                }
            });
        }

        adView.setNativeAd(nativeAd);
    }
}
