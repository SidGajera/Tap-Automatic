package com.smartclick.auto.tap.autoclicker.service;

import static android.widget.Toast.LENGTH_SHORT;
import static com.smartclick.auto.tap.autoclicker.MyApplication.adUnitsMapHome;
import static com.smartclick.auto.tap.autoclicker.MyApplication.googleAds;
import static com.smartclick.auto.tap.autoclicker.utils.StorageUtils.TAG;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.smartclick.auto.tap.autoclicker.MyApplication;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.activity.StartActivity;
import com.smartclick.auto.tap.autoclicker.databinding.MultiModeSettingDialogBinding;
import com.smartclick.auto.tap.autoclicker.databinding.TimePickerDialogBinding;
import com.smartclick.auto.tap.autoclicker.manager.CustomAdsListener;
import com.smartclick.auto.tap.autoclicker.model.AdType;
import com.smartclick.auto.tap.autoclicker.model.MultiModel;
import com.smartclick.auto.tap.autoclicker.model.MultiModelTwo;
import com.smartclick.auto.tap.autoclicker.onclick.timePickerMultiClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.timePickerCancelMultiClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.timePickerOkMultiClickListener;
import com.smartclick.auto.tap.autoclicker.onclick.multiModeClickLisner;
import com.smartclick.auto.tap.autoclicker.utils.Common;
import com.smartclick.auto.tap.autoclicker.utils.JsonWriter;
import com.smartclick.auto.tap.autoclicker.utils.MultiModeTargetTwoView;
import com.smartclick.auto.tap.autoclicker.utils.MultiModeTargetView;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import java.util.ArrayList;
import java.util.List;

