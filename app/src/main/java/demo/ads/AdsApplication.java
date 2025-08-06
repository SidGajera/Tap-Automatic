package demo.ads;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class AdsApplication extends Application {
    public static final String TAG = "AdsApplication";
    public static AppOpenManager appOpenManager;
    public static AdsApplication instance;

    public static synchronized AdsApplication getInstance() {
        AdsApplication adsApplication;
        synchronized (AdsApplication.class) {
            synchronized (AdsApplication.class) {
                adsApplication = instance;
            }
            return adsApplication;
        }
    }

    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(getApplicationContext());
        AndroidNetworking.initialize(getApplicationContext());
        instance = this;
        MobileAds.initialize(this, initializationStatus -> {
        });
    }
}
