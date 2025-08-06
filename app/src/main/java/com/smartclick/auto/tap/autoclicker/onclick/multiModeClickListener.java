package com.smartclick.auto.tap.autoclicker.onclick;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.activity.StartActivity;

public final class multiModeClickListener implements View.OnClickListener {
    public final StartActivity f$0;

    public multiModeClickListener(StartActivity startActivity) {
        this.f$0 = startActivity;

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(startActivity);
        Bundle eventBundle = new Bundle();
        eventBundle.putString("Multi_target_mode", "Multi Target Mode");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("Multi_target_mode", eventBundle);
    }

    public void onClick(View view) {
        this.f$0.multiTargetModeSwitch(view);
    }
}