public class MultiModeService extends Service {
    public static List<MultiModel> arrayList = new ArrayList();
    public static List<MultiModelTwo> demoMultiModelTwo = new ArrayList();
    public static Handler handler1 = new Handler();
    public static boolean isPlaying = false;
    public static Runnable runnable1;
    int arrayListCount;
    int count;
    long duration = 0;
    float f187x;
    float f188y;
    View floatingView;
    String name;
    int nocCount;
    int numberOfCount;
    WindowManager.LayoutParams params;
    int runTimerHour;
    int runTimerMinute;
    int runTimerSecond;
    MultiModeSettingDialogBinding settingBinding;
    int stopType;
    TimePickerDialogBinding timePickerBinding;
    int type;
    float viewOneX;
    float viewOneY;
    WindowManager windowManager;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onCreate() {
        WindowManager.LayoutParams layoutParams;
        WindowManager.LayoutParams layoutParams2;
        super.onCreate();
        this.floatingView = LayoutInflater.from(this).inflate(R.layout.multi_mode_layout, (ViewGroup) null);
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
        demoMultiModelTwo = StartActivity.demoMultiDbModel.getArrayList();
        arrayList.clear();
        for (int i = 0; i < demoMultiModelTwo.size(); i++) {
            if (demoMultiModelTwo.get(i).getType() == 0) {
                MultiModel multiModel = new MultiModel();
                multiModel.setType(0);
                multiModel.setTypeOne(new float[]{demoMultiModelTwo.get(i).getClickOneX(), demoMultiModelTwo.get(i).getClickOneY()});
                arrayList.add(multiModel);
                if (Build.VERSION.SDK_INT >= 26) {
                    layoutParams2 = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
                } else {
                    layoutParams2 = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
                }
                layoutParams2.gravity = Gravity.TOP | Gravity.START;
                layoutParams2.x = ((int) demoMultiModelTwo.get(i).getClickOneX()) - 67;
                layoutParams2.y = ((int) demoMultiModelTwo.get(i).getClickOneY()) - 120;
                MultiModeTargetView multiModeTargetView = new MultiModeTargetView(this, this.windowManager, layoutParams2, arrayList.size() - 1);
                this.windowManager.addView(multiModeTargetView, layoutParams2);
                List<MultiModel> list = arrayList;
                list.get(list.size() - 1).setMultiModeTargetView(multiModeTargetView);
            } else {
                MultiModel multiModel2 = new MultiModel();
                multiModel2.setType(1);
                multiModel2.setTypeTwo(new float[]{demoMultiModelTwo.get(i).getClickOneX(), demoMultiModelTwo.get(i).getClickOneY(), demoMultiModelTwo.get(i).getClickTwoX(), demoMultiModelTwo.get(i).getClickTwoY()});
                arrayList.add(multiModel2);
                if (Build.VERSION.SDK_INT >= 26) {
                    layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
                } else {
                    layoutParams = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
                }
                layoutParams.gravity = Gravity.TOP | Gravity.START;
                layoutParams.x = 0;
                layoutParams.y = 0;
                MultiModeTargetTwoView multiModeTargetTwoView = new MultiModeTargetTwoView(this, this.windowManager, arrayList.size() - 1, 1);
                this.windowManager.addView(multiModeTargetTwoView, layoutParams);
                List<MultiModel> list2 = arrayList;
                list2.get(list2.size() - 1).setMultiModeTargetTwoView(multiModeTargetTwoView);
            }
        }
        if (!arrayList.isEmpty()) {
            ((ImageView) this.floatingView.findViewById(R.id.save)).setImageDrawable(getResources().getDrawable(R.drawable.effect_save_m));
        } else {
            ((ImageView) this.floatingView.findViewById(R.id.save)).setImageDrawable(getResources().getDrawable(R.drawable.save_pressed_two));
        }
        this.floatingView.findViewById(R.id.close).setOnClickListener(new multiModeClickLisner(this));
        this.floatingView.findViewById(R.id.save).setOnClickListener(MultiModeService.this::floatingViewSaveOnClick);
        this.floatingView.findViewById(R.id.pluse).setOnClickListener(MultiModeService.this::floatingViewPluseOnClick);
        this.floatingView.findViewById(R.id.minus).setOnClickListener(MultiModeService.this::floatingViewMinusOnClick);
        this.floatingView.findViewById(R.id.swipe).setOnClickListener(MultiModeService.this::floatingViewSwipeOnClick);
        this.floatingView.findViewById(R.id.play).setOnClickListener(MultiModeService.this::floatingPlayOnClick);
        this.floatingView.findViewById(R.id.move).setOnTouchListener((view, motionEvent) -> {
            if (MultiModeService.isPlaying) {
                return false;
            }
            int action = motionEvent.getAction();
            if (action == 0) {
                MultiModeService main6multimodeservice = MultiModeService.this;
                main6multimodeservice.viewOneX = (float) main6multimodeservice.params.x;
                MultiModeService main6multimodeservice2 = MultiModeService.this;
                main6multimodeservice2.viewOneY = (float) main6multimodeservice2.params.y;
                MultiModeService.this.f187x = motionEvent.getRawX();
                MultiModeService.this.f188y = motionEvent.getRawY();
                return true;
            } else if (action != 2) {
                return false;
            } else {
                MultiModeService.this.params.x = (int) ((MultiModeService.this.viewOneX + motionEvent.getRawX()) - MultiModeService.this.f187x);
                MultiModeService.this.params.y = (int) ((MultiModeService.this.viewOneY + motionEvent.getRawY()) - MultiModeService.this.f188y);
                MultiModeService.this.windowManager.updateViewLayout(MultiModeService.this.floatingView, MultiModeService.this.params);
                return true;
            }
        });
        this.floatingView.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MultiModeService.this.floatingViewSettingOnClick(view);
            }
        });
    }

    public void multiModeOnClick(View view) {
        Intent broadcastIntent = new Intent(getPackageName() + "." + Common.ACTION_UPDATE_UI);
        broadcastIntent.putExtra("switch_state", "multi_mode_off");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);

        Log.e(TAG, "singleCloseOnClick: Multi floatingView closed");

        if (!isPlaying) {
            stopSelf();
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved called — Multi service App closed from recents. Stopping service...");
        // Remove the floating view if needed
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
            floatingView = null;
        }
        stopSelf(); // Stop the service itself
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MultiModeService.class));

        handler1.removeCallbacksAndMessages(null);
        if (windowManager != null && floatingView != null) {
            windowManager.removeView(floatingView);
            floatingView = null;
        }
        stopSelf();

        List<MultiModel> list = arrayList;
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getType() == 0) {
                    arrayList.get(i).getMultiModeTargetView().remove();
                } else {
                    arrayList.get(i).getMultiModeTargetTwoView().remove();
                }
            }
        }
        MultiModeSettingDialogBinding multiModeSettingDialogBinding = this.settingBinding;
        if (multiModeSettingDialogBinding != null) {
            multiModeSettingDialogBinding.getRoot();
        }
        TimePickerDialogBinding timePickerDialogBinding = this.timePickerBinding;
        if (timePickerDialogBinding != null) {
            timePickerDialogBinding.getRoot();
        }
        assert arrayList != null;
        arrayList.clear();
        sendBroadcast(new Intent(Common.ACTION_UPDATE_UI));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // your logic
        return START_NOT_STICKY; // <- prevent auto-restart after app is killed
    }

    public void floatingViewSaveOnClick(View view) {
        if (!arrayList.isEmpty()) {
            Log.d(TAG, "floatingViewSaveOnClick onCreate: click 1 arrayList size == " + arrayList.size());
            new SaveAsyncTask().execute(new String[0]);
            return;
        }
        Toast.makeText(this, getResources().getString(R.string.ac75), LENGTH_SHORT).show();
    }

    /* access modifiers changed from: package-private */
    public void floatingViewPluseOnClick(View view) {
        WindowManager.LayoutParams layoutParams;
        if (!isPlaying) {
            MultiModel multiModel = new MultiModel();
            multiModel.setType(0);
            multiModel.setTypeOne(new float[]{0.0f, 0.0f});
            arrayList.add(multiModel);
            if (Build.VERSION.SDK_INT >= 26) {
                layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
            } else {
                layoutParams = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
            }
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.x = 0;
            layoutParams.y = 0;
            MultiModeTargetView multiModeTargetView = new MultiModeTargetView(this, this.windowManager, layoutParams, arrayList.size() - 1);
            this.windowManager.addView(multiModeTargetView, layoutParams);
            List<MultiModel> list = arrayList;
            list.get(list.size() - 1).setMultiModeTargetView(multiModeTargetView);
            if (!arrayList.isEmpty()) {
                ((ImageView) this.floatingView.findViewById(R.id.save)).setImageDrawable(getResources().getDrawable(R.drawable.effect_save_m));
            } else {
                ((ImageView) this.floatingView.findViewById(R.id.save)).setImageDrawable(getResources().getDrawable(R.drawable.save_pressed_two));
            }
        }
        Log.d(TAG, "floatingViewPlusesOnClick onCreate: click 1 ");
    }

    public void floatingViewMinusOnClick(View view) {
        if (!isPlaying) {
            List<MultiModel> list = arrayList;
            if (list != null && !list.isEmpty()) {
                List<MultiModel> list2 = arrayList;
                if (list2.get(list2.size() - 1).getType() == 0) {
                    List<MultiModel> list3 = arrayList;
                    list3.get(list3.size() - 1).getMultiModeTargetView().remove();
                } else {
                    List<MultiModel> list4 = arrayList;
                    list4.get(list4.size() - 1).getMultiModeTargetTwoView().remove();
                }
                List<MultiModel> list5 = arrayList;
                list5.remove(list5.size() - 1);
            }
            if (!(arrayList != null && arrayList.isEmpty())) {
                ((ImageView) this.floatingView.findViewById(R.id.save)).setImageDrawable(getResources().getDrawable(R.drawable.effect_save_m));
            } else {
                ((ImageView) this.floatingView.findViewById(R.id.save)).setImageDrawable(getResources().getDrawable(R.drawable.save_pressed_two));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void floatingViewSwipeOnClick(View view) {
        WindowManager.LayoutParams layoutParams;
        if (!isPlaying) {
            MultiModel multiModel = new MultiModel();
            multiModel.setType(1);
            multiModel.setTypeTwo(new float[]{0.0f, 0.0f, 0.0f, 200.0f});
            arrayList.add(multiModel);
            if (Build.VERSION.SDK_INT >= 26) {
                layoutParams = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
            } else {
                layoutParams = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
            }
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.x = 0;
            layoutParams.y = 0;
            MultiModeTargetTwoView multiModeTargetTwoView = new MultiModeTargetTwoView(this, this.windowManager, arrayList.size() - 1, 0);
            this.windowManager.addView(multiModeTargetTwoView, layoutParams);
            List<MultiModel> list = arrayList;
            list.get(list.size() - 1).setMultiModeTargetTwoView(multiModeTargetTwoView);
            if (!arrayList.isEmpty()) {
                ((ImageView) this.floatingView.findViewById(R.id.save)).setImageDrawable(getResources().getDrawable(R.drawable.effect_save_m));
            } else {
                ((ImageView) this.floatingView.findViewById(R.id.save)).setImageDrawable(getResources().getDrawable(R.drawable.save_pressed_two));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void floatingPlayOnClick(View view) {
        if (isPlaying) {
            afterCall();
            try {
                handler1.removeCallbacksAndMessages(null);
            } catch (Exception unused) {
                Log.e(TAG, "floatingPlayOnClick: " + unused.getMessage());
            }
            isPlaying = false;
            Glide.with(this).load(Integer.valueOf(R.drawable.play)).into((ImageView) this.floatingView.findViewById(R.id.play));
            return;
        } else {
            loadRewardVideoAd(0);
        }
        isPlaying = true;
        Glide.with(this).load(Integer.valueOf(R.drawable.stop)).into((ImageView) this.floatingView.findViewById(R.id.play));
        beforeCall();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                floatingPlayIntervalCount();
            }
        }, 300);
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

    public void floatingPlayIntervalCount() {
        this.arrayListCount = 0;
        this.count = SpManager.getMultiIntervalCount();
        int multiIntervalType = SpManager.getMultiIntervalType();
        this.type = multiIntervalType;
        this.duration = 0;
        if (multiIntervalType == 0) {
            this.duration = (long) SpManager.getIntervalCount();
        } else if (multiIntervalType == 1) {
            this.duration = ((long) this.count) * 1000;
        } else if (multiIntervalType == 2) {
            this.duration = ((long) this.count) * 60000;
        }
        if (StartActivity.demoMultiDbModel.getStopType() == 0) {
            if (AutoClicker.instance != null) {
                List<MultiModel> list = arrayList;
                if (list == null || list.size() <= 0) {
                    afterCall();
                    return;
                }
                if (arrayList.get(this.arrayListCount).getType() == 0) {
                    AutoClicker.instance.clickAt(arrayList.get(this.arrayListCount).getTypeOne()[0], arrayList.get(this.arrayListCount).getTypeOne()[1]);
                } else {
                    AutoClicker.instance.swipe((int) arrayList.get(this.arrayListCount).getTypeTwo()[0], (int) arrayList.get(this.arrayListCount).getTypeTwo()[1], (int) arrayList.get(this.arrayListCount).getTypeTwo()[2], (int) arrayList.get(this.arrayListCount).getTypeTwo()[3], SpManager.getMultiSwipeCount());
                }
                int size = arrayList.size() - 1;
                int i = this.arrayListCount;
                if (size == i) {
                    this.arrayListCount = 0;
                } else {
                    this.arrayListCount = i + 1;
                }
                Runnable r0 = new Runnable() {

                    public void run() {
                        if (MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getType() == 0) {
                            AutoClicker.instance.clickAt(MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeOne()[0], MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeOne()[1]);
                            if (MultiModeService.arrayList.size() - 1 == MultiModeService.this.arrayListCount) {
                                MultiModeService.this.arrayListCount = 0;
                            } else {
                                MultiModeService.this.arrayListCount++;
                            }
                            MultiModeService.handler1.postDelayed(this, MultiModeService.this.duration);
                            return;
                        }
                        AutoClicker.instance.swipe((int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[0], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[1], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[2], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[3], SpManager.getMultiSwipeCount());
                        if (MultiModeService.arrayList.size() - 1 == MultiModeService.this.arrayListCount) {
                            MultiModeService.this.arrayListCount = 0;
                        } else {
                            MultiModeService.this.arrayListCount++;
                        }
                        MultiModeService.handler1.postDelayed(this, MultiModeService.this.duration);
                    }
                };
                runnable1 = r0;
                handler1.postDelayed(r0, this.duration);
                return;
            }
            Log.d("TAG", "onCreate:IS NULL SERVICE ");
            afterCall();
        } else if (StartActivity.demoMultiDbModel.getStopType() == 1) {
            long convertTimeToMilliseconds = convertTimeToMilliseconds(StartActivity.demoMultiDbModel.getHour() + ":" + StartActivity.demoMultiDbModel.getMinute() + ":" + StartActivity.demoMultiDbModel.getSecond());
            if (AutoClicker.instance != null) {
                List<MultiModel> list2 = arrayList;
                if (list2 == null || list2.size() <= 0) {
                    afterCall();
                    return;
                }
                if (arrayList.get(this.arrayListCount).getType() == 0) {
                    AutoClicker.instance.clickAt(arrayList.get(this.arrayListCount).getTypeOne()[0], arrayList.get(this.arrayListCount).getTypeOne()[1]);
                } else {
                    AutoClicker.instance.swipe((int) arrayList.get(this.arrayListCount).getTypeTwo()[0], (int) arrayList.get(this.arrayListCount).getTypeTwo()[1], (int) arrayList.get(this.arrayListCount).getTypeTwo()[2], (int) arrayList.get(this.arrayListCount).getTypeTwo()[3], SpManager.getMultiSwipeCount());
                }
                int size2 = arrayList.size() - 1;
                int i2 = this.arrayListCount;
                if (size2 == i2) {
                    this.arrayListCount = 0;
                } else {
                    this.arrayListCount = i2 + 1;
                }
                Runnable r02 = new Runnable() {

                    public void run() {
                        if (MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getType() == 0) {
                            AutoClicker.instance.clickAt(MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeOne()[0], MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeOne()[1]);
                            if (MultiModeService.arrayList.size() - 1 == MultiModeService.this.arrayListCount) {
                                MultiModeService.this.arrayListCount = 0;
                            } else {
                                MultiModeService.this.arrayListCount++;
                            }
                            MultiModeService.handler1.postDelayed(this, MultiModeService.this.duration);
                            return;
                        }
                        AutoClicker.instance.swipe((int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[0], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[1], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[2], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[3], SpManager.getMultiSwipeCount());
                        if (MultiModeService.arrayList.size() - 1 == MultiModeService.this.arrayListCount) {
                            MultiModeService.this.arrayListCount = 0;
                        } else {
                            MultiModeService.this.arrayListCount++;
                        }
                        MultiModeService.handler1.postDelayed(this, MultiModeService.this.duration);
                    }
                };
                runnable1 = r02;
                handler1.postDelayed(r02, this.duration);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        floatingPlayStop();
                    }
                }, convertTimeToMilliseconds);
                return;
            }
            Log.d("TAG", "onCreate:IS NULL SERVICE ");
            afterCall();
        } else if (StartActivity.demoMultiDbModel.getStopType() == 2) {
            this.nocCount = StartActivity.demoMultiDbModel.getNocCount();
            if (AutoClicker.instance != null) {
                List<MultiModel> list3 = arrayList;
                if (list3 == null || list3.size() <= 0) {
                    afterCall();
                    return;
                }
                if (arrayList.get(this.arrayListCount).getType() == 0) {
                    AutoClicker.instance.clickAt(arrayList.get(this.arrayListCount).getTypeOne()[0], arrayList.get(this.arrayListCount).getTypeOne()[1]);
                } else {
                    AutoClicker.instance.swipe((int) arrayList.get(this.arrayListCount).getTypeTwo()[0], (int) arrayList.get(this.arrayListCount).getTypeTwo()[1], (int) arrayList.get(this.arrayListCount).getTypeTwo()[2], (int) arrayList.get(this.arrayListCount).getTypeTwo()[3], SpManager.getMultiSwipeCount());
                }
                int size3 = arrayList.size() - 1;
                int i3 = this.arrayListCount;
                if (size3 == i3) {
                    this.arrayListCount = 0;
                } else {
                    this.arrayListCount = i3 + 1;
                }
                this.nocCount--;
                Runnable r03 = new Runnable() {

                    public void run() {
                        if (MultiModeService.this.nocCount == 0) {
                            try {
                                MultiModeService.this.afterCall();
                                try {
                                    MultiModeService.handler1.removeCallbacksAndMessages(null);
                                } catch (Exception unused) {
                                    Log.e(TAG, "run: " + unused.getMessage());
                                }
                                MultiModeService.isPlaying = false;
                                Glide.with(MultiModeService.this).load(R.drawable.play).into((ImageView) MultiModeService.this.floatingView.findViewById(R.id.play));
                            } catch (Exception unused2) {
                                Log.e(TAG, "run: " + unused2.getMessage());
                            }
                        } else if (MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getType() == 0) {
                            AutoClicker.instance.clickAt(MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeOne()[0], MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeOne()[1]);
                            if (MultiModeService.arrayList.size() - 1 == MultiModeService.this.arrayListCount) {
                                MultiModeService.this.arrayListCount = 0;
                                MultiModeService.this.nocCount--;
                            } else {
                                MultiModeService.this.arrayListCount++;
                            }
                            MultiModeService.handler1.postDelayed(this, MultiModeService.this.duration);
                        } else {
                            AutoClicker.instance.swipe((int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[0], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[1], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[2], (int) MultiModeService.arrayList.get(MultiModeService.this.arrayListCount).getTypeTwo()[3], SpManager.getMultiSwipeCount());
                            if (MultiModeService.arrayList.size() - 1 == MultiModeService.this.arrayListCount) {
                                MultiModeService.this.arrayListCount = 0;
                                MultiModeService.this.nocCount--;
                            } else {
                                MultiModeService.this.arrayListCount++;
                            }
                            MultiModeService.handler1.postDelayed(this, MultiModeService.this.duration);
                        }
                    }
                };
                runnable1 = r03;
                handler1.postDelayed(r03, this.duration);
                return;
            }
            Log.d("TAG", "onCreate:IS NULL SERVICE ");
            afterCall();
        }
    }

    /* access modifiers changed from: package-private */
    public void floatingPlayStop() {
        try {
            afterCall();
            try {
                handler1.removeCallbacksAndMessages(null);
            } catch (Exception unused) {
                Log.e(TAG, "try try catch floatingPlayStop: " + unused.getMessage());
            }
            isPlaying = false;
            Glide.with(this).load(Integer.valueOf(R.drawable.play)).into((ImageView) this.floatingView.findViewById(R.id.play));
        } catch (Exception unused2) {
            Log.e(TAG, "catch floatingPlayStop: " + unused2.getMessage());
        }
    }

    /* access modifiers changed from: package-private */
    public void floatingViewSettingOnClick(View view) {
        if (!isPlaying) {
            openSettingDialog();
        }
    }

    public long convertTimeToMilliseconds(String str) {
        String[] split = str.split(":");
        return ((long) (Integer.parseInt(split[0]) * 60 * 60 * 1000)) + ((long) (Integer.parseInt(split[1]) * 60 * 1000)) + ((long) (Integer.parseInt(split[2]) * 1000));
    }

    /* access modifiers changed from: package-private */
    public class SaveAsyncTask extends AsyncTask<String, Void, String> {
        SaveAsyncTask() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            MultiModeService.this.floatingView.findViewById(R.id.save).setEnabled(false);
        }

        public String doInBackground(String[] strArr) {
            Log.d("TAG", "onCreate: click 2 ");
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < MultiModeService.arrayList.size(); i++) {
                if (MultiModeService.arrayList.get(i).getType() == 0) {
                    arrayList.add(new MultiModelTwo(MultiModeService.arrayList.get(i).getType(), MultiModeService.arrayList.get(i).getTypeOne()[0], MultiModeService.arrayList.get(i).getTypeOne()[1], 0.0f, 0.0f));
                } else {
                    arrayList.add(new MultiModelTwo(MultiModeService.arrayList.get(i).getType(), MultiModeService.arrayList.get(i).getTypeTwo()[0], MultiModeService.arrayList.get(i).getTypeTwo()[1], MultiModeService.arrayList.get(i).getTypeTwo()[2], MultiModeService.arrayList.get(i).getTypeTwo()[3]));
                }
            }
            StartActivity.demoMultiDbModel.setArrayList(arrayList);
            if (StartActivity.position == -1) {
                StartActivity.multiDbModelList.add(StartActivity.demoMultiDbModel);
            } else {
                StartActivity.multiDbModelList.get(StartActivity.position).setName(StartActivity.demoMultiDbModel.getName());
                StartActivity.multiDbModelList.get(StartActivity.position).setIntervalType(StartActivity.demoMultiDbModel.getIntervalType());
                StartActivity.multiDbModelList.get(StartActivity.position).setIntervalCount(StartActivity.demoMultiDbModel.getIntervalCount());
                StartActivity.multiDbModelList.get(StartActivity.position).setSwipeCount(StartActivity.demoMultiDbModel.getSwipeCount());
                StartActivity.multiDbModelList.get(StartActivity.position).setStopType(StartActivity.demoMultiDbModel.getStopType());
                StartActivity.multiDbModelList.get(StartActivity.position).setHour(StartActivity.demoMultiDbModel.getHour());
                StartActivity.multiDbModelList.get(StartActivity.position).setMinute(StartActivity.demoMultiDbModel.getMinute());
                StartActivity.multiDbModelList.get(StartActivity.position).setSecond(StartActivity.demoMultiDbModel.getSecond());
                StartActivity.multiDbModelList.get(StartActivity.position).setNocCount(StartActivity.demoMultiDbModel.getNocCount());
                StartActivity.multiDbModelList.get(StartActivity.position).setArrayList(StartActivity.demoMultiDbModel.getArrayList());
            }
            JsonWriter.writeListToJsonFile(MultiModeService.this, "Data.json", StartActivity.multiDbModelList);
            return null;
        }

        public void onPostExecute(String str) {
            MultiModeService.this.floatingView.findViewById(R.id.save).setEnabled(true);
            MultiModeService main6multimodeservice = MultiModeService.this;
            Toast.makeText(main6multimodeservice, main6multimodeservice.getResources().getString(R.string.ac85), LENGTH_SHORT).show();
        }
    }

    public void afterCall() {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getType() == 0) {
                after(arrayList.get(i).getMultiModeTargetView());
            } else {
                MultiModeTargetTwoView multiModeTargetTwoView = arrayList.get(i).getMultiModeTargetTwoView();
                after(multiModeTargetTwoView);
                multiModeTargetTwoView.afterCall();
            }
        }
    }

    private void beforeCall() {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getType() == 0) {
                before(arrayList.get(i).getMultiModeTargetView());
            } else {
                MultiModeTargetTwoView multiModeTargetTwoView = arrayList.get(i).getMultiModeTargetTwoView();
                before(multiModeTargetTwoView);
                multiModeTargetTwoView.beforeCall();
            }
        }
    }

    private void after(View view) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        this.windowManager.updateViewLayout(view, layoutParams);
    }

    private void before(View view) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        ;
        this.windowManager.updateViewLayout(view, layoutParams);
    }

    public void openSettingDialog() {
        this.settingBinding = MultiModeSettingDialogBinding.inflate(LayoutInflater.from(this));
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
        this.name = StartActivity.demoMultiDbModel.getName();
        this.stopType = StartActivity.demoMultiDbModel.getStopType();
        this.numberOfCount = StartActivity.demoMultiDbModel.getNocCount();
        this.runTimerHour = StartActivity.demoMultiDbModel.getHour();
        this.runTimerMinute = StartActivity.demoMultiDbModel.getMinute();
        this.runTimerSecond = StartActivity.demoMultiDbModel.getSecond();
        this.settingBinding.timerTv.setText("" + String.format("%02d", Integer.valueOf(this.runTimerHour)) + ":" + String.format("%02d", Integer.valueOf(this.runTimerMinute)) + ":" + String.format("%02d", Integer.valueOf(this.runTimerSecond)));
        this.settingBinding.timerTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MultiModeService.this.settingTimerTvOnClick(view);
            }
        });
        this.settingBinding.name.setText(String.valueOf(name));
        this.settingBinding.name.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    MultiModeService.this.settingBinding.name.setError(null);
                } else {
                    MultiModeService.this.settingBinding.name.setError(MultiModeService.this.getResources().getString(R.string.ac83));
                }
            }
        });
        this.settingBinding.nocEt.setText("" + this.numberOfCount);
        this.settingBinding.nocEt.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() <= 0) {
                    MultiModeService.this.settingBinding.nocEt.setError(MultiModeService.this.getResources().getString(R.string.ac42));
                } else if (Integer.parseInt(editable.toString()) != 0) {
                    MultiModeService.this.numberOfCount = Integer.parseInt(editable.toString());
                } else {
                    MultiModeService.this.settingBinding.nocEt.setError(MultiModeService.this.getResources().getString(R.string.ac42));
                }
            }
        });
        setStopType(this.stopType);
        this.settingBinding.getRoot().setOnClickListener(MultiModeService.this::multiModeClickListener);
        this.settingBinding.runInfinite.setOnClickListener(MultiModeService.this::settingRunInfiniteClickListener);
        this.settingBinding.runTimer.setOnClickListener(MultiModeService.this::settingRunTimerClickListener);
        this.settingBinding.noc.setOnClickListener(MultiModeService.this::settingNocClickListener);
        this.settingBinding.save.setOnClickListener(MultiModeService.this::settingSaveClickListener);
        this.settingBinding.cancel.setOnClickListener(MultiModeService.this::settingCancelClickListener);
    }

    /* access modifiers changed from: package-private */
    public void settingTimerTvOnClick(View view) {
        openTimePicker();
    }

    /* access modifiers changed from: package-private */
    public void multiModeClickListener(View view) {
        try {
            this.windowManager.removeView(this.settingBinding.getRoot());
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
            Log.e(TAG, "multiModeOnClick: " + unused.getMessage());
        }
    }

    /* access modifiers changed from: package-private */
    public void settingRunInfiniteClickListener(View view) {
        if (this.stopType != 0) {
            this.stopType = 0;
            setStopType(0);
        }
    }

    /* access modifiers changed from: package-private */
    public void settingRunTimerClickListener(View view) {
        if (this.stopType != 1) {
            this.stopType = 1;
            setStopType(1);
        }
    }

    /* access modifiers changed from: package-private */
    public void settingNocClickListener(View view) {
        if (this.stopType != 2) {
            this.stopType = 2;
            setStopType(2);
        }
    }

    /* access modifiers changed from: package-private */
    public void settingSaveClickListener(View view) {
        if (this.settingBinding.name.getText().length() <= 0) {
            this.settingBinding.name.setError(getResources().getString(R.string.ac83));
        } else if (checkNOCCount()) {
            StartActivity.demoMultiDbModel.setName(this.settingBinding.name.getText().toString());
            StartActivity.demoMultiDbModel.setStopType(this.stopType);
            StartActivity.demoMultiDbModel.setHour(this.runTimerHour);
            StartActivity.demoMultiDbModel.setMinute(this.runTimerMinute);
            StartActivity.demoMultiDbModel.setSecond(this.runTimerSecond);
            StartActivity.demoMultiDbModel.setNocCount(this.numberOfCount);
            try {
                this.windowManager.removeView(this.settingBinding.getRoot());
                this.windowManager.removeView(this.timePickerBinding.getRoot());
            } catch (Exception unused) {
                Log.e(TAG, "settingSaveOnClick: " + unused.getMessage());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void settingCancelClickListener(View view) {
        try {
            this.windowManager.removeView(this.settingBinding.getRoot());
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
            Log.e(TAG, "settingCancelOnClick: " + unused.getMessage());
        }
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
        this.timePickerBinding.getRoot().setOnClickListener(new timePickerMultiClickListener(this));
        this.timePickerBinding.cancel.setOnClickListener(new timePickerCancelMultiClickListener(this));
        this.timePickerBinding.ok.setOnClickListener(new timePickerOkMultiClickListener(this));
    }

    public void timePickerMultiOnClick(View view) {
        try {
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
            Log.e(TAG, "timePickerMultiOnClick: " + unused.getMessage());
        }
    }

    public void timePickerCancelMultiOnClick(View view) {
        try {
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
            Log.e(TAG, "timePickerCancelMultiOnClick: " + unused.getMessage());
        }
    }

    public void timePickerOkMultiOnClick(View view) {
        this.runTimerHour = this.timePickerBinding.numpickerHours.getValue();
        this.runTimerMinute = this.timePickerBinding.numpickerMinutes.getValue();
        int value = this.timePickerBinding.numpickerSeconds.getValue();
        this.runTimerSecond = value;
        if (this.runTimerHour == 0 && this.runTimerMinute == 0 && value == 0) {
            this.timePickerBinding.msg.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    MultiModeService.this.timePickerVisibilityGone();
                }
            }, 2000);
            return;
        }
        this.settingBinding.timerTv.setText(String.format("%s:%s:%s", String.format("%02d", Integer.valueOf(this.runTimerHour)), String.format("%02d", Integer.valueOf(this.runTimerMinute)), String.format("%02d", Integer.valueOf(this.runTimerSecond))));
        try {
            this.windowManager.removeView(this.timePickerBinding.getRoot());
        } catch (Exception unused) {
            Log.e(TAG, "timePickerOkMultiOnClick: " + unused.getMessage());
        }
    }

    /* access modifiers changed from: package-private */
    public void timePickerVisibilityGone() {
        try {
            this.timePickerBinding.msg.setVisibility(View.GONE);
        } catch (Exception unused) {
            Log.e(TAG, "timePickerVisibilityGone: " + unused.getMessage());
        }
    }
}
