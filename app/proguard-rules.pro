# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile




#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule {
# <init>(...);
#}
#
#-keep class com.example.app.json.** { *; }
#

#
#
#-keep class com.google.gson.examples.android.model.** { <fields>; }
#
#
#-keep class com.smartclick.auto.tap.autoclicker.activity.ConfigurationActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.onclick.fivePermissionActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.GuideActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.LanguageActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.onclick.overlayPermissionActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.MultiModeInstructionActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.MultiModeSettingActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.onclick.onePermissionActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.PermissionActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.PrivacyPolicyActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.SingleModeInstructionActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.onclick.SingleModeInstructionActivityone.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.PermissionActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.activity.SingleModeSettingActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.onclick.twoPermissionActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.onclick.startButtonActivity.*
#-keep class com.smartclick.auto.tap.autoclicker.onclick.agreeDialogPermissionActivity.*
#
#
#
#-keep class com.smartclick.auto.tap.autoclicker.adapter.ConfigurationAdapter.*
#-keep class com.smartclick.auto.tap.autoclicker.adapter.IntroAdapter.*
#-keep class com.smartclick.auto.tap.autoclicker.adapter.LanguageAdapter.*
#-keep class com.smartclick.auto.tap.autoclicker.adapter.SaveListDialogAdapter.*
#
#-keep class com.smartclick.auto.tap.autoclicker.service.AutoClicker.*
#-keep class com.smartclick.auto.tap.autoclicker.onclick.SingleModeServic10.*
#
#


#-keep class com.smartclick.auto.tap.autoclicker.R$raw { *; }


# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.OpenSSLProvider

# Gson uses reflection to access fields
-keep class com.smartclick.auto.tap.autoclicker.model.** { *; }

# Keep default constructors
-keepclassmembers class * {
    public <init>();
}

# Keep Gson annotations
-keepattributes Signature
-keepattributes *Annotation*

# Avoid stripping generic type info used by Gson
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}


-dontwarn com.google.android.gms.measurement.internal.zzib
-dontwarn com.google.android.gms.measurement.internal.zzir
-dontwarn com.google.android.gms.measurement.internal.zziu
-dontwarn com.google.android.gms.measurement.internal.zziv
-dontwarn com.google.android.gms.measurement.internal.zzjc
-dontwarn com.google.android.gms.measurement.internal.zzje
-dontwarn com.google.android.gms.measurement.internal.zzjj