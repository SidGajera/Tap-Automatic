package com.smartclick.auto.tap.autoclicker.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.service.MultiModeService;

@SuppressLint("ViewConstructor")
public class MultiModeTargetView extends RelativeLayout {
    float clickX;
    float clickY;
    float f191x;
    float f192y;
    float viewOneX;
    float viewOneY;
    private WindowManager windowManager;

    public MultiModeTargetView(Context context, final WindowManager windowManager2, final WindowManager.LayoutParams layoutParams, final int i) {
        super(context);
        this.windowManager = windowManager2;
        LayoutInflater.from(context).inflate(R.layout.multi_mode_target, (ViewGroup) this, true);
        ((TextView) findViewById(R.id.target)).setText(String.valueOf(i + 1));
        setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MultiModeService.isPlaying) {
                    return false;
                }
                int action = motionEvent.getAction();
                if (action == 0) {
                    MultiModeTargetView.this.viewOneX = (float) layoutParams.x;
                    MultiModeTargetView.this.viewOneY = (float) layoutParams.y;
                    MultiModeTargetView.this.f191x = motionEvent.getRawX();
                    MultiModeTargetView.this.f192y = motionEvent.getRawY();
                    return true;
                } else if (action == 1) {
                    MultiModeTargetView.this.clickX = motionEvent.getRawX();
                    MultiModeTargetView.this.clickY = motionEvent.getRawY();
                    Log.d("TAG", "onTouch: ACTION_UP -> " + MultiModeTargetView.this.clickX + " : " + MultiModeTargetView.this.clickY);
                    MultiModeService.arrayList.get(i).setTypeOne(new float[]{MultiModeTargetView.this.clickX, MultiModeTargetView.this.clickY});
                    return true;
                } else if (action != 2) {
                    return true;
                } else {
                    layoutParams.x = (int) ((MultiModeTargetView.this.viewOneX + motionEvent.getRawX()) - MultiModeTargetView.this.f191x);
                    layoutParams.y = (int) ((MultiModeTargetView.this.viewOneY + motionEvent.getRawY()) - MultiModeTargetView.this.f192y);
                    windowManager2.updateViewLayout(MultiModeTargetView.this, layoutParams);
                    return true;
                }
            }
        });
    }

    public void remove() {
        this.windowManager.removeView(this);
    }
}
