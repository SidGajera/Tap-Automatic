package com.smartclick.auto.tap.autoclicker.onclick;

import android.app.Dialog;
import android.view.View;

public final class twoPermissionActivity implements View.OnClickListener {
    public final Dialog f$0;

    public twoPermissionActivity(Dialog dialog) {
        this.f$0 = dialog;
    }

    public void onClick(View view) {
        this.f$0.dismiss();
    }
}
