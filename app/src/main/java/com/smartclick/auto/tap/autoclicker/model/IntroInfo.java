package com.smartclick.auto.tap.autoclicker.model;

public class IntroInfo {
    private final int infoHeader;
    private final int infoSrc;
    private final int infoTxt;

    public IntroInfo(int i, int i2, int i3) {
        this.infoSrc = i;
        this.infoHeader = i2;
        this.infoTxt = i3;
    }

    public int getInfoSrc() {
        return this.infoSrc;
    }

    public int getInfoHeader() {
        return this.infoHeader;
    }

    public int getInfoTxt() {
        return this.infoTxt;
    }
}
