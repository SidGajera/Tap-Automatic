package com.smartclick.auto.tap.autoclicker.onclick;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.activity.PermissionActivity;

public final class startButtonActivity implements View.OnClickListener {
    public final PermissionActivity f$0;

    public startButtonActivity(PermissionActivity permissionActivity) {
        this.f$0 = permissionActivity;

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(permissionActivity);
        Bundle eventBundle = new Bundle();
        eventBundle.putString("Start_app", "Start App");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("Start_app_click", eventBundle);
    }

    public void onClick(View view) {
        this.f$0.startButtonOnClick(view);
    }
}
