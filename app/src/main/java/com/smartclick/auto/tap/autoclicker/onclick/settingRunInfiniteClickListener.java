package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.service.SingleModeService;

public final class settingRunInfiniteClickListener implements View.OnClickListener {
    public final SingleModeService f$0;

    public settingRunInfiniteClickListener(SingleModeService singleModeService) {
        this.f$0 = singleModeService;
    }

    public final void onClick(View view) {
        this.f$0.settingRunInfiniteOnClick(view);
    }
}
