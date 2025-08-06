package com.smartclick.auto.tap.autoclicker.onclick;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.activity.PermissionActivity;

public final class guideDialogPermissionActivity implements View.OnClickListener {
    public final PermissionActivity f$0;
    public final Dialog f$1;
    private FirebaseAnalytics firebaseAnalytics;

    public guideDialogPermissionActivity(PermissionActivity permissionActivity, Dialog dialog) {
        this.f$0 = permissionActivity;
        this.f$1 = dialog;

        firebaseAnalytics = FirebaseAnalytics.getInstance(permissionActivity);
        Bundle eventBundle = new Bundle();
        eventBundle.putString("Guide_permission", "Guide Permission");
        eventBundle.putString("action", "clicked");
        firebaseAnalytics.logEvent("Guide_permission_click", eventBundle);
    }

    public void onClick(View view) {
        this.f$0.guideDialogOnClick(this.f$1, view);
    }
}
