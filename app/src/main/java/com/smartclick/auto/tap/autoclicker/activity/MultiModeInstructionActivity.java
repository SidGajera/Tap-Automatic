package com.smartclick.auto.tap.autoclicker.activity;

import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapInstruction;
import static com.smartclick.auto.tap.autoclicker.MyApplication.isAdShow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityMultiModeInstructionBinding;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

public class MultiModeInstructionActivity extends BaseActivity {

    ActivityMultiModeInstructionBinding binding;
    String TAG = MultiModeInstructionActivity.class.getName();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityMultiModeInstructionBinding inflate = ActivityMultiModeInstructionBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.binding.back.setOnClickListener(MultiModeInstructionActivity.this::backClickListener);
        isNetworkAvailable();
        if (isAdShow) {
            setupLoadAds(adUnitsMapInstruction);
            new Thread(
                    () -> runOnUiThread(() -> {
                        loadBannerAd(findViewById(R.id.nativeLay));
                        loadInterstitialVideoAd();
                    })
            ).start();
        }

        SpManager.setMultiInstructionEventCount(SpManager.getMultiInstructionEventCount()+1);
        Log.e(TAG, "onCreate: event_count multi mode instruction visit ==> "+SpManager.getMultiInstructionEventCount());
    }

    public void backClickListener(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
