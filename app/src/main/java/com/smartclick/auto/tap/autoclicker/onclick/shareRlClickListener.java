package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.activity.StartActivity;

public final class shareRlClickListener implements View.OnClickListener {
    public final StartActivity f$0;

    public shareRlClickListener(StartActivity startActivity) {
        this.f$0 = startActivity;
    }

    public void onClick(View view) {
        this.f$0.shareRlOnClick(view);
    }
}
