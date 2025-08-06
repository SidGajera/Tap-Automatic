package demo.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartclick.auto.tap.autoclicker.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Objects;

public class GoogleAds {
    private static final String TAG = "Google Ads => ";
    private static GoogleAds instance;
    private Dialog dialog;
    private CustomAdsListener listener;
    private NativeAd nativeAd00;

    private GoogleAds() {
    }

    public static boolean checkConnection(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        if (activeNetworkInfo.getType() == 1) {
            return true;
        }
        if (activeNetworkInfo.getType() == 0) {
            return true;
        }
        return false;
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

    public boolean admobBanner(Context context, final View view) {
        if (!checkConnection(context) || !AdsHandler.isAdsOn()) {
            return false;
        }
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AdsHandler.bannerId);
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
            /* class demo.ads.GoogleAds.AnonymousClass1 */

            @Override // com.google.android.gms.ads.AdListener
            public void onAdLoaded() {
                super.onAdLoaded();
                view.setVisibility(View.VISIBLE);
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                view.setVisibility(View.GONE);
            }
        });
        return true;
    }

    public boolean admobBanner90(Context context, final View view) {
        if (!checkConnection(context) || !AdsHandler.isAdsOn()) {
            return false;
        }
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AdsHandler.bannerId);
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
            /* class demo.ads.GoogleAds.AnonymousClass2 */

            @Override // com.google.android.gms.ads.AdListener
            public void onAdLoaded() {
                view.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                view.setVisibility(View.GONE);
            }
        });
        return true;
    }

    public void showCounterInterstitialAd(final Activity activity, CustomAdsListener customAdsListener) {
        this.listener = customAdsListener;
        if (AdsHandler.sharedPreferences == null) {
            AdsHandler.getInstance(activity);
        }
        if (!checkConnection(activity) || !AdsHandler.isAdsOn()) {
            this.listener.onFinish();
            return;
        }
        showLoading(activity, false);
        InterstitialAd.load(activity, AdsHandler.interstitialId, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            /* class demo.ads.GoogleAds.AnonymousClass3 */

            public void onAdLoaded(InterstitialAd interstitialAd) {
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    /* class demo.ads.GoogleAds.AnonymousClass3.AnonymousClass1 */

                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdShowedFullScreenContent() {
                    }

                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdDismissedFullScreenContent() {
                        GoogleAds.this.listener.onFinish();
                    }

                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        GoogleAds.this.listener.onFinish();
                    }
                });
                GoogleAds.this.hideLoading();
                interstitialAd.show(activity);
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Toast.makeText(activity, loadAdError.getMessage(), 0).show();
                GoogleAds.this.hideLoading();
                GoogleAds.this.listener.onFinish();
            }
        });
    }

    public void showRewardedAd(final Activity activity, CustomAdsListener customAdsListener) {
        this.listener = customAdsListener;
        if (AdsHandler.sharedPreferences == null) {
            AdsHandler.getInstance(activity);
        }
        if (checkConnection(activity) && AdsHandler.isAdsOn()) {
            showLoading(activity, false);
            RewardedAd.load(activity, AdsHandler.rewardedId, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
                /* class demo.ads.GoogleAds.AnonymousClass4 */

                @Override // com.google.android.gms.ads.AdLoadCallback
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    GoogleAds.this.hideLoading();
                    if (GoogleAds.this.listener != null) {
                        GoogleAds.this.listener.onFinish();
                    }
                    Log.e(GoogleAds.TAG, loadAdError.getMessage());
                }

                public void onAdLoaded(RewardedAd rewardedAd) {
                    rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        /* class demo.ads.GoogleAds.AnonymousClass4.AnonymousClass1 */

                        @Override // com.google.android.gms.ads.FullScreenContentCallback
                        public void onAdShowedFullScreenContent() {
                        }

                        @Override // com.google.android.gms.ads.FullScreenContentCallback
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            GoogleAds.this.hideLoading();
                            if (GoogleAds.this.listener != null) {
                                GoogleAds.this.listener.onFinish();
                            }
                        }

                        @Override // com.google.android.gms.ads.FullScreenContentCallback
                        public void onAdDismissedFullScreenContent() {
                            GoogleAds.this.hideLoading();
                            if (GoogleAds.this.listener != null) {
                                GoogleAds.this.listener.onFinish();
                            }
                        }
                    });
                    rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                        /* class demo.ads.GoogleAds.AnonymousClass4.AnonymousClass2 */

                        @Override // com.google.android.gms.ads.OnUserEarnedRewardListener
                        public void onUserEarnedReward(RewardItem rewardItem) {
                            GoogleAds.this.hideLoading();
                        }
                    });
                    GoogleAds.this.hideLoading();
                }
            });
        }
    }

    public boolean addNativeView(final Context context, final View view) {
        if (!checkConnection(context) || !AdsHandler.isAdsOn()) {
            return false;
        }
        AdLoader.Builder builder = new AdLoader.Builder(context, AdsHandler.nativeId);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            /* class demo.ads.GoogleAds.AnonymousClass5 */

            @Override // com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener
            public void onNativeAdLoaded(NativeAd nativeAd) {
                if (GoogleAds.this.nativeAd00 != null) {
                    GoogleAds.this.nativeAd00.destroy();
                }
                GoogleAds.this.nativeAd00 = nativeAd;
                NativeAdView nativeAdView = (NativeAdView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.small_ad_unified, (ViewGroup) null);
                GoogleAds.this.populateNativeAdView(nativeAd, nativeAdView);

                if (view instanceof LinearLayout) {
                    LinearLayout linearLayout = (LinearLayout) view;
                    linearLayout.removeAllViews();
                    linearLayout.addView(nativeAdView);
                } else if (view instanceof RelativeLayout) {
                    RelativeLayout relativeLayout = (RelativeLayout) view;
                    relativeLayout.removeAllViews();
                    relativeLayout.addView(nativeAdView);
                } else if (view instanceof FrameLayout) {
                    FrameLayout frameLayout = (FrameLayout) view;
                    frameLayout.removeAllViews();
                    frameLayout.addView(nativeAdView);
                }
            }
        });
        builder.withAdListener(new AdListener() {
            /* class demo.ads.GoogleAds.AnonymousClass6 */

            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(LoadAdError loadAdError) {
            }
        }).build().loadAd(new AdRequest.Builder().build());
        return true;
    }

    public boolean addBigNativeView(final Context context, final View view) {
        if (!checkConnection(context) || !AdsHandler.isAdsOn()) {
            return false;
        }
        AdLoader.Builder builder = new AdLoader.Builder(context, AdsHandler.nativeId);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            /* class demo.ads.GoogleAds.AnonymousClass7 */

            @Override // com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener
            public void onNativeAdLoaded(NativeAd nativeAd) {
                if (GoogleAds.this.nativeAd00 != null) {
                    GoogleAds.this.nativeAd00.destroy();
                }
                GoogleAds.this.nativeAd00 = nativeAd;
                NativeAdView nativeAdView = (NativeAdView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.big_ad_unified, (ViewGroup) null);
                GoogleAds.this.populateNativeAdView(nativeAd, nativeAdView);

                if (view instanceof LinearLayout) {
                    LinearLayout linearLayout = (LinearLayout) view;
                    linearLayout.removeAllViews();
                    linearLayout.addView(nativeAdView);
                } else if (view instanceof RelativeLayout) {
                    RelativeLayout relativeLayout = (RelativeLayout) view;
                    relativeLayout.removeAllViews();
                    relativeLayout.addView(nativeAdView);
                } else if (view instanceof FrameLayout) {
                    FrameLayout frameLayout = (FrameLayout) view;
                    frameLayout.removeAllViews();
                    frameLayout.addView(nativeAdView);
                }
            }
        });
        builder.withAdListener(new AdListener() {
            /* class demo.ads.GoogleAds.AnonymousClass8 */

            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(LoadAdError loadAdError) {
            }
        }).build().loadAd(new AdRequest.Builder().build());
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void populateNativeAdView(NativeAd nativeAd, NativeAdView nativeAdView) {
        nativeAdView.setMediaView((MediaView) nativeAdView.findViewById(R.id.ad_media));
        nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.ad_headline));
        nativeAdView.setBodyView(nativeAdView.findViewById(R.id.ad_body));
        nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.ad_call_to_action));
        nativeAdView.setIconView(nativeAdView.findViewById(R.id.ad_app_icon));
        nativeAdView.setPriceView(nativeAdView.findViewById(R.id.ad_price));
        nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.ad_stars));
        nativeAdView.setStoreView(nativeAdView.findViewById(R.id.ad_store));
        nativeAdView.setAdvertiserView(nativeAdView.findViewById(R.id.ad_advertiser));
        ((TextView) nativeAdView.getHeadlineView()).setText(nativeAd.getHeadline());
        nativeAdView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        if (nativeAd.getBody() == null) {
            nativeAdView.getBodyView().setVisibility(4);
        } else {
            nativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) nativeAdView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            nativeAdView.getCallToActionView().setVisibility(4);
        } else {
            nativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) nativeAdView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            nativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) nativeAdView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            nativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getPrice() == null) {
            nativeAdView.getPriceView().setVisibility(4);
        } else {
            nativeAdView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) nativeAdView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            nativeAdView.getStoreView().setVisibility(4);
        } else {
            nativeAdView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) nativeAdView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getStarRating() == null) {
            nativeAdView.getStarRatingView().setVisibility(4);
        } else {
            ((RatingBar) nativeAdView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            nativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getAdvertiser() == null) {
            nativeAdView.getAdvertiserView().setVisibility(4);
        } else {
            ((TextView) nativeAdView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            nativeAdView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        nativeAdView.setNativeAd(nativeAd);
    }

    public void showLoading(Activity activity, boolean z) {
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
}
