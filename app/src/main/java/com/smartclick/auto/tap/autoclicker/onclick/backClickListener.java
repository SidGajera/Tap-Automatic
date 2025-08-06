package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.activity.SingleModeInstructionActivity;

public final class backClickListener implements View.OnClickListener {
    public final SingleModeInstructionActivity f$0;

    public backClickListener(SingleModeInstructionActivity singleModeInstructionActivity) {
        this.f$0 = singleModeInstructionActivity;
    }

    public final void onClick(View view) {
        this.f$0.backOnClick(view);
    }
}
