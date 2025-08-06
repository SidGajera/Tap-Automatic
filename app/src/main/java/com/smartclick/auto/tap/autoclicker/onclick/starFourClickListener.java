package com.smartclick.auto.tap.autoclicker.onclick;

import android.view.View;

import com.smartclick.auto.tap.autoclicker.activity.StartActivity;
import com.smartclick.auto.tap.autoclicker.databinding.RateDialogBinding;

public final class starFourClickListener implements View.OnClickListener {
    public final StartActivity f$0;
    public final RateDialogBinding f$1;

    public starFourClickListener(StartActivity startActivity, RateDialogBinding rateDialogBinding) {
        this.f$0 = startActivity;
        this.f$1 = rateDialogBinding;
    }

    public final void onClick(View view) {
        this.f$0.starFourOnClick(this.f$1, view);
    }
}
