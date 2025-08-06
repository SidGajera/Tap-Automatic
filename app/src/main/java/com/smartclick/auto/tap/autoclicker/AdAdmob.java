package com.smartclick.auto.tap.autoclicker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdAdmob {
    static ProgressDialog ProgressDialog;

    public AdAdmob(Activity activity) {
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {

            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    public static void FullscreenAd(final Activity activity) {
        Ad_Popup(activity);
        InterstitialAd.load(activity, activity.getString(R.string.int_admob), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            public void onAdLoaded(InterstitialAd interstitialAd) {
                interstitialAd.show(activity);
                AdAdmob.ProgressDialog.dismiss();
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                AdAdmob.ProgressDialog.dismiss();
            }
        });
    }

    private static void Ad_Popup(Context context) {
        ProgressDialog show = ProgressDialog.show(context, "", "Ad Loading . . .", true);
        ProgressDialog = show;
        show.setCancelable(true);
        ProgressDialog.show();
    }
}
