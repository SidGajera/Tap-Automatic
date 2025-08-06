package com.smartclick.auto.tap.autoclicker.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityPrivacyPolicyBinding;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;

public class PrivacyPolicyActivity extends BaseActivity {
    ActivityPrivacyPolicyBinding binding;
    private long mLastClickTime = 0;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityPrivacyPolicyBinding inflate = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - PrivacyPolicyActivity.this.mLastClickTime >= 1000) {
                    PrivacyPolicyActivity.this.mLastClickTime = SystemClock.elapsedRealtime();
                    PrivacyPolicyActivity.this.onBackPressed();
                }
            }
        });
        this.binding.webview.loadUrl(getString(R.string.privacy_policys));
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
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
