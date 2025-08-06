package com.smartclick.auto.tap.autoclicker.notification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import com.smartclick.auto.tap.autoclicker.MyApplication;
import com.smartclick.auto.tap.autoclicker.R;

public class WebActivity extends AppCompatActivity {
    public ProgressDialog f186pd;
    String link;
    SharedPreferences.Editor myEdit;
    RelativeLayout rl_loadingscreen;
    SharedPreferences sharedPreferences;
    String type;
    public WebView webView;

    @Override // androidx.fragment.app.FragmentActivity
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web);
        Log.e("getmetype", "web ");
        getWindow().addFlags(128);
        Log.e("getmetype", "next ");
        Intent intent = getIntent();
        if (intent != null) {
            this.link = intent.getStringExtra("link");
            this.type = getIntent().getStringExtra("type");
        }
        WebView webView2 = (WebView) findViewById(R.id.webView1);
        this.webView = webView2;
        webView2.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(true);
        RelativeLayout relativeLayout = findViewById(R.id.rl_loadingscreen);
        this.rl_loadingscreen = relativeLayout;
        relativeLayout.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("bdcPref", 0);
        this.sharedPreferences = sharedPreferences2;
        this.myEdit = sharedPreferences2.edit();
        if (!this.sharedPreferences.getBoolean("hasMatch", false) || !this.type.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_3D)) {
            next();
        } else {
            next();
        }
    }

    /* access modifiers changed from: package-private */
    public void next() {
        showads();
    }

    public void showads() {
        onPreExecute();
        new Handler().postDelayed(new Runnable() {

            public void run() {
                WebActivity.this.f186pd.hide();
                WebActivity.this.adsss();
            }
        }, 3000);
    }

    private void loadwebview(String str) {
        this.rl_loadingscreen.setVisibility(View.GONE);
        this.webView.setWebViewClient(new WebViewClient() {
             public ProgressDialog f6929a;

            public void onLoadResource(WebView webView, String str) {
                if (this.f6929a == null) {
                    ProgressDialog progressDialog = new ProgressDialog(WebActivity.this);
                    this.f6929a = progressDialog;
                    progressDialog.setMessage("Loading...");
                    this.f6929a.setCancelable(true);
                    this.f6929a.show();
                }
            }

            public void onPageFinished(WebView webView, String str) {
                this.f6929a.dismiss();
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(str);
    }

    public void onPreExecute() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.f186pd = progressDialog;
        progressDialog.setTitle("Please Wait");
        this.f186pd.setMessage("Please wait...");
        this.f186pd.setCancelable(false);
    }

    public void adsss() {
        if (this.sharedPreferences.getString("colorfulldayfulls_show_atnotific", "0").equalsIgnoreCase("1")) {
            loadwebview(this.link);
        } else {
            loadwebview(this.link);
        }
        Log.d("TAG", "The interstitial ad wasn't ready yet.");
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        if (this.sharedPreferences.getString("colorfulldayfulls_show_onexitnotifi", "0").equalsIgnoreCase("1")) {
            finishAffinity();
        } else if (this.sharedPreferences.getString("colorfullday_show_exitinnotifi", "0").equalsIgnoreCase("1")) {
            finishAffinity();
        } else {
            finishAffinity();
        }
    }
}
