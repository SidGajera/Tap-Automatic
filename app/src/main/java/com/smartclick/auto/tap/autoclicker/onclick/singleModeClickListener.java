package com.smartclick.auto.tap.autoclicker.onclick;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.activity.StartActivity;

public final class singleModeClickListener implements View.OnClickListener {
    public final StartActivity f$0;

    public singleModeClickListener(StartActivity startActivity) {
        this.f$0 = startActivity;

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(startActivity);
        Bundle eventBundle = new Bundle();
        eventBundle.putString("Single_target_mode", "Single Target Mode");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("Single_target_mode_click", eventBundle);
    }

    public final void onClick(View view) {
        this.f$0.singleTargetModeSwitch(view);
    }
}
