package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.activity.StartActivity;

public final class languageRlClickListener implements View.OnClickListener {
    public final StartActivity f$0;

    public languageRlClickListener(StartActivity startActivity) {
        this.f$0 = startActivity;
    }

    public final void onClick(View view) {
        this.f$0.languageRlOnClick(view);
    }
}
