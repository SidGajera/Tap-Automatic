package com.smartclick.auto.tap.autoclicker.model;

import java.util.List;

public class MultiDbModel {
    public int hour;
    public int intervalCount;
    public int intervalType;
    public int minute;

    public String name;
    public int nocCount;
    public int second;
    public int stopType;
    public int swipeCount;
    public List<MultiModelTwo> targets;

    public MultiDbModel() {
    }

    public MultiDbModel(String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, List<MultiModelTwo> list) {
        this.name = str;
        this.intervalType = i;
        this.intervalCount = i2;
        this.swipeCount = i3;
        this.stopType = i4;
        this.hour = i5;
        this.minute = i6;
        this.second = i7;
        this.nocCount = i8;
        this.targets = list;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getIntervalType() {
        return this.intervalType;
    }

    public void setIntervalType(int i) {
        this.intervalType = i;
    }

    public int getIntervalCount() {
        return this.intervalCount;
    }

    public void setIntervalCount(int i) {
        this.intervalCount = i;
    }

    public int getSwipeCount() {
        return this.swipeCount;
    }

    public void setSwipeCount(int i) {
        this.swipeCount = i;
    }

    public int getStopType() {
        return this.stopType;
    }

    public void setStopType(int i) {
        this.stopType = i;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int i) {
        this.hour = i;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int i) {
        this.minute = i;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int i) {
        this.second = i;
    }

    public int getNocCount() {
        return this.nocCount;
    }

    public void setNocCount(int i) {
        this.nocCount = i;
    }

    public List<MultiModelTwo> getArrayList() {
        return this.targets;
    }

    public void setArrayList(List<MultiModelTwo> list) {
        this.targets = list;
    }
}
