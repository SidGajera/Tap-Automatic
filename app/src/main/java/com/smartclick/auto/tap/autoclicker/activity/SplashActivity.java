package com.smartclick.auto.tap.autoclicker.activity;

import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapHome;
import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapInstruction;
import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapIntro;
import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapLanguage;
import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapPermission;
import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapSetting;
import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapSplash;
import static com.smartclick.auto.tap.autoclicker.MyApplication.isAdShow;
import static com.smartclick.auto.tap.autoclicker.utils.Common.isAccessibilityServiceEnabled;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.smartclick.auto.tap.autoclicker.databinding.ActivitySplashBinding;
import com.smartclick.auto.tap.autoclicker.model.AppPages;
import com.google.gson.Gson;
import com.smartclick.auto.tap.autoclicker.service.AutoClicker;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.Common;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {
    public static String TAG = SplashActivity.class.getName()+"_Log";
    ActivitySplashBinding binding;
    public ArrayList<AppPages> appPagesArrayList;
    private int appPagesIndex = 0;

    public static void onFinishClickListener(boolean z) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivitySplashBinding inflate = ActivitySplashBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        createFile("Data.json");
        SpManager.setSplashEventCount(SpManager.getSplashEventCount()+1);
        Log.e(TAG, "onCreate: splash visit ==> "+SpManager.getSplashEventCount());
        setupApiLoadAds();
        SpManager.setSplashEventCount(SpManager.getSplashEventCount()+1);
        Log.e(TAG, "onCreate: event_count splash event ==> "+SpManager.getSplashEventCount());
    }

    private void createFile(String str) {
        try {
            File file = new File(getFilesDir(), str);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Implemented Accessibility Service
    //Accessibility Service permission only once, and keep it remembered across app launches — like other stable auto clicker apps.
    private void navigateToScreen() {
        if (SpManager.getIsFirstTime()) {
            StartActivity.StartFlag = 0;
            startActivity(new Intent(SplashActivity.this, LanguageActivity.class).putExtra("Type", 1));
            finish();
        } else if /*(!Common.isAccessibilitySettingsOn(SplashActivity.this)*/ ((!isAccessibilityServiceEnabled(SplashActivity.this, AutoClicker.class))
                || !Common.checkStoragePermission(SplashActivity.this)) {
            StartActivity.StartFlag = 0;
            startActivity(new Intent(SplashActivity.this, PermissionActivity.class));
            finish();
        } else {
            Intent intent = new Intent(this, StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void setupApiLoadAds() {
        AndroidNetworking.get("https://spsofttech.com/projects/google_ads/api/advertise_on_off")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            int result = response.getInt("result");
//                            String message = response.getString("message");
                            isAdShow = result == 1;
//                            JSONArray dataArray = response.getJSONArray("data");
                            Log.i(TAG, "onResponse: " + response);
                            if (isAdShow) {
//                                setupLoadAds();
                                setupCallAppPages();
                            } else {
                                new Handler().postDelayed(() -> navigateToScreen(), 3000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "setupApiLoadAds onResponse: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "onError: " + error);
                    }
                });
    }

    private void setupCallAppPages() {
        AndroidNetworking.get("https://spsofttech.com/projects/google_ads/api/app_pages")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            int result = response.getInt("result");
                            String message = response.getString("message");
                            isAdShow = result == 1;
                            JSONArray dataArray = response.getJSONArray("data");
                            appPagesArrayList = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                appPagesArrayList.add(new Gson().fromJson(dataArray.getString(i), AppPages.class));
                            }
                            appPagesIndex = 0;
                            callLoadPageAdvertiseLink();
                            Log.i(TAG, "onResponse: " + response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "onError: " + error);
                    }
                });
    }

    private void callLoadPageAdvertiseLink() {
        if (Objects.equals(appPagesArrayList.get(appPagesIndex).status, "1")) {
            AndroidNetworking.post("https://spsofttech.com/projects/google_ads/api/page_advertise_link")
                    .addBodyParameter("page_id", String.valueOf(appPagesArrayList.get(appPagesIndex).id))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            try {
                                int result = response.getInt("result");
                                String message = response.getString("message");
                                isAdShow = result == 1;
                                JSONArray dataArray = response.getJSONArray("data");
                                Log.i(TAG, "onResponse: callLoadPageAdvertiseLink " + response);
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject adTypeObj = dataArray.getJSONObject(i);
                                    String adName = adTypeObj.getString("name");
                                    JSONArray resultArray = adTypeObj.getJSONArray("result");

                                    List<String> adLinks = new ArrayList<>();
                                    for (int j = 0; j < resultArray.length(); j++) {
                                        JSONObject linkObj = resultArray.getJSONObject(j);
                                        String getLink = linkObj.getString("link");
                                        adLinks.add(getLink);
//                                        Log.e(TAG, "onResponse: resultArray adName = "+adName+
//                                                "\n onResponse: resultArray getLink = "+getLink+
//                                                "\n onResponse: resultArray linkObj = "+linkObj);
                                    }

                                    if (appPagesArrayList.get(appPagesIndex).id == 1) {
                                        adUnitsMapSplash.put(adName, adLinks);
                                    } else if (appPagesArrayList.get(appPagesIndex).id == 2) {
                                        adUnitsMapIntro.put(adName, adLinks);
                                    } else if (appPagesArrayList.get(appPagesIndex).id == 3) {
                                        adUnitsMapLanguage.put(adName, adLinks);
                                    } else if (appPagesArrayList.get(appPagesIndex).id == 4) {
                                        adUnitsMapPermission.put(adName, adLinks);
                                    } else if (appPagesArrayList.get(appPagesIndex).id == 5) {
                                        adUnitsMapHome.put(adName, adLinks);
                                    } else if (appPagesArrayList.get(appPagesIndex).id == 6) {
                                        adUnitsMapSetting.put(adName, adLinks);
                                    } else if (appPagesArrayList.get(appPagesIndex).id == 7) {
                                        adUnitsMapInstruction.put(adName, adLinks);
                                    }
                                }

                                appPagesIndex++;

                                if (appPagesArrayList == null || appPagesArrayList.isEmpty()) {
                                    Log.d(TAG, "PageAds empty pages app ads");
                                    return;
                                }

                                if (appPagesIndex > appPagesArrayList.size() - 1) {
                                    Log.d(TAG, "PageAds All Pages Complete");
                                    setupLoadAds(adUnitsMapSplash);
                                    new Thread(
                                            () -> runOnUiThread(() -> {
                                                loadBannerAd(binding.adViewContainer);
                                                loadInterstitialVideoAd();
                                            })
                                    ).start();
                                    return;
                                }
                                callLoadPageAdvertiseLink();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "onError: " + e.getMessage());
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            Log.e(TAG, "onError: " + error);
                        }
                    });
        } else {
            new Handler().postDelayed(this::navigateToScreen, 3000);
        }
    }

    @Override
    public void onFinish() {
        navigateToScreen();
        Log.e(TAG, "onFinish: splash visit");
    }

    @Override
    public void onLoading() {
        Log.e(TAG, "onLoading: splash visit");
    }

    @Override
    public void onError() {
        Log.e(TAG, "onError: splash visit");
    }

    @Override
    public void adShow() {
        Log.e(TAG, "onCreate: splash visit ==> "+SpManager.getSplashEventCount());
    }
}
