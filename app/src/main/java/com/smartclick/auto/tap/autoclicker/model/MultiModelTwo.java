package com.smartclick.auto.tap.autoclicker.model;

public class MultiModelTwo {
    public float clickOneX;
    public float clickOneY;
    public float clickTwoX;
    public float clickTwoY;
    public int type;

    public MultiModelTwo() {
    }

    public MultiModelTwo(int i, float f, float f2, float f3, float f4) {
        this.type = i;
        this.clickOneX = f;
        this.clickOneY = f2;
        this.clickTwoX = f3;
        this.clickTwoY = f4;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public float getClickOneX() {
        return this.clickOneX;
    }

    public void setClickOneX(float f) {
        this.clickOneX = f;
    }

    public float getClickOneY() {
        return this.clickOneY;
    }

    public void setClickOneY(float f) {
        this.clickOneY = f;
    }

    public float getClickTwoX() {
        return this.clickTwoX;
    }

    public void setClickTwoX(float f) {
        this.clickTwoX = f;
    }

    public float getClickTwoY() {
        return this.clickTwoY;
    }

    public void setClickTwoY(float f) {
        this.clickTwoY = f;
    }
}
