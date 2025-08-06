package com.smartclick.auto.tap.autoclicker.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapHome;
import static com.smartclick.auto.tap.autoclicker.MyApplication.isAdShow;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.net.MailTo;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Key;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.adapter.SaveListDialogAdapter;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityStartBinding;
import com.smartclick.auto.tap.autoclicker.databinding.RateDialogBinding;
import com.smartclick.auto.tap.autoclicker.databinding.SaveListDialogBinding;
import com.smartclick.auto.tap.autoclicker.model.MultiDbModel;
import com.smartclick.auto.tap.autoclicker.model.MultiModelTwo;
import com.smartclick.auto.tap.autoclicker.onclick.mInstructionsRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.starSubmitPlaystoreOnClick;
import com.smartclick.auto.tap.autoclicker.onclick.starOneClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.starTwoClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.starThreeClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.moreClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.guideRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.starFiveClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.starCancelClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.singleModeClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.multiModeClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.shareRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.privacyRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.mConfigurationRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.sSettingRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.sInstructionsRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.mSettingRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.languageRlClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.newIdClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.starSubmitOnClick;
import com.smartclick.auto.tap.autoclicker.onclick.popupBgClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.starSubmitClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.starFourClickListener;
import com.smartclick.auto.tap.autoclicker.service.SingleModeService;
import com.smartclick.auto.tap.autoclicker.service.MultiModeService;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.Common;
import com.smartclick.auto.tap.autoclicker.utils.Resizer;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;
import com.smartclick.auto.tap.autoclicker.utils.StorageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import demo.ads.ExitScreen;

public class StartActivity extends BaseActivity {
    public static int StartFlag;
    public static MultiDbModel demoMultiDbModel;
    public static List<MultiDbModel> multiDbModelList = new ArrayList<>();
    public static int position = -1;
    ActivityStartBinding binding;
    public int click = 0;
    String TAG = StartActivity.class.getName();
    // Declare at class level if not already
    private long mLastClickTimeSingle = 0;
    private static final int CLICK_DEBOUNCE_THRESHOLD = 500; // You can change this to 1000 for 1 second
    private long mLastClickTimeMulti = 0;

    private BroadcastReceiver receiver;

