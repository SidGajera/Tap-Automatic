package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.service.SingleModeService;

public final class downClickListener implements View.OnClickListener {
    public final SingleModeService f$0;

    public downClickListener(SingleModeService singleModeService) {
        this.f$0 = singleModeService;
    }

    public void onClick(View view) {
        this.f$0.downOnClick(view);
    }
}
