package com.smartclick.auto.tap.autoclicker.service;

import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapHome;
import static com.smartclick.auto.tap.autoclicker.MyApplication.googleAds;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.MyApplication;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.SingleModeSettingDialogBinding;
import com.smartclick.auto.tap.autoclicker.databinding.TimePickerDialogBinding;
import com.smartclick.auto.tap.autoclicker.manager.CustomAdsListener;
import com.smartclick.auto.tap.autoclicker.model.AdType;
import com.smartclick.auto.tap.autoclicker.onclick.SingleCloseClickLisner;
import com.smartclick.auto.tap.autoclicker.onclick.settingRunInfiniteClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.settingSaveClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.timePickerCancelClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.settingRunTimerClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.singlePlayClickLisner;
import com.smartclick.auto.tap.autoclicker.onclick.settingCancelClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.downClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.popupRlOneClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.settingMStvClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.settingStvClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.settingMtvClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.timePickerClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.timePickerOkClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.intervalTypeClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.timePickerOkClickLisner;
import com.smartclick.auto.tap.autoclicker.onclick.settingTimerClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.settingNocClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.singlePlayRunClickLisner;
import com.smartclick.auto.tap.autoclicker.onclick.settingBindingClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.SinglePlayClickListner;
import com.smartclick.auto.tap.autoclicker.onclick.SingleSettingClickLisner;
import com.smartclick.auto.tap.autoclicker.utils.Common;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import java.util.List;


public class SingleModeService extends Service {
    public static Handler handler1 = new Handler();
    public static Runnable runnable1;
    float clickX;
    float clickY;
    int count;
    long duration = 0;
    View floatingView;
    int intervalType;
    int intervalValue;
    public boolean isPlaying = false;
    int nocCount;
    int numberOfCount;
    WindowManager.LayoutParams params;
    int runTimerHour;
    int runTimerMinute;
    int runTimerSecond;
    SingleModeSettingDialogBinding settingBinding;
    int stopType;
    WindowManager.LayoutParams targetParams;
    View targetView;
    TimePickerDialogBinding timePickerBinding;
    int type;
    float viewOneX;
    float viewOneY;
    float viewTwoX;
    float viewTwoY;
    WindowManager windowManager;
    String TAG = SingleModeService.class.getName();

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.floatingView = LayoutInflater.from(this).inflate(R.layout.single_mode_layout, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();

            Notification notification = new NotificationCompat.Builder(this, "click_channel")
                    .setContentTitle("Auto Clicker Running")
                    .setContentText("Single Mode is active")
                    .setSmallIcon(R.mipmap.ic_launcher) // Ensure this icon exists
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setOngoing(true)
                    .build();

            startForeground(1001, notification); // 👈 MUST BE CALLED IMMEDIATELY
        }