    ActivityResultLauncher<Intent> configurationResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResult -> {
        Intent data;
        if (activityResult.getResultCode() == -1 && (data = activityResult.getData()) != null && data.getIntExtra("Choice", -1) == 1) {
            startService(new Intent(StartActivity.this, MultiModeService.class));
            Glide.with(StartActivity.this).load(Common.isMyServiceRunning(StartActivity.this, MultiModeService.class) ? R.drawable.on : R.drawable.off).into(binding.multiMode);
            setEnableDisable();
        }
    });
    int f180i = 0;

    /* access modifiers changed from: protected */
    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityStartBinding inflate = ActivityStartBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        setData();
        if (isAdShow) {
            setupLoadAds(adUnitsMapHome);
            new Thread(
                    () -> runOnUiThread(() -> {
                        loadBannerAd(findViewById(R.id.nativeLay));
                        loadInterstitialVideoAd();
                    })
            ).start();
        }

        SpManager.setHomeEventCount(SpManager.getHomeEventCount() + 1);
        Log.e(TAG, "onCreate: event_count start visit ==> " + SpManager.getHomeEventCount());
    }

    public static List<MultiDbModel> parseJson(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            int i = 0;
            while (i < jSONArray.length()) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
                int i2 = jSONObject.getInt("intervalType");
                int i3 = jSONObject.getInt("intervalCount");
                int i4 = jSONObject.getInt("swipeCount");
                int i5 = jSONObject.getInt("stopType");
                int i6 = jSONObject.getInt("hour");
                int i7 = jSONObject.getInt("minute");
                int i8 = jSONObject.getInt("second");
                int i9 = jSONObject.getInt("nocCount");
                JSONArray jSONArray2 = jSONObject.getJSONArray("targets");
                ArrayList arrayList2 = new ArrayList();
                int i10 = 0;
                while (i10 < jSONArray2.length()) {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i10);
                    try {
                        arrayList2.add(new MultiModelTwo(jSONObject2.getInt("type"), (float) jSONObject2.getInt("clickOneX"), (float) jSONObject2.getInt("clickOneY"), (float) jSONObject2.getInt("clickTwoX"), (float) jSONObject2.getInt("clickTwoY")));
                        i10++;
                        jSONArray = jSONArray;
                        jSONArray2 = jSONArray2;
                        i = i;
                        i7 = i7;
                        i6 = i6;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("TAG", "parseJson: " + e.getMessage());
                        return arrayList;
                    }
                }
                arrayList.add(new MultiDbModel(string, i2, i3, i4, i5, i6, i7, i8, i9, arrayList2));
                i++;
                jSONArray = jSONArray;
            }
            return arrayList;
        } catch (JSONException e2) {
            e2.printStackTrace();
            Log.d("TAG", "parseJson: " + e2.getMessage());
            return arrayList;
        }
    }

    public String LoadData(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            fileInputStream.close();
            return new String(bArr, Key.STRING_CHARSET_NAME);
        } catch (IOException unused) {
            return "";
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private void setData() {
        multiDbModelList = parseJson(LoadData(new File(getFilesDir(), "Data.json").getAbsolutePath()));

        this.binding.more.setOnClickListener(new moreClickListener(this));
        this.binding.popupBg.setOnClickListener(new popupBgClickListener(this));
        this.binding.shareRl.setOnClickListener(new shareRlClickListener(this));
        this.binding.privacyRl.setOnClickListener(new privacyRlClickListener(this));
        this.binding.sSettingRl.setOnClickListener(new sSettingRlClickListener(this));
        this.binding.sInstructionsRl.setOnClickListener(new sInstructionsRlClickListener(this));
        this.binding.mSettingRl.setOnClickListener(new mSettingRlClickListener(this));
        this.binding.mInstructionsRl.setOnClickListener(new mInstructionsRlClickListener(this));
        this.binding.mConfigurationRl.setOnClickListener(new mConfigurationRlClickListener(this));
        this.binding.languageRl.setOnClickListener(new languageRlClickListener(this));
        this.binding.guideRl.setOnClickListener(new guideRlClickListener(this));
        RequestManager with = Glide.with(this);
        boolean isMyServiceRunning = Common.isMyServiceRunning(this, SingleModeService.class);
        int i = R.drawable.on;
        with.load(isMyServiceRunning ? R.drawable.on : R.drawable.off).into(this.binding.singleMode);
        RequestManager with2 = Glide.with(this);
        if (!Common.isMyServiceRunning(this, MultiModeService.class)) {
            i = R.drawable.off;
        }
        with2.load(i).into(this.binding.multiMode);
        setEnableDisable();
        this.binding.singleMode.setOnClickListener(new singleModeClickListener(this));
        this.binding.multiMode.setOnClickListener(new multiModeClickListener(this));
    }

    /* access modifiers changed from: package-private */
    public void moreOnClick(View view) {
        this.binding.popupBg.setVisibility(VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void popupBgOnClick(View view) {
        this.binding.popupBg.setVisibility(GONE);
    }

    /* access modifiers changed from: package-private */
    public void shareRlOnClick(View view) {
        this.binding.popupBg.setVisibility(GONE);
        StorageUtils.share_app(this);
    }

    /* access modifiers changed from: package-private */
    public void privacyRlOnClick(View view) {
        this.binding.popupBg.setVisibility(GONE);
        startActivity(new Intent(this, PrivacyPolicyActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    /* access modifiers changed from: package-private */
    public void sSettingRlOnClick(View view) {
        this.click = 1;
        showAds();
    }

    /* access modifiers changed from: package-private */
    public void sInstructionsRlOnClick(View view) {
        this.click = 2;
        showAds();
    }

    /* access modifiers changed from: package-private */
    public void mSettingRlOnClick(View view) {
        this.click = 3;
        showAds();
    }

    /* access modifiers changed from: package-private */
    public void mInstructionsRlOnClick(View view) {
        this.click = 4;
        showAds();
    }

    /* access modifiers changed from: package-private */
    public void mConfigurationRlOnClick(View view) {
        this.click = 5;
        showAds();
    }

    /* access modifiers changed from: package-private */
    public void languageRlOnClick(View view) {
        this.binding.popupBg.setVisibility(GONE);
        startActivity(new Intent(this, LanguageActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("Type", 2));
    }

    /* access modifiers changed from: package-private */
    public void guideRlOnClick(View view) {
        this.binding.popupBg.setVisibility(GONE);
        startActivity(new Intent(this, GuideActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("Type", 2));
    }

    public void singleTargetModeSwitch(View view) {
        long currentTime = SystemClock.elapsedRealtime();

        long difference = currentTime - mLastClickTimeSingle;
        Log.d(TAG, "Debounce Check [SingleMode]");
        Log.d(TAG, "Current Time: " + currentTime);
        Log.d(TAG, "mLastClickTimeSingle: " + mLastClickTimeSingle);
        Log.d(TAG, "Difference: " + difference + " ms");

        if (difference <= 1000) {
            // Too soon – ignore this click
            Log.d(TAG, "Click ignored due to debounce.");
            Toast.makeText(this, "Please wait a second before switching again.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Update after successful click is allowed
        mLastClickTimeSingle = currentTime;

        // Proceed with mode switch
        boolean isMultiServiceRunning = Common.isMyServiceRunning(this, MultiModeService.class);
        boolean isSingleServiceRunning = Common.isMyServiceRunning(this, SingleModeService.class);

        if (isMultiServiceRunning) {
            stopService(new Intent(this, MultiModeService.class));
            Glide.with(this).load(R.drawable.off).into(this.binding.multiMode);

            startService(new Intent(this, SingleModeService.class));
            Log.d(TAG, "Started SingleModeService (after stopping MultiModeService)");
        } else if (isSingleServiceRunning) {
            stopService(new Intent(this, SingleModeService.class));
            Log.d(TAG, "Stopped SingleModeService");
        } else {
            startService(new Intent(this, SingleModeService.class));
            Log.d(TAG, "Started SingleModeService (no service was running)");
        }

        // Post icon update after 1 second to reflect actual service status
        new Handler().postDelayed(() -> {
            boolean isRunning = Common.isMyServiceRunning(this, SingleModeService.class);
            Glide.with(this)
                    .load(isRunning ? R.drawable.on : R.drawable.off)
                    .into(this.binding.singleMode);
            Log.d(TAG, "Updated icon based on service state: " + isRunning);
        }, 1000);

        setEnableDisable();
    }

    public void multiTargetModeSwitch(View view) {
        long currentTime = SystemClock.elapsedRealtime();
        Log.d(TAG, "Debounce Check [MultiMode]\nCurrent Time: " + currentTime +
                "\nmLastClickTimeMulti: " + mLastClickTimeMulti +
                "\nDifference: " + (currentTime - mLastClickTimeMulti) + " ms");

        if (currentTime - mLastClickTimeMulti >= 1000) {
            mLastClickTimeMulti = currentTime;

            boolean isSingleServiceRunning = Common.isMyServiceRunning(this, SingleModeService.class);
            boolean isMultiServiceRunning = Common.isMyServiceRunning(this, MultiModeService.class);

            if (isSingleServiceRunning) {
                stopService(new Intent(this, SingleModeService.class));
                Glide.with(this)
                        .load(R.drawable.off)
                        .into(this.binding.singleMode);
            }

            if (isMultiServiceRunning) {
                stopService(new Intent(this, MultiModeService.class));
                Log.d(TAG, "Stopped MultiModeService");

                new Handler().postDelayed(() -> {
                    boolean isRunning = Common.isMyServiceRunning(this, MultiModeService.class);
                    Glide.with(this)
                            .load(isRunning ? R.drawable.on : R.drawable.off)
                            .into(this.binding.multiMode);
                    Log.d(TAG, "Updated icon after stopping MultiModeService. Service running: " + isRunning);
                }, 500);

            } else {
                if (!multiDbModelList.isEmpty()) {
                    openSaveListDialog();
                } else {
                    position = -1;
                    int nameNum = 0;
                    demoMultiDbModel = new MultiDbModel("Saved_"+nameNum,
                            SpManager.getMultiIntervalType(),
                            SpManager.getMultiIntervalCount(),
                            SpManager.getMultiSwipeCount(),
                            2, 0, 5, 0, 10, new ArrayList<>());

                    startService(new Intent(this, MultiModeService.class));
                    Log.d(TAG, "Started MultiModeService (no service was running)");

                    new Handler().postDelayed(() -> {
                        boolean isRunning = Common.isMyServiceRunning(this, MultiModeService.class);
                        Glide.with(this)
                                .load(isRunning ? R.drawable.on : R.drawable.off)
                                .into(this.binding.multiMode);
                        Log.d(TAG, "Updated icon after starting MultiModeService. Service running: " + isRunning);
                    }, 500);
                }
            }

            setEnableDisable();

        } else {
            Log.e(TAG, "Click ignored (too soon)" +
                    "\nTime since last multi click: " + (currentTime - mLastClickTimeMulti) + " ms");
            Toast.makeText(this, "Please wait a second before switching again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String state = intent.getStringExtra("switch_state");
                Log.e(TAG, "BroadcastReceiver floatingView closed onReceive: state = " + state);
                assert state != null;
                if (state.equals("single_mode_off")) {
                    Glide.with(StartActivity.this).load(R.drawable.off).into(binding.singleMode);
                    Log.d(TAG, "floatingView closed SingleModeService (after stopping SingleModeService)");
                } else if (state.equals("multi_mode_off")) {
                    Glide.with(StartActivity.this).load(R.drawable.off).into(binding.multiMode);
                    Log.d(TAG, "floatingView closed SingleModeService (after stopping MultiModeService)");
                }

                if (Objects.equals(intent.getAction(), Common.ACTION_UPDATE_UI)) {
                    boolean isSingleRunning = Common.isMyServiceRunning(StartActivity.this, SingleModeService.class);
                    boolean isMultiRunning = Common.isMyServiceRunning(StartActivity.this, MultiModeService.class);

                    Glide.with(StartActivity.this)
                            .load(isSingleRunning ? R.drawable.on : R.drawable.off)
                            .into(binding.singleMode);

                    Glide.with(StartActivity.this)
                            .load(isMultiRunning ? R.drawable.on : R.drawable.off)
                            .into(binding.multiMode);

                    setEnableDisable();
                }
            }
        };

        IntentFilter filter = new IntentFilter(getPackageName() + "." + Common.ACTION_UPDATE_UI);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("ACTION_CLOSE_APP"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageName() + "." + Common.ACTION_UPDATE_UI);
//        registerReceiver(this.receiver, intentFilter);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // For Android 13+ (API 33+), use the new 3-parameter version
            registerReceiver(receiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            // For Android 12 and below, use the old version
            registerReceiver(receiver, intentFilter);
        }

        boolean isSingleRunning = Common.isMyServiceRunning(StartActivity.this, SingleModeService.class);
        boolean isMultiRunning = Common.isMyServiceRunning(StartActivity.this, MultiModeService.class);
        if (isSingleRunning) {
            Glide.with(StartActivity.this)
                    .load(R.drawable.on)
                    .into(binding.singleMode);
        } else {
            Glide.with(StartActivity.this)
                    .load(R.drawable.off)
                    .into(binding.singleMode);
        }
        if (isMultiRunning) {
            Glide.with(StartActivity.this)
                    .load(R.drawable.on)
                    .into(binding.multiMode);
        } else {
            Glide.with(StartActivity.this)
                    .load(R.drawable.off)
                    .into(binding.multiMode);
        }

        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        stopService();
        if (isFinishing()) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("ACTION_CLOSE_APP"));
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        stopService();
        unregisterReceiver(this.receiver);
        Intent intent = new Intent("ACTION_CLOSE_APP");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        super.onDestroy();
    }

    public void stopService(){
        stopService(new Intent(this, SingleModeService.class));
        stopService(new Intent(this, MultiModeService.class));
    }

    @Override
    public void onBackPressed() {
        boolean isSingleRunning = Common.isMyServiceRunning(StartActivity.this, SingleModeService.class);
        boolean isMultiRunning = Common.isMyServiceRunning(StartActivity.this, MultiModeService.class);
        if (isSingleRunning) {
            stopService(new Intent(this, SingleModeService.class));
        }
        if (isMultiRunning) {
            stopService(new Intent(this, MultiModeService.class));
        }
        startActivity(new Intent(this, ExitScreen.class));
        super.onBackPressed();
    }

    public void setEnableDisable() {
        if (Common.isMyServiceRunning(this, SingleModeService.class)) {
            this.binding.sSettingRl.setEnabled(false);
            this.binding.sInstructionsRl.setEnabled(false);
            this.binding.tv1.setTextColor(getColor(R.color.disable_gray));
            this.binding.tv2.setTextColor(getColor(R.color.disable_gray));
        } else {
            this.binding.sSettingRl.setEnabled(true);
            this.binding.sInstructionsRl.setEnabled(true);
            this.binding.tv1.setTextColor(getColor(R.color.enable_black));
            this.binding.tv2.setTextColor(getColor(R.color.enable_black));
        }
        if (Common.isMyServiceRunning(this, MultiModeService.class)) {
            this.binding.mSettingRl.setEnabled(false);
            this.binding.mConfigurationRl.setEnabled(false);
            this.binding.mInstructionsRl.setEnabled(false);
            this.binding.tv3.setTextColor(getColor(R.color.disable_gray));
            this.binding.tv4.setTextColor(getColor(R.color.disable_gray));
            this.binding.tv5.setTextColor(getColor(R.color.disable_gray));
            return;
        }
        this.binding.mSettingRl.setEnabled(true);
        this.binding.mConfigurationRl.setEnabled(true);
        this.binding.mInstructionsRl.setEnabled(true);
        this.binding.tv3.setTextColor(getColor(R.color.enable_black));
        this.binding.tv4.setTextColor(getColor(R.color.enable_black));
        this.binding.tv5.setTextColor(getColor(R.color.enable_black));
    }

    public void openSaveListDialog() {
        SaveListDialogBinding inflate = SaveListDialogBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Resizer.getheightandwidth(this);
        Resizer.setSize(inflate.getRoot(), 804, 528);
        Resizer.setSize(inflate.newId, 526, 115);
        dialog.show();
        inflate.newId.setOnClickListener(new newIdClickListener(this, dialog));
        inflate.recycleView.setAdapter(new SaveListDialogAdapter(this, multiDbModelList, (i, multiDbModel) -> {
            StartActivity.position = i;
            StartActivity.demoMultiDbModel = multiDbModel;
            dialog.dismiss();
            startService(new Intent(StartActivity.this, MultiModeService.class));
            Glide.with(StartActivity.this).load(Common.isMyServiceRunning(StartActivity.this, MultiModeService.class) ? R.drawable.on : R.drawable.off).into(binding.multiMode);
            setEnableDisable();
        }));
    }

    /* access modifiers changed from: package-private */
    public void newIdOnClick(Dialog dialog, View view) {
        position = -1;
        int nameNum = multiDbModelList.size();
        demoMultiDbModel = new MultiDbModel("Saved_"+nameNum, SpManager.getMultiIntervalType(), SpManager.getMultiIntervalCount(), SpManager.getMultiSwipeCount(), 2, 0, 5, 0, 10, new ArrayList<>());
        dialog.dismiss();
        startService(new Intent(this, MultiModeService.class));
        Glide.with(this).load(Common.isMyServiceRunning(this, MultiModeService.class) ? R.drawable.on : R.drawable.off).into(this.binding.multiMode);
        setEnableDisable();
    }

    private void showRateDialog() {
        RateDialogBinding inflate = RateDialogBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return true;
            }
        });
        dialog.show();
        inflate.star1.setOnClickListener(new starOneClickListener(this, inflate));
        inflate.star2.setOnClickListener(new starTwoClickListener(this, inflate));
        inflate.star3.setOnClickListener(new starThreeClickListener(this, inflate));
        inflate.star4.setOnClickListener(new starFourClickListener(this, inflate));
        inflate.star5.setOnClickListener(new starFiveClickListener(this, inflate));
        inflate.cancel.setOnClickListener(new starCancelClickListener(this, dialog));
        inflate.rate.setOnClickListener(new starSubmitClickListener(this, dialog));
    }

    /* access modifiers changed from: package-private */
    public void starOneOnClick(RateDialogBinding rateDialogBinding, View view) {
        this.f180i = 1;
        rateDialogBinding.star1.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star2.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
        rateDialogBinding.star3.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
        rateDialogBinding.star4.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
        rateDialogBinding.star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
    }

    /* access modifiers changed from: package-private */
    public void starTwoOnClick(RateDialogBinding rateDialogBinding, View view) {
        this.f180i = 2;
        rateDialogBinding.star1.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star2.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star3.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
        rateDialogBinding.star4.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
        rateDialogBinding.star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
    }

    /* access modifiers changed from: package-private */
    public void starThreeOnClick(RateDialogBinding rateDialogBinding, View view) {
        this.f180i = 3;
        rateDialogBinding.star1.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star2.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star3.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star4.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
        rateDialogBinding.star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
    }

    /* access modifiers changed from: package-private */
    public void starFourOnClick(RateDialogBinding rateDialogBinding, View view) {
        this.f180i = 4;
        rateDialogBinding.star1.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star2.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star3.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star4.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unfill));
    }

    /* access modifiers changed from: package-private */
    public void starFiveOnClick(RateDialogBinding rateDialogBinding, View view) {
        this.f180i = 5;
        rateDialogBinding.star1.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star2.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star3.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star4.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
        rateDialogBinding.star5.setImageDrawable(getResources().getDrawable(R.drawable.star_fill));
    }

    /* access modifiers changed from: package-private */
    public void starCancelOnClick(Dialog dialog, View view) {
        List<ActivityManager.AppTask> appTasks;
        dialog.dismiss();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (!(activityManager == null || (appTasks = activityManager.getAppTasks()) == null || appTasks.size() <= 0)) {
            for (ActivityManager.AppTask appTask : appTasks) {
                appTask.finishAndRemoveTask();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @SuppressLint("IntentReset")
    public void starSubmitOnClick(Dialog dialog, View view) {
        SpManager.setRate_Which(this.f180i);
        int i = this.f180i;
        if (i == 0) {
            Toast.makeText(this, getResources().getString(R.string.ac97), Toast.LENGTH_SHORT).show();
        } else if (i <= 1 || i > 3) {
            SpManager.setRate_Which(i);
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
            } catch (ActivityNotFoundException unused) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            dialog.dismiss();
            new Handler().postDelayed(new starSubmitPlaystoreOnClick(this), 5000);
        } else {
            SpManager.setRate_Which(i);
            Intent intent = new Intent("android.intent.action.SENDTO");
            intent.setType("text/plain");
            intent.setData(Uri.parse(MailTo.MAILTO_SCHEME));
            intent.putExtra("android.intent.extra.EMAIL", new String[]{"androapps108@gmail.com"});
            intent.putExtra("android.intent.extra.SUBJECT", "Response For the Tap Automatic Application");
            intent.putExtra("android.intent.extra.TEXT", "Thank you For your Response. If you have Any Suggestion then You may type Here. \n");
            intent.setPackage("com.google.android.gm");
            try {
                startActivity(Intent.createChooser(intent, "Send mail..."));
            } catch (ActivityNotFoundException unused2) {
                Toast.makeText(this, getResources().getString(R.string.ac98), Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
            new Handler().postDelayed(new starSubmitOnClick(this), 5000);
        }
    }

    /* access modifiers changed from: package-private */
    public void starSubmitRun() {
        List<ActivityManager.AppTask> appTasks;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (!(activityManager == null || (appTasks = activityManager.getAppTasks()) == null || appTasks.size() <= 0)) {
            for (ActivityManager.AppTask appTask : appTasks) {
                appTask.finishAndRemoveTask();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void starSubmitPlayStoreRun() {
        List<ActivityManager.AppTask> appTasks;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (!(activityManager == null || (appTasks = activityManager.getAppTasks()) == null || appTasks.size() <= 0)) {
            for (ActivityManager.AppTask appTask : appTasks) {
                appTask.finishAndRemoveTask();
            }
        }
    }

    public void showAds() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.Showing_txt));
        callNext();
        StartFlag++;
    }

    public void callNext() {
        Class cls;
        int i = this.click;
        if (i == 5) {
            this.configurationResult.launch(new Intent(this, ConfigurationActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            return;
        }
        if (i == 1) {
            cls = SingleModeSettingActivity.class;
        } else if (i == 2) {
            cls = SingleModeInstructionActivity.class;
        } else {
            cls = i == 3 ? MultiModeSettingActivity.class : MultiModeInstructionActivity.class;
        }
        startActivity(new Intent(this, cls).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
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
