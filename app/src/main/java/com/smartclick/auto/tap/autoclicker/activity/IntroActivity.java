package com.smartclick.auto.tap.autoclicker.activity;

import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapIntro;
import static com.smartclick.auto.tap.autoclicker.MyApplication.isAdShow;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.adapter.IntroAdapter;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityIntroBinding;
import com.smartclick.auto.tap.autoclicker.model.IntroInfo;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.Resizer;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;
    private FirebaseAnalytics firebaseAnalytics;
    String TAG = IntroActivity.class.getName();
    private final ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int i, float f, int i2) {
            super.onPageScrolled(i, f, i2);
            Log.e(TAG, "onPageScrolled: i = "+i);
        }
        @Override
        public void onPageSelected(int i) {
            super.onPageSelected(i);
            int j = i+1;

            Log.e(TAG, "onPageSelected: j = "+j);
            Bundle eventBundle = new Bundle();
            eventBundle.putString("Intro_next_"+j, "Intro Next Button "+j);
            eventBundle.putString("action", "clicked "+j);
            firebaseAnalytics.logEvent("Intro_next_click_"+j, eventBundle);

            Glide.with(IntroActivity.this).load(i == 0
                    ? R.drawable.guide_1_indicator : i == 1
                    ? R.drawable.guide_2_indicator : i == 2
                    ? R.drawable.guide_3_indicator : R.drawable.guide_4_indicator).into(IntroActivity.this.binding.indicator);
            IntroActivity.this.binding.nextDoneTxt.setText(i == 3 ? R.string.ac10 : R.string.ac9);
            if (IntroActivity.this.binding.infoPager.getCurrentItem() == 1 || IntroActivity.this.binding.infoPager.getCurrentItem() == 3) {
                IntroActivity.this.binding.nativeAdContainerIntro.setVisibility(View.GONE);
            } else {
                IntroActivity.this.binding.nativeAdContainerIntro.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onPageScrollStateChanged(int i) {
            super.onPageScrollStateChanged(i);
            Log.e(TAG, "onPageScrollStateChanged: i = "+i);
        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityIntroBinding inflate = ActivityIntroBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(inflate.getRoot());
        getIntent().getIntExtra("Type", 1);
        final ArrayList<IntroInfo> arrayList = new ArrayList<>();
        arrayList.add(new IntroInfo(R.drawable.guide_img_1, R.string.ac5, R.string.ac6));
        arrayList.add(new IntroInfo(R.drawable.guide_img_2, R.string.ac7, R.string.ac8));
        arrayList.add(new IntroInfo(R.drawable.guide_img_3, R.string.ac107, R.string.ac108));
        arrayList.add(new IntroInfo(R.drawable.guide_img_4, R.string.ac109, R.string.ac110));
//        Resizer.getheightandwidth(this);
//        Resizer.setSize(this.binding.indicator, 87, 35, true);
//        Resizer.setSize(this.binding.nextDoneTxt, 242, 144, true);
        this.binding.infoPager.setAdapter(new IntroAdapter(arrayList));
        this.binding.nextDoneTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IntroActivity.this.nextDoneTxtClickListener(arrayList, view);
            }
        });
        if (isAdShow) {
            setupLoadAds(adUnitsMapIntro);
            new Thread(() -> runOnUiThread(() -> loadNativeAd(binding.nativeAdContainerIntro, nativeVideoAds, 0, true))).start();
        }

        SpManager.setIntroEventCount(SpManager.getIntroEventCount()+1);
        Log.e(TAG, "onCreate: event_count intro visit ==> "+SpManager.getIntroEventCount());

    }

    public void nextDoneTxtClickListener(ArrayList<IntroInfo> list, View view) {
        if (this.binding.infoPager.getCurrentItem() == 3) {
            SpManager.setIsFirstTime(false);
            loadRewardVideoAd();
            return;
        }
//        else if (this.binding.infoPager.getCurrentItem() == 0 || this.binding.infoPager.getCurrentItem() == 2) {
//            this.binding.nativeAdContainerIntro.setVisibility(View.GONE);
//        } else {
//            this.binding.nativeAdContainerIntro.setVisibility(View.VISIBLE);
//        }
        this.binding.infoPager.setCurrentItem(Math.min(this.binding.infoPager.getCurrentItem() + 1, list.size() - 1), true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        List<ActivityManager.AppTask> appTasks;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (!(activityManager == null || (appTasks = activityManager.getAppTasks()) == null || appTasks.size() <= 0)) {
            for (ActivityManager.AppTask appTask : appTasks) {
                appTask.finishAndRemoveTask();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.binding.infoPager.registerOnPageChangeCallback(this.onPageChangeCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.binding.infoPager.unregisterOnPageChangeCallback(this.onPageChangeCallback);
    }

    private void navigateToScreen() {
        Intent intent = new Intent(this, PermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
