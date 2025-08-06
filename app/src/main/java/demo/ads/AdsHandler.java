package demo.ads;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.common.util.IOUtils;
import java.io.IOException;

public class AdsHandler {
    private static Activity activity = null;
    public static String bannerId = "";
    public static SharedPreferences.Editor editor = null;
    public static AdsHandler instance = null;
    public static String interstitialId = "";
    public static String nativeId = "";
    public static String openAds = "";
    public static String rewardedId = "";
    public static SharedPreferences sharedPreferences;

    public static byte[] getByte(Context context, int i) throws IOException {
        return IOUtils.toByteArray(context.getResources().openRawResource(i));
    }

    public static void setAdsOn(boolean z) {
        editor.putBoolean("ads", z);
        editor.apply();
    }

    public static boolean isAdsOn() {
        SharedPreferences sharedPreferences2 = sharedPreferences;
        return sharedPreferences2 != null && sharedPreferences2.getBoolean("ads", true);
    }

    public static synchronized AdsHandler getInstance(Activity activity2) {
        AdsHandler adsHandler;
        synchronized (AdsHandler.class) {
            activity = activity2;
            SharedPreferences sharedPreferences2 = activity2.getSharedPreferences("AdmobPref", 0);
            sharedPreferences = sharedPreferences2;
            editor = sharedPreferences2.edit();
            if (instance == null) {
                instance = new AdsHandler();
            }
            adsHandler = instance;
        }
        return adsHandler;
    }
}