        if (Build.VERSION.SDK_INT >= 26) {
            this.params = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
        } else {
            this.params = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
        }
        this.params.gravity = Gravity.TOP | Gravity.START;
        this.params.x = 0;
        this.params.y = 100;
        WindowManager windowManager2 = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.windowManager = windowManager2;
        windowManager2.addView(this.floatingView, this.params);
        this.windowManager.getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.windowManager.getDefaultDisplay().getRealSize(new Point());
        createTargetWindow();
        floatingTouch(true);
        this.floatingView.findViewById(R.id.close).setOnClickListener(new SingleCloseClickLisner(this));
        this.floatingView.findViewById(R.id.setting).setOnClickListener(new SingleSettingClickLisner(this));
        this.floatingView.findViewById(R.id.play).setOnClickListener(new SinglePlayClickListner(this));
    }

    public void singleCloseOnClick(View view) {
        Intent broadcastIntent = new Intent(getPackageName() + "." + Common.ACTION_UPDATE_UI);
        broadcastIntent.putExtra("switch_state", "single_mode_off");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);

        Log.e(TAG, "singleCloseOnClick: Single floatingView closed");
        // Optionally stop the service
        stopSelf();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("ACTION_CLOSE_APP".equals(intent.getAction())) {
                stopSelf();
            }
        }
    };

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onDestroy called, floatingView = " + (floatingView != null));
        Log.d(TAG, "onTaskRemoved called — Single service App closed from recents. Service App removed from recent. Stopping service...");
        // Remove the floating view if needed
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
            floatingView = null;
        }
        stopSelf(); // Stop the service itself
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onDestroy called, floatingView = " + (floatingView != null));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("ACTION_CLOSE_APP"));
        return START_NOT_STICKY; // <- prevent auto-restart after app is killed
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy called, floatingView = " + (floatingView != null));
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        stopService(new Intent(this, SingleModeService.class));
        if (windowManager != null && floatingView != null) {
            windowManager.removeView(floatingView);
            windowManager.removeView(targetView);
            floatingView = null;
        }
        handler1.removeCallbacksAndMessages(null);
        SingleModeSettingDialogBinding singleModeSettingDialogBinding = this.settingBinding;
        if (singleModeSettingDialogBinding != null) {
            singleModeSettingDialogBinding.getRoot();
        }
        TimePickerDialogBinding timePickerDialogBinding = this.timePickerBinding;
        if (timePickerDialogBinding != null) {
            timePickerDialogBinding.getRoot();
        }
        sendBroadcast(new Intent(Common.ACTION_UPDATE_UI));
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                "click_channel",
                "Auto Clicker Service",
                NotificationManager.IMPORTANCE_LOW
        );
        channel.setDescription("This channel is used by Auto Clicker in Single Mode");
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }

    public void singleSettingOnClick(View view) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle eventBundle = new Bundle();
        eventBundle.putString("Setting_mode", "Setting Mode Service");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("Setting_mode_click", eventBundle);

        if (!this.isPlaying) {
            openSettingDialog();
        }
    }

    /* access modifiers changed from: package-private */
    public void singlePlayOnClick(View view) {
        if (this.isPlaying) {
            after();
            try {
                handler1.removeCallbacksAndMessages(null);
            } catch (Exception unused) {
                Log.e(TAG, "singlePlayOnClick: "+unused.getMessage() );
            }
            this.isPlaying = false;
            floatingTouch(true);
            targetTouch(true);
            Glide.with(this).load(R.drawable.play).into((ImageView) this.floatingView.findViewById(R.id.play));
            return;
        } else {
            loadRewardVideoAd(0);
        }
        this.isPlaying = true;
        floatingTouch(false);
        targetTouch(false);
        Glide.with(this).load(R.drawable.stop).into((ImageView) this.floatingView.findViewById(R.id.play));
        before();
        new Handler().postDelayed(new singlePlayClickLisner(this), 300);


        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle eventBundle = new Bundle();
        eventBundle.putString("Play_Single", "Play Single Mode Service");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("Play_Single_click", eventBundle);
        Log.d(TAG, "onTouch: GHI " + this.clickX + " - " + this.clickY);
    }

    public void loadRewardVideoAd(int index) {
        List<String> rewardVideoAds = adUnitsMapHome.get(AdType.REWARD_VIDEO);
        if (rewardVideoAds == null || rewardVideoAds.isEmpty()) {
            Log.d(TAG, "loadRewardVideoAd: No Reward Video Ads Available");
            return;
        }

        if (index >= rewardVideoAds.size()) {
            Log.d(TAG, "loadRewardVideoAd: All Reward Video Ads Loaded");
            return;
        }

        String adUnitId = rewardVideoAds.get(index);
        Log.d(TAG, index + " Trying  Ad Unit: " + adUnitId);

        googleAds.showRewardedAd(MyApplication.getCurrentActivity(), adUnitId, new CustomAdsListener() {
            @Override
            public void onFinish() {
                new Handler().postDelayed(this::onFinish, 1000);
            }

            @Override
            public void onLoading() {
                googleAds.showLoading(MyApplication.getCurrentActivity(), false);
            }

            @Override
            public void onError() {
                loadRewardVideoAd(index + 1);
            }

            @Override
            public void adShow() {
                googleAds.hideLoading();
            }
        });
    }

    public void singlePlayRun() {
        this.count = SpManager.getIntervalCount();
        int intervalType2 = SpManager.getIntervalType();
        this.type = intervalType2;
        this.duration = 0;
        if (intervalType2 == 0) {
            this.duration = SpManager.getIntervalCount();
        } else if (intervalType2 == 1) {
            this.duration = ((long) this.count) * 1000;
        } else if (intervalType2 == 2) {
            this.duration = ((long) this.count) * 60000;
        }
        if (SpManager.getStopType() == 0) {
            if (AutoClicker.instance != null) {
                AutoClicker.instance.clickAt(this.clickX, this.clickY);
                Runnable r0 = new Runnable() {

                    public void run() {
                        AutoClicker.instance.clickAt(SingleModeService.this.clickX, SingleModeService.this.clickY);
                        SingleModeService.handler1.postDelayed(this, SingleModeService.this.duration);
                    }
                };
                runnable1 = r0;
                handler1.postDelayed(r0, this.duration);
            }
        } else if (SpManager.getStopType() == 1) {
            if (AutoClicker.instance != null) {
                long convertTimeToMilliseconds = convertTimeToMilliseconds(SpManager.getRunTimerHour() + ":" + SpManager.getRunTimerMinute() + ":" + SpManager.getRunTimerSecond());
                AutoClicker.instance.clickAt(this.clickX, this.clickY);
                Runnable r2 = new Runnable() {

                    public void run() {
                        AutoClicker.instance.clickAt(SingleModeService.this.clickX, SingleModeService.this.clickY);
                        SingleModeService.handler1.postDelayed(this, SingleModeService.this.duration);
                    }
                };
                runnable1 = r2;
                handler1.postDelayed(r2, this.duration);
                new Handler().postDelayed(new singlePlayRunClickLisner(this), convertTimeToMilliseconds);
                return;
            }
            Log.d("TAG", "onCreate:IS NULL SERVICE ");
        } else if (SpManager.getStopType() == 2) {
            this.nocCount = SpManager.getNumberOfCount();
            AutoClicker.instance.clickAt(this.clickX, this.clickY);
            this.nocCount--;
            Runnable r02 = new Runnable() {

                public void run() {
                    if (SingleModeService.this.nocCount == 0) {
                        try {
                            SingleModeService.this.after();
                            try {
                                SingleModeService.handler1.removeCallbacksAndMessages(null);
                            } catch (Exception unused) {
                            }
                            SingleModeService.this.isPlaying = false;
                            SingleModeService.this.floatingTouch(true);
                            SingleModeService.this.targetTouch(true);
                            Glide.with(SingleModeService.this).load(R.drawable.play).into((ImageView) SingleModeService.this.floatingView.findViewById(R.id.play));
                        } catch (Exception unused2) {
                        }
                    } else {
                        AutoClicker.instance.clickAt(SingleModeService.this.clickX, SingleModeService.this.clickY);
                        SingleModeService.handler1.postDelayed(this, SingleModeService.this.duration);
                        SingleModeService.this.nocCount--;
                    }
                }
            };
            runnable1 = r02;
            handler1.postDelayed(r02, this.duration);
        }
    }

    /* access modifiers changed from: package-private */
    public void singlePlayRunClick() {
        try {
            after();
            try {
                handler1.removeCallbacksAndMessages(null);
            } catch (Exception unused) {
                Log.e(TAG, "singlePlayRunClick: " + unused.getMessage());
            }
            this.isPlaying = false;
            floatingTouch(true);
            targetTouch(true);
            Glide.with(this).load(R.drawable.play).into((ImageView) this.floatingView.findViewById(R.id.play));
        } catch (Exception unused2) {
            Log.e(TAG, "singlePlayRunClick: " + unused2.getMessage());
        }
    }

    public long convertTimeToMilliseconds(String str) {
        String[] split = str.split(":");
        return ((long) Integer.parseInt(split[0]) * 60 * 60 * 1000) + ((long) Integer.parseInt(split[1]) * 60 * 1000) + (Integer.parseInt(split[2]) * 1000L);
    }

    public void after() {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.targetView.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        this.windowManager.updateViewLayout(this.targetView, layoutParams);
    }

    private void before() {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.targetView.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        this.windowManager.updateViewLayout(this.targetView, layoutParams);
    }

    public void createTargetWindow() {
        this.targetView = LayoutInflater.from(this).inflate(R.layout.single_mode_target, null);
        if (Build.VERSION.SDK_INT >= 26) {
            this.targetParams = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
        } else {
            this.targetParams = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
        }
        this.targetParams.gravity = Gravity.CENTER;
        this.targetParams.x = 0;
        this.targetParams.y = 0;
        this.windowManager.addView(this.targetView, this.targetParams);
        targetTouch(true);
    }

    public void targetTouch(boolean z) {
        if (z) {
            this.targetView.findViewById(R.id.target).setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        SingleModeService singleModeService = SingleModeService.this;
                        singleModeService.viewTwoX = ((float) singleModeService.targetParams.x) - motionEvent.getRawX();
                        SingleModeService singleModeService2 = SingleModeService.this;
                        singleModeService2.viewTwoY = ((float) singleModeService2.targetParams.y) - motionEvent.getRawY();
                        return true;
                    } else if (action == 1) {
                        SingleModeService.this.clickX = motionEvent.getRawX();
                        SingleModeService.this.clickY = motionEvent.getRawY();
                        Log.d("AutoClickService", "ABCD XY ->" + SingleModeService.this.clickX + " - " + SingleModeService.this.clickY);
                        return true;
                    } else if (action != 2) {
                        return false;
                    } else {
                        SingleModeService.this.targetParams.x = (int) (motionEvent.getRawX() + SingleModeService.this.viewTwoX);
                        SingleModeService.this.targetParams.y = (int) (motionEvent.getRawY() + SingleModeService.this.viewTwoY);
                        SingleModeService.this.windowManager.updateViewLayout(SingleModeService.this.targetView, SingleModeService.this.targetParams);
                        return true;
                    }
                }
            });
        } else {
            this.targetView.findViewById(R.id.target).setOnTouchListener(null);
        }
    }

    public void floatingTouch(boolean z) {
        if (z) {
            this.floatingView.findViewById(R.id.move).setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View view, MotionEvent motionEvent) {


                    FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
                    Bundle eventBundle = new Bundle();
                    eventBundle.putString("Move", "Move Service");
                    eventBundle.putString("action", "clicked");
                    firebaseAnalytics.logEvent("Move", eventBundle);


                    int action = motionEvent.getAction();
                    if (action == 0) {
                        SingleModeService singleModeService = SingleModeService.this;
                        singleModeService.viewOneX = ((float) singleModeService.params.x) - motionEvent.getRawX();
                        SingleModeService singleModeService2 = SingleModeService.this;
                        singleModeService2.viewOneY = ((float) singleModeService2.params.y) - motionEvent.getRawY();
                        return true;
                    } else if (action != 2) {
                        return false;
                    } else {
                        SingleModeService.this.params.x = (int) (motionEvent.getRawX() + SingleModeService.this.viewOneX);
                        SingleModeService.this.params.y = (int) (motionEvent.getRawY() + SingleModeService.this.viewOneY);
                        SingleModeService.this.windowManager.updateViewLayout(SingleModeService.this.floatingView, SingleModeService.this.params);
                        return true;
                    }
                }
            });
        } else {
            this.floatingView.findViewById(R.id.move).setOnTouchListener(null);
        }
    }

    public void openSettingDialog() {
        this.settingBinding = SingleModeSettingDialogBinding.inflate(LayoutInflater.from(this));
        this.timePickerBinding = TimePickerDialogBinding.inflate(LayoutInflater.from(this));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, Build.VERSION.SDK_INT >= 26 ? 2038 : 2002, 4, -3);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = 0;
        layoutParams.y = 0;
        WindowManager windowManager2 = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.windowManager = windowManager2;
        windowManager2.addView(this.settingBinding.getRoot(), layoutParams);
        setData();
    }

    private void setData() {
        this.settingBinding.popupRl.setVisibility(View.GONE);
        this.intervalType = SpManager.getIntervalType();
        this.intervalValue = SpManager.getIntervalCount();
        this.stopType = SpManager.getStopType();
        this.numberOfCount = SpManager.getNumberOfCount();
        this.runTimerHour = SpManager.getRunTimerHour();
        this.runTimerMinute = SpManager.getRunTimerMinute();
        this.runTimerSecond = SpManager.getRunTimerSecond();
        this.settingBinding.timerTv.setText(String.format("%02d", Integer.valueOf(this.runTimerHour)) + ":" + String.format("%02d", Integer.valueOf(this.runTimerMinute)) + ":" + String.format("%02d", Integer.valueOf(this.runTimerSecond)));
        this.settingBinding.timerTv.setOnClickListener(new settingTimerClickListener(this));
        this.settingBinding.intervalEt.setText(String.valueOf(intervalValue));
        this.settingBinding.intervalEt.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    int parseInt = Integer.parseInt(editable.toString());
                    if (SingleModeService.this.intervalType == 0) {
                        if (parseInt >= 40) {
                            SingleModeService.this.intervalValue = Integer.parseInt(editable.toString());
                            return;
                        }
                        SingleModeService.this.settingBinding.intervalEt.setError(SingleModeService.this.getResources().getString(R.string.ac43));
                    } else if (parseInt != 0) {
                        SingleModeService.this.intervalValue = Integer.parseInt(editable.toString());
                    } else {
                        SingleModeService.this.settingBinding.intervalEt.setError(SingleModeService.this.getResources().getString(R.string.ac43));
                    }
                } else {
                    SingleModeService.this.settingBinding.intervalEt.setError(SingleModeService.this.getResources().getString(R.string.ac43));
                }
            }
        });
        this.settingBinding.nocEt.setText(String.valueOf(numberOfCount));
        this.settingBinding.nocEt.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() <= 0) {
                    SingleModeService.this.settingBinding.nocEt.setError(SingleModeService.this.getResources().getString(R.string.ac42));
                } else if (Integer.parseInt(editable.toString()) != 0) {
                    SingleModeService.this.numberOfCount = Integer.parseInt(editable.toString());
                } else {
                    SingleModeService.this.settingBinding.nocEt.setError(SingleModeService.this.getResources().getString(R.string.ac42));
                }
            }
        });
        this.settingBinding.intervalType.setOnClickListener(new intervalTypeClickListener(this));
        this.settingBinding.down.setOnClickListener(new downClickListener(this));
        setIntervalType(this.intervalType);
        setStopType(this.stopType);
        this.settingBinding.popupRlOne.setOnClickListener(new popupRlOneClickListener(this));
        this.settingBinding.getRoot().setOnClickListener(new settingBindingClickListener(this));
        this.settingBinding.msTv.setOnClickListener(new settingMStvClickListener(this));
        this.settingBinding.sTv.setOnClickListener(new settingStvClickListener(this));
        this.settingBinding.mTv.setOnClickListener(new settingMtvClickListener(this));
        this.settingBinding.runInfinite.setOnClickListener(new settingRunInfiniteClickListener(this));
        this.settingBinding.runTimer.setOnClickListener(new settingRunTimerClickListener(this));
        this.settingBinding.noc.setOnClickListener(new settingNocClickListener(this));
        this.settingBinding.save.setOnClickListener(new settingSaveClickListener(this));
        this.settingBinding.cancel.setOnClickListener(new settingCancelClickListener(this));
    }

    /* access modifiers changed from: package-private */
    public void settingTimerOnClick(View view) {
        openTimePicker();
    }

    /* access modifiers changed from: package-private */
    public void intervalTypeOnClick(View view) {
        this.settingBinding.popupRl.setVisibility(View.VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void downOnClick(View view) {
        this.settingBinding.popupRl.setVisibility(View.VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void settingPopupRlOneOnClick(View view) {
        this.settingBinding.popupRl.setVisibility(View.GONE);
    }

    public void settingOnClick(View view) {
        if (this.settingBinding.popupRl.getVisibility() == View.VISIBLE) {
            this.settingBinding.popupRl.setVisibility(View.GONE);
            return;
        }
        try {
            this.windowManager.removeView(this.settingBinding.getRoot());
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void settingMStvOnClick(View view) {
        if (this.intervalType != 0) {
            this.intervalType = 0;
            setIntervalType(0);
            this.settingBinding.popupRl.setVisibility(View.GONE);
            if (this.settingBinding.intervalEt.getText().length() > 0) {
                int parseInt = Integer.parseInt(this.settingBinding.intervalEt.getText().toString());
                if (this.intervalType == 0) {
                    if (parseInt >= 40) {
                        this.intervalValue = Integer.parseInt(this.settingBinding.intervalEt.getText().toString());
                    } else {
                        this.settingBinding.intervalEt.setError(getResources().getString(R.string.ac43));
                    }
                } else if (parseInt != 0) {
                    this.intervalValue = Integer.parseInt(this.settingBinding.intervalEt.getText().toString());
                } else {
                    this.settingBinding.intervalEt.setError(getResources().getString(R.string.ac43));
                }
            } else {
                this.settingBinding.intervalEt.setError(getResources().getString(R.string.ac43));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void settingStvOnClick(View view) {
        if (this.intervalType != 1) {
            this.intervalType = 1;
            setIntervalType(1);
            this.settingBinding.popupRl.setVisibility(View.GONE);
        }
    }

    /* access modifiers changed from: package-private */
    public void settingMtvOnClick(View view) {
        if (this.intervalType != 2) {
            this.intervalType = 2;
            setIntervalType(2);
            this.settingBinding.popupRl.setVisibility(View.GONE);
        }
    }

    /* access modifiers changed from: package-private */
    public void settingRunInfiniteOnClick(View view) {
        if (this.stopType != 0) {
            this.stopType = 0;
            setStopType(0);
        }
    }

    /* access modifiers changed from: package-private */
    public void settingRunTimerOnClick(View view) {
        if (this.stopType != 1) {
            this.stopType = 1;
            setStopType(1);
        }
    }

    /* access modifiers changed from: package-private */
    public void settingNocOnClick(View view) {
        if (this.stopType != 2) {
            this.stopType = 2;
            setStopType(2);
        }
    }

    /* access modifiers changed from: package-private */
    public void settingSaveOnClick(View view) {
        if (checkIntervalCount() && checkNOCCount()) {
            SpManager.setIntervalType(this.intervalType);
            SpManager.setIntervalCount(this.intervalValue);
            SpManager.setStopType(this.stopType);
            SpManager.setRunTimerHour(this.runTimerHour);
            SpManager.setRunTimerMinute(this.runTimerMinute);
            SpManager.setRunTimerSecond(this.runTimerSecond);
            SpManager.setNumberOfCount(this.numberOfCount);
            try {
                this.windowManager.removeView(this.settingBinding.getRoot());
                this.windowManager.removeView(this.timePickerBinding.getRoot());
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void settingCancelOnClick(View view) {
        try {
            this.windowManager.removeView(this.settingBinding.getRoot());
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
        }
    }

    private void setIntervalType(int i) {
        String str;
        this.settingBinding.intervalType.setText(getResources().getString(i == 0 ? R.string.ac32 : i == 1 ? R.string.ac33 : R.string.ac34));
        String str2 = "#FF000000";
        this.settingBinding.msTv.setTextColor(Color.parseColor(i == 0 ? "#61c665" : str2));
        TextView textView = this.settingBinding.sTv;
        if (i == 1) {
            str = "#61c665";
        } else {
            str = str2;
        }
        textView.setTextColor(Color.parseColor(str));
        TextView textView2 = this.settingBinding.mTv;
        if (i == 2) {
            str2 = "#61c665";
        }
        textView2.setTextColor(Color.parseColor(str2));
    }

    private void setStopType(int i) {
        RequestManager with = Glide.with(this);
        int i2 = R.drawable.rb_selected;
        with.load(Integer.valueOf(i == 0 ? R.drawable.rb_selected : R.drawable.rb_unselected)).into(this.settingBinding.runInfinite);
        Glide.with(this).load(Integer.valueOf(i == 1 ? R.drawable.rb_selected : R.drawable.rb_unselected)).into(this.settingBinding.runTimer);
        RequestManager with2 = Glide.with(this);
        if (i != 2) {
            i2 = R.drawable.rb_unselected;
        }
        with2.load(Integer.valueOf(i2)).into(this.settingBinding.noc);
    }

    public boolean checkIntervalCount() {
        if (this.settingBinding.intervalEt.getText().length() > 0) {
            int parseInt = Integer.parseInt(this.settingBinding.intervalEt.getText().toString());
            if (this.intervalType == 0) {
                if (parseInt >= 40) {
                    return true;
                }
                this.settingBinding.intervalEt.setError(getResources().getString(R.string.ac43));
                return false;
            } else if (parseInt != 0) {
                this.intervalValue = Integer.parseInt(this.settingBinding.intervalEt.getText().toString());
                return true;
            } else {
                this.settingBinding.intervalEt.setError(getResources().getString(R.string.ac43));
                return false;
            }
        } else {
            this.settingBinding.intervalEt.setError(getResources().getString(R.string.ac43));
            return false;
        }
    }

    public boolean checkNOCCount() {
        if (this.settingBinding.nocEt.getText().length() <= 0) {
            this.settingBinding.nocEt.setError(getResources().getString(R.string.ac42));
            return false;
        } else if (Integer.parseInt(this.settingBinding.nocEt.getText().toString()) != 0) {
            return true;
        } else {
            this.settingBinding.nocEt.setError(getResources().getString(R.string.ac42));
            return false;
        }
    }

    private void openTimePicker() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, Build.VERSION.SDK_INT >= 26 ? 2038 : 2002, 4, -3);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = 0;
        layoutParams.y = 0;
        WindowManager windowManager2 = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.windowManager = windowManager2;
        windowManager2.addView(this.timePickerBinding.getRoot(), layoutParams);
        this.timePickerBinding.getRoot().setBackground(getDrawable(R.drawable.popup_bg));
        this.timePickerBinding.numpickerHours.setMaxValue(23);
        this.timePickerBinding.numpickerMinutes.setMaxValue(59);
        this.timePickerBinding.numpickerSeconds.setMaxValue(59);
        this.timePickerBinding.numpickerHours.setValue(this.runTimerHour);
        this.timePickerBinding.numpickerMinutes.setValue(this.runTimerMinute);
        this.timePickerBinding.numpickerSeconds.setValue(this.runTimerSecond);
        this.timePickerBinding.numpickerHours.setFormatter(new NumberPicker.Formatter() {

            public String format(int i) {
                return String.format("%02d", Integer.valueOf(i));
            }
        });
        this.timePickerBinding.numpickerMinutes.setFormatter(new NumberPicker.Formatter() {

            public String format(int i) {
                return String.format("%02d", Integer.valueOf(i));
            }
        });
        this.timePickerBinding.numpickerSeconds.setFormatter(new NumberPicker.Formatter() {

            public String format(int i) {
                return String.format("%02d", Integer.valueOf(i));
            }
        });
        this.timePickerBinding.getRoot().setOnClickListener(new timePickerClickListener(this));
        this.timePickerBinding.cancel.setOnClickListener(new timePickerCancelClickListener(this));
        this.timePickerBinding.ok.setOnClickListener(new timePickerOkClickListener(this));
    }

    public void timePickerOnClick(View view) {
        try {
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void timePickerCancelOnClick(View view) {
        try {
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
        }
    }

    public void timePickerOkOnClick(View view) {
        this.runTimerHour = this.timePickerBinding.numpickerHours.getValue();
        this.runTimerMinute = this.timePickerBinding.numpickerMinutes.getValue();
        int value = this.timePickerBinding.numpickerSeconds.getValue();
        this.runTimerSecond = value;
        if (this.runTimerHour == 0 && this.runTimerMinute == 0 && value == 0) {
            this.timePickerBinding.msg.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new timePickerOkClickLisner(this), 2000);
            return;
        }
        this.settingBinding.timerTv.setText(String.format("%02d", Integer.valueOf(this.runTimerHour)) + ":" + String.format("%02d", Integer.valueOf(this.runTimerMinute)) + ":" + String.format("%02d", Integer.valueOf(this.runTimerSecond)));
        try {
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
        }
    }

    public void timePickerRun() {
        try {
            this.timePickerBinding.msg.setVisibility(View.GONE);
        } catch (Exception unused) {
        }
    }
}
