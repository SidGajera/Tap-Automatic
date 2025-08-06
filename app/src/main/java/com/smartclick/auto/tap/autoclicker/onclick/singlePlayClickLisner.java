package com.smartclick.auto.tap.autoclicker.onclick;

import android.util.Log;

import com.smartclick.auto.tap.autoclicker.service.SingleModeService;

public final class singlePlayClickLisner implements Runnable {
    public final SingleModeService f$0;
    String TAG = singlePlayClickLisner.class.getName();
    public singlePlayClickLisner(SingleModeService singleModeService) {
        this.f$0 = singleModeService;
    }

    public void run() {
        try{
            this.f$0.singlePlayRun();
        } catch (Exception e) {
            Log.e(TAG, "run: e = "+e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
