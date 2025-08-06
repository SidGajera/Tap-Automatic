package com.smartclick.auto.tap.autoclicker.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityMultiModeSettingBinding;
import com.smartclick.auto.tap.autoclicker.databinding.SaveDialogBinding;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import java.util.Objects;

public class MultiModeSettingActivity extends BaseActivity {
    ActivityMultiModeSettingBinding binding;
    int intervalType;
    int intervalValue;
    int swipeCount;
    String TAG = MultiModeSettingActivity.class.getName();

    /* access modifiers changed from: protected */
    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityMultiModeSettingBinding inflate = ActivityMultiModeSettingBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        setData();

        SpManager.setMultiTargetEventCount(SpManager.getMultiTargetEventCount() + 1);
        Log.e(TAG, "onCreate: event_count multi mode target visit ==> " + SpManager.getMultiTargetEventCount());
    }

    private void setData() {
        this.binding.popupRl.setVisibility(GONE);
        this.intervalType = SpManager.getMultiIntervalType();
        this.intervalValue = SpManager.getMultiIntervalCount();
        this.swipeCount = SpManager.getMultiSwipeCount();
        this.binding.intervalEt.setText(String.valueOf(this.intervalValue));
        this.binding.intervalEt.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    int parseInt = Integer.parseInt(editable.toString());
                    if (MultiModeSettingActivity.this.intervalType == 0) {
                        if (parseInt >= 40) {
                            MultiModeSettingActivity.this.intervalValue = Integer.parseInt(editable.toString());
                            return;
                        }
                        MultiModeSettingActivity.this.binding.intervalEt.setError(MultiModeSettingActivity.this.getResources().getString(R.string.ac63));
                    } else if (parseInt != 0) {
                        MultiModeSettingActivity.this.intervalValue = Integer.parseInt(editable.toString());
                    } else {
                        MultiModeSettingActivity.this.binding.intervalEt.setError(MultiModeSettingActivity.this.getResources().getString(R.string.ac63));
                    }
                } else {
                    MultiModeSettingActivity.this.binding.intervalEt.setError(MultiModeSettingActivity.this.getResources().getString(R.string.ac63));
                }
            }
        });
        this.binding.swipeEt.setText(String.valueOf(swipeCount));
        this.binding.swipeEt.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() <= 0) {
                    MultiModeSettingActivity.this.binding.swipeEt.setError(MultiModeSettingActivity.this.getResources().getString(R.string.ac62));
                } else if (Integer.parseInt(editable.toString()) >= 300) {
                    MultiModeSettingActivity.this.swipeCount = Integer.parseInt(editable.toString());
                } else {
                    MultiModeSettingActivity.this.binding.swipeEt.setError(MultiModeSettingActivity.this.getResources().getString(R.string.ac62));
                }
            }
        });

        this.binding.intervalType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MultiModeSettingActivity.this.intervalTypeOnClick(view);
            }
        });

        this.binding.down.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MultiModeSettingActivity.this.downOnClick(view);
            }
        });
        setIntervalType(this.intervalType);

        this.binding.popupRl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                MultiModeSettingActivity.this.popupRlOnClick(view);
            }
        });

        this.binding.msTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MultiModeSettingActivity.this.msTvOnClick(view);
            }
        });

        this.binding.sTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MultiModeSettingActivity.this.sTvOnClick(view);
            }
        });

        this.binding.mTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MultiModeSettingActivity.this.mTvOnClick(view);
            }
        });
        this.binding.reset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                MultiModeSettingActivity.this.resetOnClick(view);
            }
        });

        this.binding.save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MultiModeSettingActivity.this.saveOnClick(view);
            }
        });

        this.binding.back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                MultiModeSettingActivity.this.backOnClick(view);
            }
        });
    }

    public void intervalTypeOnClick(View view) {
        this.binding.popupRl.setVisibility(VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void downOnClick(View view) {
        this.binding.popupRl.setVisibility(VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void popupRlOnClick(View view) {
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
                        this.binding.intervalEt.setError(getResources().getString(R.string.ac63));
                    }
                } else if (parseInt != 0) {
                    this.intervalValue = Integer.parseInt(this.binding.intervalEt.getText().toString());
                } else {
                    this.binding.intervalEt.setError(getResources().getString(R.string.ac63));
                }
            } else {
                this.binding.intervalEt.setError(getResources().getString(R.string.ac63));
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
    public void resetOnClick(View view) {
        SpManager.setMultiIntervalType(0);
        SpManager.setMultiIntervalCount(100);
        SpManager.setMultiSwipeCount(500);
        setData();
    }

    /* access modifiers changed from: package-private */
    public void saveOnClick(View view) {
        if (checkIntervalCount() && checkSwipeCount()) {
            openSaveDialog();
        }
    }

    /* access modifiers changed from: package-private */
    public void backOnClick(View view) {
        onBackPressed();
    }

    private void openSaveDialog() {
        SaveDialogBinding inflate = SaveDialogBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        inflate.cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveDialogOnClick(dialog, view);
            }
        });
        inflate.ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveDialogOnClick(dialog, view);
            }
        });
        dialog.show();
    }

    public void saveDialogOnClick(Dialog dialog, View view) {
        SpManager.setMultiIntervalType(this.intervalType);
        SpManager.setMultiIntervalCount(this.intervalValue);
        SpManager.setMultiSwipeCount(this.swipeCount);
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

    public boolean checkIntervalCount() {
        if (this.binding.intervalEt.getText().length() > 0) {
            int parseInt = Integer.parseInt(this.binding.intervalEt.getText().toString());
            if (this.intervalType == 0) {
                if (parseInt >= 40) {
                    return true;
                }
                this.binding.intervalEt.setError(getResources().getString(R.string.ac63));
                return false;
            } else if (parseInt != 0) {
                this.intervalValue = Integer.parseInt(this.binding.intervalEt.getText().toString());
                return true;
            } else {
                this.binding.intervalEt.setError(getResources().getString(R.string.ac63));
                return false;
            }
        } else {
            this.binding.intervalEt.setError(getResources().getString(R.string.ac63));
            return false;
        }
    }

    public boolean checkSwipeCount() {
        if (this.binding.swipeEt.getText().length() <= 0) {
            this.binding.swipeEt.setError(getResources().getString(R.string.ac62));
            return false;
        } else if (Integer.parseInt(this.binding.swipeEt.getText().toString()) >= 300) {
            return true;
        } else {
            this.binding.swipeEt.setError(getResources().getString(R.string.ac62));
            return false;
        }
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
