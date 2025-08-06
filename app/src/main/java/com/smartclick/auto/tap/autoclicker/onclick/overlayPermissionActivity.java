package com.smartclick.auto.tap.autoclicker.onclick;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.activity.PermissionActivity;

public final class overlayPermissionActivity implements View.OnClickListener {
    public final PermissionActivity f$0;

    public overlayPermissionActivity(PermissionActivity permissionActivity) {
        this.f$0 = permissionActivity;


        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(permissionActivity);
        Bundle eventBundle = new Bundle();
        eventBundle.putString("Overlay_permission", "Overlay Permission");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("Overlay_permission_click", eventBundle);
    }

    public final void onClick(View view) {
        this.f$0.overlayOnCLick(view);
    }
}
