package com.smartclick.auto.tap.autoclicker.activity;

import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapLanguage;
import static com.smartclick.auto.tap.autoclicker.MyApplication.isAdShow;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.adapter.LanguageAdapter;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityLanguageBinding;
import com.smartclick.auto.tap.autoclicker.model.LanguageModel;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.Resizer;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends BaseActivity {
    ActivityLanguageBinding binding;
    LanguageAdapter languageAdapter;
    ArrayList<LanguageModel> language_list = new ArrayList<>();
    private long mLastClickTime = 0;
    private FirebaseAnalytics firebaseAnalytics;
    String TAG = LanguageActivity.class.getName();

    /* access modifiers changed from: protected */
    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityLanguageBinding inflate = ActivityLanguageBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(inflate.getRoot());
        if (getIntent().getIntExtra("Type", 1) == 1) {
            this.binding.back.setVisibility(View.GONE);
        } else {
            this.binding.back.setVisibility(View.VISIBLE);
        }
        isNetworkAvailable();
        Resizer.getheightandwidth(this);
        Resizer.setSize(this.binding.header, 1080, 160, true);
        Resizer.setSize(this.binding.save, 50, 50, true);
        Resizer.setSize(this.binding.back, 80, 110, true);
        Resizer.setMargins(this.binding.save, 50, 0, 50, 0);
        Resizer.setMargins(this.binding.back, 50, 0, 50, 0);

        this.binding.back.setOnClickListener(LanguageActivity.this::backClickListener);

        this.binding.save.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - LanguageActivity.this.mLastClickTime >= 1000) {
                LanguageActivity.this.mLastClickTime = SystemClock.elapsedRealtime();
                SpManager.setLanguageCode(SpManager.getLanguageCodeSnip());
                loadInterstitialVideoAd();
            }

            Bundle eventBundle = new Bundle();
            eventBundle.putString("language_save", "Language Save Button");
            eventBundle.putString("action", "clicked");
            firebaseAnalytics.logEvent("language_button_click", eventBundle);
        });

        if (SpManager.isIndian()) {
            this.language_list.add(new LanguageModel(R.drawable.english, "English (Default)", "en"));
            this.language_list.add(new LanguageModel(R.drawable.hindi, "Hindi (\u0939\u093f\u0902\u0926\u0940)", "hi"));
            this.language_list.add(new LanguageModel(R.drawable.punjabi, "Punjabi (\u0a2a\u0a70\u0a1c\u0a3e\u0a2c\u0a40)", "pa"));
            this.language_list.add(new LanguageModel(R.drawable.arabic, "Arabic (\u0639\u0631\u0628\u064a)", "ar"));
            this.language_list.add(new LanguageModel(R.drawable.chinese, "Chinese (\u4e2d\u6587)", "zh"));
            this.language_list.add(new LanguageModel(R.drawable.dutch, "Dutch (Nederlands)", "nl"));
            this.language_list.add(new LanguageModel(R.drawable.french, "French (Fran\u00e7ais)", "fr"));
            this.language_list.add(new LanguageModel(R.drawable.german, "German (Deutsch)", "de"));
            this.language_list.add(new LanguageModel(R.drawable.indonesian, "Indonesian (Indonesia)", "in"));
            this.language_list.add(new LanguageModel(R.drawable.italian, "Italian (Italiana)", "it"));
            this.language_list.add(new LanguageModel(R.drawable.japanese, "Japanese (\u65e5\u672c\u8a9e)", "ja"));
            this.language_list.add(new LanguageModel(R.drawable.korean, "Korean (\ud55c\uad6d\uc778)", "ko"));
            this.language_list.add(new LanguageModel(R.drawable.malaysian, "Malaysian (Malaysia)", "ms"));
            this.language_list.add(new LanguageModel(R.drawable.portuguese, "Portuguese (Portugu\u00eas)", "pt"));
            this.language_list.add(new LanguageModel(R.drawable.russian, "Russian (\u0420\u0443\u0441\u0441\u043a\u0438\u0439)", "ru"));
            this.language_list.add(new LanguageModel(R.drawable.spanish, "Spanish (Espa\u00f1ola)", "es"));
            this.language_list.add(new LanguageModel(R.drawable.thai, "Thai (\u0e41\u0e1a\u0e1a\u0e44\u0e17\u0e22)", "th"));
            this.language_list.add(new LanguageModel(R.drawable.turkish, "Turkish (T\u00fcrk\u00e7e)", "tr"));
            this.language_list.add(new LanguageModel(R.drawable.vietnamese, "Vietnamese (Ti\u1ebfng Vi\u1ec7t)", "vi"));
        } else {
            this.language_list.add(new LanguageModel(R.drawable.english, "English (Default)", "en"));
            this.language_list.add(new LanguageModel(R.drawable.arabic, "Arabic (\u0639\u0631\u0628\u064a)", "ar"));
            this.language_list.add(new LanguageModel(R.drawable.chinese, "Chinese (\u4e2d\u6587)", "zh"));
            this.language_list.add(new LanguageModel(R.drawable.dutch, "Dutch (Nederlands)", "nl"));
            this.language_list.add(new LanguageModel(R.drawable.french, "French (Fran\u00e7ais)", "fr"));
            this.language_list.add(new LanguageModel(R.drawable.german, "German (Deutsch)", "de"));
            this.language_list.add(new LanguageModel(R.drawable.hindi, "Hindi (\u0939\u093f\u0902\u0926\u0940)", "hi"));
            this.language_list.add(new LanguageModel(R.drawable.indonesian, "Indonesian (Indonesia)", "in"));
            this.language_list.add(new LanguageModel(R.drawable.italian, "Italian (Italiana)", "it"));
            this.language_list.add(new LanguageModel(R.drawable.japanese, "Japanese (\u65e5\u672c\u8a9e)", "ja"));
            this.language_list.add(new LanguageModel(R.drawable.korean, "Korean (\ud55c\uad6d\uc778)", "ko"));
            this.language_list.add(new LanguageModel(R.drawable.malaysian, "Malaysian (Malaysia)", "ms"));
            this.language_list.add(new LanguageModel(R.drawable.portuguese, "Portuguese (Portugu\u00eas)", "pt"));
            this.language_list.add(new LanguageModel(R.drawable.punjabi, "Punjabi (\u0a2a\u0a70\u0a1c\u0a3e\u0a2c\u0a40)", "pa"));
            this.language_list.add(new LanguageModel(R.drawable.russian, "Russian (\u0420\u0443\u0441\u0441\u043a\u0438\u0439)", "ru"));
            this.language_list.add(new LanguageModel(R.drawable.spanish, "Spanish (Espa\u00f1ola)", "es"));
            this.language_list.add(new LanguageModel(R.drawable.thai, "Thai (\u0e41\u0e1a\u0e1a\u0e44\u0e17\u0e22)", "th"));
            this.language_list.add(new LanguageModel(R.drawable.turkish, "Turkish (T\u00fcrk\u00e7e)", "tr"));
            this.language_list.add(new LanguageModel(R.drawable.vietnamese, "Vietnamese (Ti\u1ebfng Vi\u1ec7t)", "vi"));
        }

        this.languageAdapter = new LanguageAdapter(this, this.language_list);
        this.binding.languageLayout.setHasFixedSize(true);
        this.binding.languageLayout.setLayoutManager(new LinearLayoutManager(this));
        this.binding.languageLayout.setAdapter(this.languageAdapter);

        if (isAdShow) {
            setupLoadAds(adUnitsMapLanguage);
            new Thread(
                    () -> runOnUiThread(() -> loadNativeAd(binding.nativeAdContainerLanguage, nativeVideoAds, 0, true))
            ).start();
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Language Fetching token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    Log.d("FCM_TOKEN", "Language screen tokem =>\n" + token);
                });

        Bundle screenBundle = new Bundle();
        screenBundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Language_Screen");
        screenBundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "Language_Activity");
        firebaseAnalytics.logEvent("Language_"+FirebaseAnalytics.Event.SCREEN_VIEW, screenBundle);

        SpManager.setLanguageEventCount(SpManager.getLanguageEventCount()+1);
        Log.e(TAG, "onCreate: event_count language visit ==> "+SpManager.getLanguageEventCount());

//        FirebaseCrashlytics.getInstance().log("Testing crash");
//        throw new RuntimeException("Test Crash from Crashlytics");
    }

    private void navigateToScreen() {
        if (LanguageActivity.this.getIntent().getIntExtra("Type", 1) == 1) {
            LanguageActivity.this.startActivity(new Intent(LanguageActivity.this, IntroActivity.class).putExtra("Type", 1));
            LanguageActivity.this.finish();
            return;
        }
        Intent intent = new Intent(LanguageActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LanguageActivity.this.startActivity(intent);
        LanguageActivity.this.finish();
    }

    public void backClickListener(View view) {
        onBackPressed();
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        super.onBackPressed();
        List<ActivityManager.AppTask> appTasks;
        SpManager.setLanguageCodeSnip(SpManager.getLanguageCode());
        if (getIntent().getIntExtra("Type", 1) == 1) {
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            if (!(activityManager == null || (appTasks = activityManager.getAppTasks()) == null || appTasks.size() <= 0)) {
                for (ActivityManager.AppTask appTask : appTasks) {
                    appTask.finishAndRemoveTask();
                }
                return;
            }
            return;
        }
        finish();
    }

    @Override
    public void onFinish() {
        navigateToScreen();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void adShow() {

    }
}
