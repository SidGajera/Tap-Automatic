package com.smartclick.auto.tap.autoclicker.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.ActivitySingleModeSettingBinding;
import com.smartclick.auto.tap.autoclicker.databinding.SaveDialogBinding;
import com.smartclick.auto.tap.autoclicker.databinding.TimePickerDialogBinding;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import java.util.Objects;


public class SingleModeSettingActivity extends BaseActivity {

    ActivitySingleModeSettingBinding binding;
    int intervalType;
    int intervalValue;
    int numberOfCount;
    int runTimerHour;
    int runTimerMinute;
    int runTimerSecond;
    int stopType;
    String TAG = SingleModeSettingActivity.class.getName();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivitySingleModeSettingBinding inflate = ActivitySingleModeSettingBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        setData();

        SpManager.setSingleTargetEventCount(SpManager.getSingleTargetEventCount() + 1);
        Log.e(TAG, "onCreate: event_count single mode target visit ==> " + SpManager.getSingleTargetEventCount());
    }

    private void setData() {
        this.binding.popupRl.setVisibility(GONE);
        this.intervalType = SpManager.getIntervalType();
        this.intervalValue = SpManager.getIntervalCount();
        this.stopType = SpManager.getStopType();
        this.numberOfCount = SpManager.getNumberOfCount();
        this.runTimerHour = SpManager.getRunTimerHour();
        this.runTimerMinute = SpManager.getRunTimerMinute();
        this.runTimerSecond = SpManager.getRunTimerSecond();
        this.binding.timerTv.setText(String.format("%s:%s:%s", String.format("%02d", Integer.valueOf(this.runTimerHour)), String.format("%02d", Integer.valueOf(this.runTimerMinute)), String.format("%02d", Integer.valueOf(this.runTimerSecond))));
        this.binding.timerTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timerTvOnClick(view);
            }
        });
        this.binding.intervalEt.setText(String.valueOf(this.intervalValue));
        this.binding.intervalEt.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    int parseInt = Integer.parseInt(editable.toString());
                    if (intervalType == 0) {
                        if (parseInt >= 40) {
                            intervalValue = Integer.parseInt(editable.toString());
                            return;
                        }
                        binding.intervalEt.setError(getResources().getString(R.string.ac43));
                    } else if (parseInt != 0) {
                        intervalValue = Integer.parseInt(editable.toString());
                    } else {
                        binding.intervalEt.setError(getResources().getString(R.string.ac43));
                    }
                } else {
                    binding.intervalEt.setError(getResources().getString(R.string.ac43));
                }
            }
        });
        this.binding.nocEt.setText(String.valueOf(this.numberOfCount));
        this.binding.nocEt.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() <= 0) {
                    binding.nocEt.setError(getResources().getString(R.string.ac42));
                } else if (Integer.parseInt(editable.toString()) != 0) {
                    numberOfCount = Integer.parseInt(editable.toString());
                } else {
                    binding.nocEt.setError(getResources().getString(R.string.ac42));
                }
            }
        });
        this.binding.intervalType.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                intervalTypeOnClick(view);
            }
        });
        this.binding.down.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                downOnClick(view);
            }
        });
        setIntervalType(this.intervalType);
        setStopType(this.stopType);
        this.binding.popupRl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                popupRLOnClick(view);
            }
        });
        this.binding.msTv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                msTvOnClick(view);
            }
        });
        this.binding.sTv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                sTvOnClick(view);
            }
        });
        this.binding.mTv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mTvOnClick(view);
            }
        });
        this.binding.runInfinite.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                runInfiniteOnClick(view);
            }
        });
        this.binding.runTimer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                runTimerOnClick(view);
            }
        });
        this.binding.noc.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                nocOnClick(view);
            }
        });
        this.binding.reset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                resetOnClick(view);
            }
        });
        this.binding.save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                saveOnClick(view);
            }
        });
        this.binding.back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                backOnClick(view);
            }
        });
    }

    public void timerTvOnClick(View view) {
        openTimePicker();
    }

    /* access modifiers changed from: package-private */
    public void intervalTypeOnClick(View view) {
        this.binding.popupRl.setVisibility(VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void downOnClick(View view) {
        this.binding.popupRl.setVisibility(VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void popupRLOnClick(View view) {
        this.binding.popupRl.setVisibility(GONE);
    }

    /* access modifiers changed from: package-private */
    public void msTvOnClick(View view) {
        if (this.intervalType != 0) {
            this.intervalType = 0;
            setIntervalType(0);
            this.binding.popupRl.setVisibility(GONE);
            if (this.binding.intervalEt.getText().length() > 0) {
                int parseInt = Integer.parseInt(this.binding.intervalEt.getText().toString());
                if (this.intervalType == 0) {
                    if (parseInt >= 40) {
                        this.intervalValue = Integer.parseInt(this.binding.intervalEt.getText().toString());
                    } else {
                        this.binding.intervalEt.setError(getResources().getString(R.string.ac43));
                    }
                } else if (parseInt != 0) {
                    this.intervalValue = Integer.parseInt(this.binding.intervalEt.getText().toString());
                } else {
                    this.binding.intervalEt.setError(getResources().getString(R.string.ac43));
                }
            } else {
                this.binding.intervalEt.setError(getResources().getString(R.string.ac43));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void sTvOnClick(View view) {
        if (this.intervalType != 1) {
            this.intervalType = 1;
            setIntervalType(1);
            this.binding.popupRl.setVisibility(GONE);
        }
    }

    /* access modifiers changed from: package-private */
    public void mTvOnClick(View view) {
        if (this.intervalType != 2) {
            this.intervalType = 2;
            setIntervalType(2);
            this.binding.popupRl.setVisibility(GONE);
        }
    }

    /* access modifiers changed from: package-private */
    public void runInfiniteOnClick(View view) {
        if (this.stopType != 0) {
            this.stopType = 0;
            setStopType(0);
        }
    }

    /* access modifiers changed from: package-private */
    public void runTimerOnClick(View view) {
        if (this.stopType != 1) {
            this.stopType = 1;
            setStopType(1);
        }
    }

    /* access modifiers changed from: package-private */
    public void nocOnClick(View view) {
        if (this.stopType != 2) {
            this.stopType = 2;
            setStopType(2);
        }
    }

    /* access modifiers changed from: package-private */
    public void resetOnClick(View view) {
        SpManager.setIntervalType(0);
        SpManager.setIntervalCount(100);
        SpManager.setStopType(0);
        SpManager.setRunTimerHour(0);
        SpManager.setRunTimerMinute(5);
        SpManager.setRunTimerSecond(0);
        SpManager.setNumberOfCount(10);
        setData();
    }

    /* access modifiers changed from: package-private */
    public void saveOnClick(View view) {
        if (checkIntervalCount() && checkNOCCount()) {
            openSaveDialog();
        }
    }

    /* access modifiers changed from: package-private */
    public void backOnClick(View view) {
        onBackPressed();
    }

    public boolean checkIntervalCount() {
        if (this.binding.intervalEt.getText().length() > 0) {
            int parseInt = Integer.parseInt(this.binding.intervalEt.getText().toString());
            if (this.intervalType == 0) {
                if (parseInt >= 40) {
                    return true;
                }
                this.binding.intervalEt.setError(getResources().getString(R.string.ac43));
                return false;
            } else if (parseInt != 0) {
                this.intervalValue = Integer.parseInt(this.binding.intervalEt.getText().toString());
                return true;
            } else {
                this.binding.intervalEt.setError(getResources().getString(R.string.ac43));
                return false;
            }
        } else {
            this.binding.intervalEt.setError(getResources().getString(R.string.ac43));
            return false;
        }
    }

    public boolean checkNOCCount() {
        if (this.binding.nocEt.getText().length() <= 0) {
            this.binding.nocEt.setError(getResources().getString(R.string.ac42));
            return false;
        } else if (Integer.parseInt(this.binding.nocEt.getText().toString()) != 0) {
            return true;
        } else {
            this.binding.nocEt.setError(getResources().getString(R.string.ac42));
            return false;
        }
    }

    private void openTimePicker() {
        final TimePickerDialogBinding inflate = TimePickerDialogBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        inflate.numpickerHours.setMaxValue(23);
        inflate.numpickerMinutes.setMaxValue(59);
        inflate.numpickerSeconds.setMaxValue(59);
        inflate.numpickerHours.setValue(this.runTimerHour);
        inflate.numpickerMinutes.setValue(this.runTimerMinute);
        inflate.numpickerSeconds.setValue(this.runTimerSecond);
        inflate.numpickerHours.setFormatter(new NumberPicker.Formatter() {

            public String format(int i) {
                return String.format("%02d", Integer.valueOf(i));
            }
        });
        inflate.numpickerMinutes.setFormatter(new NumberPicker.Formatter() {

            public String format(int i) {
                return String.format("%02d", Integer.valueOf(i));
            }
        });
        inflate.numpickerSeconds.setFormatter(new NumberPicker.Formatter() {

            public String format(int i) {
                return String.format("%02d", Integer.valueOf(i));
            }
        });
        inflate.cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            }
        });
        inflate.ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                okOnClick(inflate, dialog, view);
            }
        });
        dialog.show();
    }

    /* access modifiers changed from: package-private */
    public void okOnClick(final TimePickerDialogBinding timePickerDialogBinding, Dialog dialog, View view) {
        this.runTimerHour = timePickerDialogBinding.numpickerHours.getValue();
        this.runTimerMinute = timePickerDialogBinding.numpickerMinutes.getValue();
        int value = timePickerDialogBinding.numpickerSeconds.getValue();
        this.runTimerSecond = value;
        if (this.runTimerHour == 0 && this.runTimerMinute == 0 && value == 0) {
            timePickerDialogBinding.msg.setVisibility(VISIBLE);
            new Handler().postDelayed(new Runnable() {

                public void run() {
                    SingleModeSettingActivity.lambda$openTimePicker$14(timePickerDialogBinding);
                }
            }, 2000);
            return;
        }
        dialog.cancel();
        this.binding.timerTv.setText(String.format("%02d", Integer.valueOf(this.runTimerHour)) + ":" + String.format("%02d", Integer.valueOf(this.runTimerMinute)) + ":" + String.format("%02d", Integer.valueOf(this.runTimerSecond)));
    }

    static void lambda$openTimePicker$14(TimePickerDialogBinding timePickerDialogBinding) {
        try {
            timePickerDialogBinding.msg.setVisibility(GONE);
        } catch (Exception unused) {
        }
    }

    private void openSaveDialog() {
        SaveDialogBinding inflate = SaveDialogBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        inflate.cancel.setOnClickListener(view -> cancelOnClickListener(dialog, view));
        inflate.ok.setOnClickListener(view -> cancelOnClickListener(dialog, view));
        dialog.show();
    }

    /* access modifiers changed from: package-private */
    public void cancelOnClickListener(Dialog dialog, View view) {
        SpManager.setIntervalType(this.intervalType);
        SpManager.setIntervalCount(this.intervalValue);
        SpManager.setStopType(this.stopType);
        SpManager.setRunTimerHour(this.runTimerHour);
        SpManager.setRunTimerMinute(this.runTimerMinute);
        SpManager.setRunTimerSecond(this.runTimerSecond);
        SpManager.setNumberOfCount(this.numberOfCount);
        dialog.cancel();
        finish();
    }

    private void setIntervalType(int i) {
        String str;
        this.binding.intervalType.setText(getResources().getString(i == 0 ? R.string.ac32 : i == 1 ? R.string.ac33 : R.string.ac34));
        String str2 = "#FF000000";
        this.binding.msTv.setTextColor(Color.parseColor(i == 0 ? "#61c665" : str2));
        TextView textView = this.binding.sTv;
        if (i == 1) {
            str = "#61c665";
        } else {
            str = str2;
        }
        textView.setTextColor(Color.parseColor(str));
        TextView textView2 = this.binding.mTv;
        if (i == 2) {
            str2 = "#61c665";
        }
        textView2.setTextColor(Color.parseColor(str2));
    }

    private void setStopType(int i) {
        RequestManager with = Glide.with(this);
        int i2 = R.drawable.rb_selected;
        with.load(i == 0 ? R.drawable.rb_selected : R.drawable.rb_unselected).into(this.binding.runInfinite);
        Glide.with(this).load(i == 1 ? R.drawable.rb_selected : R.drawable.rb_unselected).into(this.binding.runTimer);
        RequestManager with2 = Glide.with(this);
        if (i != 2) {
            i2 = R.drawable.rb_unselected;
        }
        with2.load(i2).into(this.binding.noc);
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
