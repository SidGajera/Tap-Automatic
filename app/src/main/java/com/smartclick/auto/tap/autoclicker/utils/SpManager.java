package com.smartclick.auto.tap.autoclicker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class SpManager {
    public static String CONNECT_TYPE = "connect_type";
    public static String INTERVAL_COUNT = "intervalCount";
    public static String INTERVAL_TYPE = "intervalType";
    public static String IS_FIRST_TIME = "is_first_time";
    public static String IS_INDIAN = "is_indian";
    public static String LANGUAGE_CODE = "language_code";
    public static String LANGUAGE_CODE_SNIP = "language_code_snip";
    public static String LANGUAGE_SELECTED = "language_selected";
    public static String MULTI_INTERVAL_COUNT = "multiIntervalCount";
    public static String MULTI_INTERVAL_TYPE = "multiIntervalType";
    public static String MULTI_SWIPE_COUNT = "multiSwipeCount";
    public static String NUMBER_OF_COUNT = "numberOfCount";
    public static String RUN_TIMER_HOUR = "runTimerHour";
    public static String RUN_TIMER_MINUTE = "runTimerMinute";
    public static String RUN_TIMER_SECOND = "runTimerSecond";
    public static String Rate_Which = "Rate_Which";
    public static String STOP_TYPE = "stopType";
    public static String isSRatePopupMC = "isSRatePopupMC";
    static SharedPreferences.Editor myEdit;
    static SharedPreferences sharedPreferences;

    public static String SPLASH_EVENT_COUNT = "splashVisitCount";
    public static String LANGUAGE_EVENT_COUNT = "languageVisitCount";
    public static String INTRO_EVENT_COUNT = "introVisitCount";
    public static String PERMISSION_EVENT_COUNT = "permissionVisitCount";
    public static String HOME_EVENT_COUNT = "homeVisitCount";
    public static String SINGLE_TARGET_EVENT_COUNT = "singleTargetVisitCount";
    public static String SINGLE_INSTRUCTION_EVENT_COUNT = "singleInstructionVisitCount";
    public static String MULTI_TARGET_EVENT_COUNT = "multiTargetVisitCount";
    public static String MULTI_CONFIGURATION_EVENT_COUNT = "multiConfigurationVisitCount";
    public static String MULTI_INSTRUCTION_EVENT_COUNT = "multiInstructionVisitCount";
    public static String GUIDE_EVENT_COUNT = "guideVisitCount";

    public static FirebaseAnalytics firebaseAnalytics;

    public static Bundle eventBundle;

    public static void initializingSharedPreference(Context context) {
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("MySharedPref123", 0);
        sharedPreferences = sharedPreferences2;
        myEdit = sharedPreferences2.edit();
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        eventBundle = new Bundle();
    }

    public static boolean isIndian() {
        return sharedPreferences.getBoolean(IS_INDIAN, true);
    }

    public static void setIndian(boolean z) {
        sharedPreferences.edit().putBoolean(IS_INDIAN, z).apply();
    }

    public static boolean getLanguageSelected() {
        return sharedPreferences.getBoolean(LANGUAGE_SELECTED, false);
    }

    public static void setLanguageSelected(boolean z) {
        sharedPreferences.edit().putBoolean(LANGUAGE_SELECTED, z).apply();
    }

    public static String getLanguageCode() {
        return sharedPreferences.getString(LANGUAGE_CODE, "en");
    }

    public static void setLanguageCode(String str) {
        sharedPreferences.edit().putString(LANGUAGE_CODE, str).apply();
    }

    public static String getLanguageCodeSnip() {
        return sharedPreferences.getString(LANGUAGE_CODE_SNIP, "en");
    }

    public static void setLanguageCodeSnip(String str) {
        sharedPreferences.edit().putString(LANGUAGE_CODE_SNIP, str).apply();
    }

    public static boolean getIsFirstTime() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME, true);
    }

    public static void setIsFirstTime(boolean z) {
        sharedPreferences.edit().putBoolean(IS_FIRST_TIME, z).apply();
    }

    public static String getConnectType() {
        return sharedPreferences.getString(CONNECT_TYPE, "A2DP");
    }

    public static void setConnectType(String str) {
        sharedPreferences.edit().putString(CONNECT_TYPE, str).apply();
    }

    public static void setIsRatePopupMC(int i) {
        sharedPreferences.edit().putInt(isSRatePopupMC, i).apply();
    }

    public static int getIsRatePopupMC() {
        return sharedPreferences.getInt(isSRatePopupMC, 0);
    }

    public static void setRate_Which(int i) {
        sharedPreferences.edit().putInt(Rate_Which, i).apply();
    }

    public static int getRate_Which() {
        return sharedPreferences.getInt(Rate_Which, 0);
    }

    public static void setIntervalType(int i) {
        sharedPreferences.edit().putInt(INTERVAL_TYPE, i).apply();
    }

    public static int getIntervalType() {
        return sharedPreferences.getInt(INTERVAL_TYPE, 0);
    }

    public static void setStopType(int i) {
        sharedPreferences.edit().putInt(STOP_TYPE, i).apply();
    }

    public static int getStopType() {
        return sharedPreferences.getInt(STOP_TYPE, 2);
    }

    public static void setNumberOfCount(int i) {
        sharedPreferences.edit().putInt(NUMBER_OF_COUNT, i).apply();
    }

    public static int getNumberOfCount() {
        return sharedPreferences.getInt(NUMBER_OF_COUNT, 10);
    }

    public static void setIntervalCount(int i) {
        sharedPreferences.edit().putInt(INTERVAL_COUNT, i).apply();
    }

    public static int getIntervalCount() {
        return sharedPreferences.getInt(INTERVAL_COUNT, 1000);
    }

    public static void setRunTimerHour(int i) {
        sharedPreferences.edit().putInt(RUN_TIMER_HOUR, i).apply();
    }

    public static int getRunTimerHour() {
        return sharedPreferences.getInt(RUN_TIMER_HOUR, 0);
    }

    public static void setRunTimerMinute(int i) {
        sharedPreferences.edit().putInt(RUN_TIMER_MINUTE, i).apply();
    }

    public static int getRunTimerMinute() {
        return sharedPreferences.getInt(RUN_TIMER_MINUTE, 5);
    }

    public static void setRunTimerSecond(int i) {
        sharedPreferences.edit().putInt(RUN_TIMER_SECOND, i).apply();
    }

    public static int getRunTimerSecond() {
        return sharedPreferences.getInt(RUN_TIMER_SECOND, 0);
    }

    public static void setMultiIntervalType(int i) {
        sharedPreferences.edit().putInt(MULTI_INTERVAL_TYPE, i).apply();
    }

    public static int getMultiIntervalType() {
        return sharedPreferences.getInt(MULTI_INTERVAL_TYPE, 0);
    }

    public static void setMultiIntervalCount(int i) {
        sharedPreferences.edit().putInt(MULTI_INTERVAL_COUNT, i).apply();
    }

    public static int getMultiIntervalCount() {
        return sharedPreferences.getInt(MULTI_INTERVAL_COUNT, 1000);
    }

    public static void setMultiSwipeCount(int i) {
        sharedPreferences.edit().putInt(MULTI_SWIPE_COUNT, i).apply();
    }

    public static int getMultiSwipeCount() {
        return sharedPreferences.getInt(MULTI_SWIPE_COUNT, 500);
    }

    public static int getSplashEventCount() {
        return sharedPreferences.getInt(SPLASH_EVENT_COUNT, 0);
    }

    public static void setSplashEventCount(int splashEventCount) {
        eventBundle.putString("splash_event_count", splashEventCount+"_impression");
        eventBundle.putString("action", "Splash screen open");
        firebaseAnalytics.logEvent("SPLASH_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(SPLASH_EVENT_COUNT, splashEventCount).apply();
    }

    public static int getLanguageEventCount() {
        return sharedPreferences.getInt(LANGUAGE_EVENT_COUNT, 0);
    }

    public static void setLanguageEventCount(int languageEventCount) {
        eventBundle.putString("language_event_count", languageEventCount+"_impression");
        eventBundle.putString("action", "Language screen open");
        firebaseAnalytics.logEvent("LANGUAGE_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(LANGUAGE_EVENT_COUNT, languageEventCount).apply();
    }

    public static int getIntroEventCount() {
        return sharedPreferences.getInt(INTRO_EVENT_COUNT, 0);
    }

    public static void setIntroEventCount(int introEventCount) {
        eventBundle.putString("intro_event_count", introEventCount+"_impression");
        eventBundle.putString("action", "Intro screen open");
        firebaseAnalytics.logEvent("INTRO_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(INTRO_EVENT_COUNT, introEventCount).apply();
    }

    public static int getPermissionEventCount() {
        return sharedPreferences.getInt(PERMISSION_EVENT_COUNT, 0);
    }

    public static void setPermissionEventCount(int permissionEventCount) {
        eventBundle.putString("permission_event_count", permissionEventCount+"_impression");
        eventBundle.putString("action", "Permission screen open");
        firebaseAnalytics.logEvent("PERMISSION_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(PERMISSION_EVENT_COUNT, permissionEventCount).apply();
    }

    public static int getHomeEventCount() {
        return sharedPreferences.getInt(HOME_EVENT_COUNT, 0);
    }

    public static void setHomeEventCount(int homeEventCount) {
        eventBundle.putString("home_event_count", homeEventCount+"_impression");
        eventBundle.putString("action", "Home screen open");
        firebaseAnalytics.logEvent("HOME_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(HOME_EVENT_COUNT, homeEventCount).apply();
    }

    public static int getSingleTargetEventCount() {
        return sharedPreferences.getInt(SINGLE_TARGET_EVENT_COUNT, 0);
    }

    public static void setSingleTargetEventCount(int singleTargetEventCount) {
        eventBundle.putString("single_event_count", singleTargetEventCount+"_impression");
        eventBundle.putString("action", "Single screen open");
        firebaseAnalytics.logEvent("SINGLE_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(SINGLE_TARGET_EVENT_COUNT, singleTargetEventCount).apply();
    }

    public static int getSingleInstructionEventCount() {
        return sharedPreferences.getInt(SINGLE_INSTRUCTION_EVENT_COUNT, 0);
    }

    public static void setSingleInstructionEventCount(int singleInstructionEventCount) {
        eventBundle.putString("single_instruction_event_count", singleInstructionEventCount+"_impression");
        eventBundle.putString("action", "Single instruction screen open");
        firebaseAnalytics.logEvent("SINGLE_INSTRUCTION_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(SINGLE_INSTRUCTION_EVENT_COUNT, singleInstructionEventCount).apply();
    }

    public static int getMultiTargetEventCount() {
        return sharedPreferences.getInt(MULTI_TARGET_EVENT_COUNT, 0);
    }

    public static void setMultiTargetEventCount(int multiTargetEventCount) {
        eventBundle.putString("multi_target_event_count", multiTargetEventCount+"_impression");
        eventBundle.putString("action", "Multi target screen open");
        firebaseAnalytics.logEvent("MULTI_TARGET_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(MULTI_TARGET_EVENT_COUNT, multiTargetEventCount).apply();
    }

    public static int getMultiConfigurationEventCount() {
        return sharedPreferences.getInt(MULTI_CONFIGURATION_EVENT_COUNT, 0);
    }

    public static void setMultiConfigurationEventCount(int multiConfigurationEventCount) {
        eventBundle.putString("multi_configuration_event_count", multiConfigurationEventCount+"_impression");
        eventBundle.putString("action", "Multi configuration screen open");
        firebaseAnalytics.logEvent("MULTI_CONFIGURATION_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(MULTI_CONFIGURATION_EVENT_COUNT, multiConfigurationEventCount).apply();
    }

    public static int getMultiInstructionEventCount() {
        return sharedPreferences.getInt(MULTI_INSTRUCTION_EVENT_COUNT, 0);
    }

    public static void setMultiInstructionEventCount(int multiInstructionEventCount) {
        eventBundle.putString("multi_instruction_event_count", multiInstructionEventCount+"_impression");
        eventBundle.putString("action", "Multi Instruction screen open");
        firebaseAnalytics.logEvent("MULTI_INSTRUCTION_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(MULTI_INSTRUCTION_EVENT_COUNT, multiInstructionEventCount).apply();
    }

    public static int getGuideEventCount() {
        return sharedPreferences.getInt(GUIDE_EVENT_COUNT, 0);

    }

    public static void setGuideEventCount(int guideEventCount) {
        eventBundle.putString("guide_event_count", guideEventCount+"_impression");
        eventBundle.putString("action", "Guiden screen open");
        firebaseAnalytics.logEvent("GUIDE_IMPRESSION", eventBundle);
        sharedPreferences.edit().putInt(GUIDE_EVENT_COUNT, guideEventCount).apply();
    }
}
