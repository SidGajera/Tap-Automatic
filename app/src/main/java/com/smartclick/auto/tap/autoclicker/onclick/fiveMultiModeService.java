package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.service.MultiModeService;

public final class fiveMultiModeService implements View.OnClickListener {
    public final MultiModeService f$0;

    public fiveMultiModeService(MultiModeService main6multimodeservice) {
        this.f$0 = main6multimodeservice;
    }

    public final void onClick(View view) {
        this.f$0.settingCancelClickListener(view);
    }
}
