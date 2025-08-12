package com.smartclick.auto.tap.autoclicker.activity;

import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapPermission;
import static com.smartclick.auto.tap.autoclicker.MyApplication.isAdShow;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityPermissionBinding;
import com.smartclick.auto.tap.autoclicker.databinding.AsseccDialogLayoutBinding;
import com.smartclick.auto.tap.autoclicker.onclick.accessibilityPermissionActivity;
import com.smartclick.auto.tap.autoclicker.onclick.agreeDialogPermissionActivity;
import com.smartclick.auto.tap.autoclicker.onclick.fivePermissionActivity;
import com.smartclick.auto.tap.autoclicker.onclick.guideDialogPermissionActivity;
import com.smartclick.auto.tap.autoclicker.onclick.onePermissionActivity;
import com.smartclick.auto.tap.autoclicker.onclick.overlayPermissionActivity;
import com.smartclick.auto.tap.autoclicker.onclick.startButtonActivity;
import com.smartclick.auto.tap.autoclicker.onclick.twoPermissionActivity;
import com.smartclick.auto.tap.autoclicker.service.MultiModeService;
import com.smartclick.auto.tap.autoclicker.service.SingleModeService;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.Common;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import java.util.Objects;

public class PermissionActivity extends BaseActivity {
    public static int REQ_ACCES = 1999;
    ActivityPermissionBinding binding;
    boolean isPermissionAllow = false;
    private FirebaseAnalytics firebaseAnalytics;

    String TAG = PermissionActivity.class.getName();

    /* access modifiers changed from: protected */
    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityPermissionBinding inflate = ActivityPermissionBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(inflate.getRoot());
        isNetworkAvailable();

