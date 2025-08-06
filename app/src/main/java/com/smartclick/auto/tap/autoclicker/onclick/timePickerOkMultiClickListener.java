package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.service.MultiModeService;

public final class timePickerOkMultiClickListener implements View.OnClickListener {
    public final MultiModeService f$0;

    public timePickerOkMultiClickListener(MultiModeService main6multimodeservice) {
        this.f$0 = main6multimodeservice;
    }

    public final void onClick(View view) {
        this.f$0.timePickerOkMultiOnClick(view);
    }
}
