package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.activity.PermissionActivity;
import com.smartclick.auto.tap.autoclicker.databinding.AsseccDialogLayoutBinding;

public final class onePermissionActivity implements View.OnClickListener {
    public final PermissionActivity f$0;
    public final AsseccDialogLayoutBinding f$1;

    public onePermissionActivity(PermissionActivity permissionActivity, AsseccDialogLayoutBinding asseccDialogLayoutBinding) {
        this.f$0 = permissionActivity;
        this.f$1 = asseccDialogLayoutBinding;
    }

    public final void onClick(View view) {
        this.f$0.acceptDialogOnClick(this.f$1, view);
    }
}