        SpManager.setPermissionEventCount(SpManager.getPermissionEventCount() + 1);
        Log.e(TAG, "onCreate: event_count permission visit ==> " + SpManager.getPermissionEventCount());

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(getPackageName())) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }

        if (isAdShow) {
            setupLoadAds(adUnitsMapPermission);
            new Thread(
                    () -> runOnUiThread(
                            () -> loadNativeAd(binding.nativeAdContainerPermission, nativeVideoAds, 0, true))
            ).start();
        }
    }

    private void navigateToScreen() {
        Intent intent = new Intent(this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Bundle eventBundle = new Bundle();
        eventBundle.putString("Permission_save", "Language Save Button");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("language_button_click", eventBundle);

        finish();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        RequestManager with = Glide.with(this);
        boolean isAccessibilitySettingsOn = Common.isAccessibilitySettingsOn(this);
        int i = R.drawable.on;
        with.load(isAccessibilitySettingsOn ? R.drawable.on : R.drawable.off).into(this.binding.accessibility);
        Glide.with(this).load(Common.checkOverlay(this) ? R.drawable.on : R.drawable.off).into(this.binding.overlay);
        RequestManager with2 = Glide.with(this);
        if (!Common.checkStoragePermission(this)) {
            i = R.drawable.off;
        }
        with2.load(i).into(this.binding.storage);
        this.binding.storage.setOnClickListener(new fivePermissionActivity(this));
        this.binding.accessibility.setOnClickListener(new accessibilityPermissionActivity(this));
        this.binding.overlay.setOnClickListener(new overlayPermissionActivity(this));
        this.binding.allow.setOnClickListener(new startButtonActivity(this));

        //Temoporary solution
        boolean isSingleRunning = Common.isMyServiceRunning(PermissionActivity.this, SingleModeService.class);
        boolean isMultiRunning = Common.isMyServiceRunning(PermissionActivity.this, MultiModeService.class);
        if (isSingleRunning) {
            stopService(new Intent(this, SingleModeService.class));
        } else if (isMultiRunning) {
            stopService(new Intent(this, MultiModeService.class));
        }
    }

    /* access modifiers changed from: package-private */
    public void storageOnCLick(View view) {
        if (!Common.checkStoragePermission(this)) {
            callPermission(Common.permissions);
        }
    }

    /* access modifiers changed from: package-private */
    public void accessibilityOnClick(View view) {
        if (!Common.isAccessibilitySettingsOn(this)) {
            openDialog();
        }
    }

    /* access modifiers changed from: package-private */
    public void overlayOnCLick(View view) {
        if (!Common.checkOverlay(this)) {
            Common.getOverlayPermission(this, REQ_ACCES);
        }
    }

    /* access modifiers changed from: package-private */
    public void startButtonOnClick(View view) {
        if (!Common.isAccessibilitySettingsOn(this) || !Common.checkStoragePermission(this) || !Common.checkOverlay(this)) {
            Toast.makeText(this, getResources().getString(R.string.ac17), Toast.LENGTH_SHORT).show();
            return;
        }
        loadRewardVideoAd();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void openDialog() {
        this.isPermissionAllow = false;
        AsseccDialogLayoutBinding inflate = AsseccDialogLayoutBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener((dialogInterface, i, keyEvent) -> true);
        SpannableString spannableString = new SpannableString("By continuing, i agree to terms & condition and Privacy policy and allow to verify credentials.");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#61c665")), 48, 62, 33);
        spannableString.setSpan(new ClickableSpan() {
            public void onClick(@NonNull View view) {
                PermissionActivity.this.startActivity(new Intent(PermissionActivity.this, PrivacyPolicyActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        }, 48, 62, 33);
        inflate.tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
        inflate.tvPrivacy.setText(spannableString);
        dialog.show();
        if (this.isPermissionAllow) {
            Glide.with(this).load(R.drawable.check).into(inflate.accept);
            inflate.agree.setBackground(getResources().getDrawable(R.drawable.effect_agree));
            inflate.agree.setTextColor(Color.parseColor("#ffffff"));
        } else {
            Glide.with(this).load(R.drawable.uncheck).into(inflate.accept);
            inflate.agree.setBackground(getResources().getDrawable(R.drawable.effect_disagree));
            inflate.agree.setTextColor(Color.parseColor("#000000"));
        }
        inflate.accept.setOnClickListener(new onePermissionActivity(this, inflate));
        inflate.disagree.setOnClickListener(new twoPermissionActivity(dialog));
        inflate.agree.setOnClickListener(new agreeDialogPermissionActivity(this, dialog));
        inflate.textViewGuide.setOnClickListener(new guideDialogPermissionActivity(this, dialog));
    }

    /* access modifiers changed from: package-private */
    @SuppressLint("UseCompatLoadingForDrawables")
    public void acceptDialogOnClick(AsseccDialogLayoutBinding asseccDialogLayoutBinding, View view) {
        if (this.isPermissionAllow) {
            this.isPermissionAllow = false;
            Glide.with(this).load(R.drawable.uncheck).into(asseccDialogLayoutBinding.accept);
            asseccDialogLayoutBinding.agree.setBackground(getResources().getDrawable(R.drawable.effect_disagree));
            asseccDialogLayoutBinding.agree.setTextColor(Color.parseColor("#000000"));
            return;
        }
        this.isPermissionAllow = true;
        Glide.with(this).load(R.drawable.check).into(asseccDialogLayoutBinding.accept);
        asseccDialogLayoutBinding.agree.setBackground(getResources().getDrawable(R.drawable.effect_agree));
        asseccDialogLayoutBinding.agree.setTextColor(Color.parseColor("#ffffff"));
    }

    /* access modifiers changed from: package-private */
    public void agreeDialogOnClick(Dialog dialog, View view) {
        if (this.isPermissionAllow) {
            dialog.dismiss();
            Common.getPermissionAccessibilty(this, REQ_ACCES);
            return;
        }
        Toast.makeText(this, "Please agree terms & condition", Toast.LENGTH_SHORT).show();
    }

    public void guideDialogOnClick(Dialog f$1, View view) {
        startActivity(new Intent(PermissionActivity.this, GuideActivity.class));
    }

    public void callPermission(String[] strArr) {
        ActivityCompat.requestPermissions(this, strArr, 123);
    }

    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback, androidx.fragment.app.FragmentActivity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 123) {
            if (iArr.length == 0 || iArr[0] != 0) {
                Toast.makeText(this, getResources().getString(R.string.ac17), Toast.LENGTH_SHORT).show();
            } else {
                Glide.with(this).load(Common.checkStoragePermission(this) ? R.drawable.on : R.drawable.off).into(this.binding.storage);
            }
        }
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
