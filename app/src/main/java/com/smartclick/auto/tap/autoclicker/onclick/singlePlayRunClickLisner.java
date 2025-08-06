package com.smartclick.auto.tap.autoclicker.onclick;

import com.smartclick.auto.tap.autoclicker.service.SingleModeService;

public final class singlePlayRunClickLisner implements Runnable {
    public final SingleModeService f$0;

    public singlePlayRunClickLisner(SingleModeService singleModeService) {
        this.f$0 = singleModeService;
    }

    public final void run() {
        this.f$0.singlePlayRunClick();
    }
}
