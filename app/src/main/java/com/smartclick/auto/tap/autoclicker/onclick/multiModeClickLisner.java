package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.service.MultiModeService;

public final class multiModeClickLisner implements View.OnClickListener {
    public final MultiModeService f$0;

    public multiModeClickLisner(MultiModeService multiModeService) {
        this.f$0 = multiModeService;
    }

    public final void onClick(View view) {
        this.f$0.multiModeOnClick(view);
    }
}
