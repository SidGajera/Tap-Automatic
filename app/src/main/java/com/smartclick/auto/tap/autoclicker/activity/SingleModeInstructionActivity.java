package com.smartclick.auto.tap.autoclicker.activity;

import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapInstruction;
import static com.smartclick.auto.tap.autoclicker.MyApplication.isAdShow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.ActivitySingleModeInstructionBinding;
import com.smartclick.auto.tap.autoclicker.onclick.backClickListener;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

public class SingleModeInstructionActivity extends BaseActivity {
    ActivitySingleModeInstructionBinding binding;

    String TAG = SingleModeInstructionActivity.class.getName();

    /* access modifiers changed from: protected */
    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivitySingleModeInstructionBinding inflate = ActivitySingleModeInstructionBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.binding.back.setOnClickListener(new backClickListener(this));
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

        SpManager.setSingleInstructionEventCount(SpManager.getSingleInstructionEventCount()+1);
        Log.e(TAG, "onCreate: event_count single mode instruction visit ==> "+SpManager.getSingleInstructionEventCount());
    }

    /* access modifiers changed from: package-private */
    public void backOnClick(View view) {
        onBackPressed();
    }

    @Override // androidx.activity.ComponentActivity
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
