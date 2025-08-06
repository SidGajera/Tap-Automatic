package com.smartclick.auto.tap.autoclicker.onclick;

import android.app.Dialog;
import android.view.View;

import com.smartclick.auto.tap.autoclicker.activity.StartActivity;

public final class starCancelClickListener implements View.OnClickListener {
    public final StartActivity f$0;
    public final Dialog f$1;

    public starCancelClickListener(StartActivity startActivity, Dialog dialog) {
        this.f$0 = startActivity;
        this.f$1 = dialog;
    }

    public final void onClick(View view) {
        this.f$0.starCancelOnClick(this.f$1, view);
    }
}
