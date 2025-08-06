package demo.ads;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetSmartAdmob extends AsyncTask<Void, Void, Boolean> {
    private static Activity activity;
    private final String[] adsId;
    private final SmartListener listener;

    /* access modifiers changed from: protected */
    public void onPreExecute() {
    }

    public GetSmartAdmob(Activity activity2, String[] strArr, SmartListener smartListener) {
        activity = activity2;
        this.adsId = strArr;
        this.listener = smartListener;
        AdsHandler.getInstance(activity2);
    }

    /* access modifiers changed from: protected */
    public Boolean doInBackground(Void... voidArr) {
        try {
            AdsHandler.bannerId = this.adsId[0];
            AdsHandler.nativeId = this.adsId[1];
            AdsHandler.interstitialId = this.adsId[2];
            AdsHandler.openAds = this.adsId[3];
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), 0).show();
            e.getMessage();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Boolean bool) {
        if (AdsHandler.openAds != null && !AdsHandler.openAds.isEmpty() && !AdsHandler.openAds.equals("0")) {
            AdsApplication.appOpenManager = new AppOpenManager(AdsApplication.getInstance());
        }
        this.listener.onFinish(bool.booleanValue());
        super.onPostExecute(bool);
    }
}
