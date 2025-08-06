package com.smartclick.auto.tap.autoclicker.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.interfaces.UpdateXY;
import com.smartclick.auto.tap.autoclicker.service.MultiModeService;

public class MultiModeTargetTwoView extends RelativeLayout {
    float clickOneX;
    float clickOneY;
    float clickTwoX;
    float clickTwoY;
    WindowManager.LayoutParams layoutParams1;
    WindowManager.LayoutParams layoutParams2;
    CustomView multiModeTargetView1;
    CustomView multiModeTargetView2;
    private WindowManager windowManager;

    public MultiModeTargetTwoView(Context context, WindowManager windowManager2, final int i, int i2) {
        super(context);
        this.windowManager = windowManager2;
        LayoutInflater.from(context).inflate(R.layout.multi_mode_target_two, (ViewGroup) this, true);
        this.clickOneX = MultiModeService.arrayList.get(i).getTypeTwo()[0];
        this.clickOneY = MultiModeService.arrayList.get(i).getTypeTwo()[1];
        this.clickTwoX = MultiModeService.arrayList.get(i).getTypeTwo()[2];
        this.clickTwoY = MultiModeService.arrayList.get(i).getTypeTwo()[3];
        if (Build.VERSION.SDK_INT >= 26) {
            this.layoutParams1 = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
        } else {
            this.layoutParams1 = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
        }
        if (i2 == 0) {
            this.layoutParams1.gravity = Gravity.CENTER;
            this.layoutParams1.x = (int) this.clickOneX;
            this.layoutParams1.y = (int) this.clickOneY;
        } else {
            this.layoutParams1.gravity = Gravity.TOP | Gravity.START;
            this.layoutParams1.x = ((int) this.clickOneX) - 67;
            this.layoutParams1.y = ((int) this.clickOneY) - 120;
        }
        CustomView customView = new CustomView(context, windowManager2, this.layoutParams1, new UpdateXY() {

            @Override
            public void updateXYData(float f, float f2) {
                MultiModeTargetTwoView.this.clickOneX = f;
                MultiModeTargetTwoView.this.clickOneY = f2;
                MultiModeService.arrayList.get(i).setTypeTwo(new float[]{MultiModeTargetTwoView.this.clickOneX, MultiModeTargetTwoView.this.clickOneY, MultiModeTargetTwoView.this.clickTwoX, MultiModeTargetTwoView.this.clickTwoY});
            }
        });
        this.multiModeTargetView1 = customView;
        windowManager2.addView(customView, this.layoutParams1);
        int i3 = i + 1;
        ((TextView) this.multiModeTargetView1.findViewById(R.id.target)).setText(String.valueOf(i3));
        if (Build.VERSION.SDK_INT >= 26) {
            this.layoutParams2 = new WindowManager.LayoutParams(-2, -2, 2038, 8, -3);
        } else {
            this.layoutParams2 = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
        }
        if (i2 == 0) {
            this.layoutParams2.gravity = Gravity.CENTER;
            this.layoutParams2.x = (int) this.clickTwoX;
            this.layoutParams2.y = (int) this.clickTwoY;
        } else {
            this.layoutParams2.gravity = Gravity.TOP | Gravity.START;
            this.layoutParams2.x = ((int) this.clickTwoX) - 67;
            this.layoutParams2.y = ((int) this.clickTwoY) - 120;
        }
        CustomView customView2 = new CustomView(context, windowManager2, this.layoutParams2, new UpdateXY() {

            @Override
            public void updateXYData(float f, float f2) {
                MultiModeTargetTwoView.this.clickTwoX = f;
                MultiModeTargetTwoView.this.clickTwoY = f2;
                MultiModeService.arrayList.get(i).setTypeTwo(new float[]{MultiModeTargetTwoView.this.clickOneX, MultiModeTargetTwoView.this.clickOneY, MultiModeTargetTwoView.this.clickTwoX, MultiModeTargetTwoView.this.clickTwoY});
            }
        });
        this.multiModeTargetView2 = customView2;
        windowManager2.addView(customView2, this.layoutParams2);
        TextView textView = (TextView) this.multiModeTargetView2.findViewById(R.id.target);
        textView.setBackground(getResources().getDrawable(R.drawable.sqaure_target));
        textView.setTextColor(Color.parseColor("#C4FFFFFF"));
        textView.setText(String.valueOf(i3));
    }

    private void before(View view) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        this.windowManager.updateViewLayout(view, layoutParams);
    }

    private void after(View view) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        this.windowManager.updateViewLayout(view, layoutParams);
    }

    public void remove() {
        this.windowManager.removeView(this.multiModeTargetView1);
        this.windowManager.removeView(this.multiModeTargetView2);
    }

    public void beforeCall() {
        before(this.multiModeTargetView1);
        before(this.multiModeTargetView2);
    }

    public void afterCall() {
        after(this.multiModeTargetView1);
        after(this.multiModeTargetView2);
    }

    public static class CustomView extends RelativeLayout {
        float clickX;
        float clickY;
        float f189x;
        float f190y;
        UpdateXY updateXY;
        float viewOneX;
        float viewOneY;

        public CustomView(Context context, final WindowManager windowManager, final WindowManager.LayoutParams layoutParams, final UpdateXY updateXY2) {
            super(context);
            this.updateXY = updateXY2;
            LayoutInflater.from(context).inflate(R.layout.multi_mode_target, (ViewGroup) this, true);
            setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (MultiModeService.isPlaying) {
                        return false;
                    }
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        CustomView.this.viewOneX = (float) layoutParams.x;
                        CustomView.this.viewOneY = (float) layoutParams.y;
                        CustomView.this.f189x = motionEvent.getRawX();
                        CustomView.this.f190y = motionEvent.getRawY();
                        return true;
                    } else if (action == 1) {
                        CustomView.this.clickX = motionEvent.getRawX();
                        CustomView.this.clickY = motionEvent.getRawY();
                        updateXY2.updateXYData(CustomView.this.clickX, CustomView.this.clickY);
                        return true;
                    } else if (action != 2) {
                        return true;
                    } else {
                        layoutParams.x = (int) ((CustomView.this.viewOneX + motionEvent.getRawX()) - CustomView.this.f189x);
                        layoutParams.y = (int) ((CustomView.this.viewOneY + motionEvent.getRawY()) - CustomView.this.f190y);
                        windowManager.updateViewLayout(CustomView.this, layoutParams);
                        updateXY2.updateXYData(motionEvent.getRawX(), motionEvent.getRawY());
                        return true;
                    }
                }
            });
        }
    }
}
