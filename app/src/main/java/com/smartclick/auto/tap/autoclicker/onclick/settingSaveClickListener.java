package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.service.SingleModeService;

public final class settingSaveClickListener implements View.OnClickListener {
    public final SingleModeService f$0;

    public settingSaveClickListener(SingleModeService singleModeService) {
        this.f$0 = singleModeService;
    }

    public final void onClick(View view) {
        this.f$0.settingSaveOnClick(view);
    }
}
