package com.smartclick.auto.tap.autoclicker.onclick;

import com.smartclick.auto.tap.autoclicker.activity.StartActivity;

public final class starSubmitOnClick implements Runnable {
    public final StartActivity f$0;

    public starSubmitOnClick(StartActivity startActivity) {
        this.f$0 = startActivity;
    }

    public final void run() {
        this.f$0.starSubmitRun();
    }
}
