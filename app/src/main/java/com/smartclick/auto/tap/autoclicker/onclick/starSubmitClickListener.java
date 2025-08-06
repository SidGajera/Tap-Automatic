package com.smartclick.auto.tap.autoclicker.onclick;

import android.app.Dialog;
import android.view.View;

import com.smartclick.auto.tap.autoclicker.activity.StartActivity;

public final class starSubmitClickListener implements View.OnClickListener {
    public final StartActivity f$0;
    public final Dialog f$1;

    public starSubmitClickListener(StartActivity startActivity, Dialog dialog) {
        this.f$0 = startActivity;
        this.f$1 = dialog;
    }

    public void onClick(View view) {
        this.f$0.starSubmitOnClick(this.f$1, view);
    }
}
