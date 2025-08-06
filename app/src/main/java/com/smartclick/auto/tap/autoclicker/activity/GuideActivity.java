package com.smartclick.auto.tap.autoclicker.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.exifinterface.media.ExifInterface;

import com.bumptech.glide.Glide;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityGuideBinding;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

public class GuideActivity extends BaseActivity {
    ActivityGuideBinding binding;
    int count = 0;
    String TAG = GuideActivity.class.getName();

     @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityGuideBinding inflate = ActivityGuideBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        setData();
        isNetworkAvailable();
//        loadBannerAd(binding.nativeLay);

         SpManager.setGuideEventCount(SpManager.getGuideEventCount()+1);
         Log.e(TAG, "onCreate: event_count guide visit ==> "+SpManager.getGuideEventCount());
    }

    private void setData() {
        Glide.with(this).load(R.drawable.g_1).into(this.binding.guidImg);
        this.binding.curentCount.setText("1");
        this.binding.footer.setOnClickListener(this::footerClickListener);
        this.binding.back.setOnClickListener(this::backClickListener);
    }

     public void footerClickListener(View view) {
        int i = this.count;
        if (i == 0) {
            this.count = i + 1;
            Glide.with(this).load(R.drawable.g_2).into(this.binding.guidImg);
            this.binding.curentCount.setText(ExifInterface.GPS_MEASUREMENT_2D);
        } else if (i == 1) {
            this.count = i + 1;
            Glide.with(this).load(R.drawable.g_3).into(this.binding.guidImg);
            this.binding.curentCount.setText(ExifInterface.GPS_MEASUREMENT_3D);
        } else {
            finish();
        }
    }

    /* access modifiers changed from: package-private */
    public void backClickListener(View view) {
        onBackPressed();
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFinish() {

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
