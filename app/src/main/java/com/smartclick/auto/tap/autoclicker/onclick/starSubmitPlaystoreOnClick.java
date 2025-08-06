package com.smartclick.auto.tap.autoclicker.onclick;

import com.smartclick.auto.tap.autoclicker.activity.StartActivity;

public final class starSubmitPlaystoreOnClick implements Runnable {
    public final StartActivity f$0;

    public starSubmitPlaystoreOnClick(StartActivity startActivity) {
        this.f$0 = startActivity;
    }

    public void run() {
        this.f$0.starSubmitPlayStoreRun();
    }
}
