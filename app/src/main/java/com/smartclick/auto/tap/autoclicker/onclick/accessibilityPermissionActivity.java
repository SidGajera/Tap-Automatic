package com.smartclick.auto.tap.autoclicker.onclick;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.activity.PermissionActivity;

public final class accessibilityPermissionActivity implements View.OnClickListener {
    public final PermissionActivity f$0;

    public accessibilityPermissionActivity(PermissionActivity permissionActivity) {
        this.f$0 = permissionActivity;

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(permissionActivity);
        Bundle eventBundle = new Bundle();
        eventBundle.putString("Accessibility_permission", "Accessibility Permission");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("Accessibility_permission_click", eventBundle);
    }

    public void onClick(View view) {
        this.f$0.accessibilityOnClick(view);
    }
}
