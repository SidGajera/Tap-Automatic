package com.smartclick.auto.tap.autoclicker.model;

import com.smartclick.auto.tap.autoclicker.utils.MultiModeTargetTwoView;
import com.smartclick.auto.tap.autoclicker.utils.MultiModeTargetView;

public class MultiModel {
    public MultiModeTargetTwoView multiModeTargetTwoView;
    public MultiModeTargetView multiModeTargetView;
    public int type;
    public float[] typeOne;
    public float[] typeTwo;

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public float[] getTypeOne() {
        return this.typeOne;
    }

    public void setTypeOne(float[] fArr) {
        this.typeOne = fArr;
    }

    public float[] getTypeTwo() {
        return this.typeTwo;
    }

    public void setTypeTwo(float[] fArr) {
        this.typeTwo = fArr;
    }

    public MultiModeTargetView getMultiModeTargetView() {
        return this.multiModeTargetView;
    }

    public void setMultiModeTargetView(MultiModeTargetView multiModeTargetView2) {
        this.multiModeTargetView = multiModeTargetView2;
    }

    public MultiModeTargetTwoView getMultiModeTargetTwoView() {
        return this.multiModeTargetTwoView;
    }

    public void setMultiModeTargetTwoView(MultiModeTargetTwoView multiModeTargetTwoView2) {
        this.multiModeTargetTwoView = multiModeTargetTwoView2;
    }
}
