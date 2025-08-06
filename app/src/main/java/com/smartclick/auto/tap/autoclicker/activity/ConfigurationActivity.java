package com.smartclick.auto.tap.autoclicker.activity;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.core.motion.utils.TypedValues;

import com.bumptech.glide.load.Key;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.AdAdmob;
import com.smartclick.auto.tap.autoclicker.adapter.ConfigurationAdapter;
import com.smartclick.auto.tap.autoclicker.databinding.ActivityConfigurationBinding;
import com.smartclick.auto.tap.autoclicker.databinding.DeleteDialogBinding;
import com.smartclick.auto.tap.autoclicker.databinding.EditConfigurationDialogBinding;
import com.smartclick.auto.tap.autoclicker.databinding.PcmMoreDialogBinding;
import com.smartclick.auto.tap.autoclicker.model.MultiDbModel;
import com.smartclick.auto.tap.autoclicker.model.MultiModelTwo;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartclick.auto.tap.autoclicker.utils.BaseActivity;
import com.smartclick.auto.tap.autoclicker.utils.JsonWriter;
import com.smartclick.auto.tap.autoclicker.utils.Resizer;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;
import com.smartclick.auto.tap.autoclicker.utils.StorageUtils;

import demo.ads.GoogleAds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigurationActivity extends BaseActivity implements ConfigurationAdapter.ClickButton {
    ActivityConfigurationBinding binding;
    ConfigurationAdapter configurationAdapter;
    List<MultiDbModel> exportList = new ArrayList<>();
    private long mLastClickTime = 0;
    MultiDbModel multiDbModel;
    int pos;
    public static String TAG = ConfigurationActivity.class.getName();

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        public void onActivityResult(ActivityResult activityResult) {
            if (activityResult.getResultCode() == -1) {
                try {
                    File file = new File(Objects.requireNonNull(ConfigurationActivity.getPath(ConfigurationActivity.this, activityResult.getData().getData())));
                    if (file.getName().contains(".json")) {
                        StartActivity.multiDbModelList.addAll(ConfigurationActivity.parseJson(ConfigurationActivity.this.LoadData(file.getAbsolutePath())));
                        ConfigurationActivity.this.configurationAdapter.updateList(StartActivity.multiDbModelList);
                        if (!StartActivity.multiDbModelList.isEmpty()) {
                            ConfigurationActivity.this.binding.noData.setVisibility(View.GONE);
                            ConfigurationActivity.this.binding.recycleView.setVisibility(View.VISIBLE);
                        } else {
                            ConfigurationActivity.this.binding.recycleView.setVisibility(View.GONE);
                            ConfigurationActivity.this.binding.noData.setVisibility(View.VISIBLE);
                        }
                        new SaveAsyncTask().execute(new String[0]);
                        return;
                    }
                    ConfigurationActivity configurationActivity = ConfigurationActivity.this;
                    Toast.makeText(configurationActivity, configurationActivity.getResources().getString(R.string.ac88), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    ConfigurationActivity configurationActivity2 = ConfigurationActivity.this;
                    Toast.makeText(configurationActivity2, configurationActivity2.getResources().getString(R.string.ac88), Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    /* access modifiers changed from: protected */
    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityConfigurationBinding inflate = ActivityConfigurationBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
//        AdAdmob.FullscreenAd(this);
//        GoogleAds.getInstance().admobBanner(this, findViewById(R.id.nativeLay));
        setSize();
        setData();

        SpManager.setMultiConfigurationEventCount(SpManager.getMultiConfigurationEventCount() + 1);
        Log.e(TAG, "onCreate: event_count multi mode target visit ==> " + SpManager.getMultiConfigurationEventCount());
    }

    private void setSize() {
        Resizer.getheightandwidth(this);
        Resizer.setSize(this.binding.header, 1080, 180, true);
        Resizer.setSize(this.binding.back, 105, 105, true);
        Resizer.setSize(this.binding.more, 105, 105, true);
        Resizer.setSize(this.binding.noData, TypedValues.MotionType.TYPE_ANIMATE_CIRCLEANGLE_TO, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, true);
        Resizer.setMargins(this.binding.back, 50, 0, 50, 0);
        Resizer.setMargins(this.binding.more, 50, 0, 50, 0);
    }

    private void setData() {
        if (!StartActivity.multiDbModelList.isEmpty()) {
            this.binding.noData.setVisibility(View.GONE);
            this.binding.recycleView.setVisibility(View.VISIBLE);
        } else {
            this.binding.recycleView.setVisibility(View.GONE);
            this.binding.noData.setVisibility(View.VISIBLE);
        }
        this.configurationAdapter = new ConfigurationAdapter(this, StartActivity.multiDbModelList, this);
        this.binding.recycleView.setAdapter(this.configurationAdapter);
        this.binding.back.setOnClickListener(ConfigurationActivity.this::backOnClickListener);
        this.binding.popBg.setOnClickListener(ConfigurationActivity.this::popBgOnClickListener);
        this.binding.more.setOnClickListener(ConfigurationActivity.this::moreOnClickListener);
        this.binding.importTv.setOnClickListener(ConfigurationActivity.this::importTvOnClickListener);
        this.binding.exportTv.setOnClickListener(ConfigurationActivity.this::exportTvOnClickListener);
    }

    /* access modifiers changed from: package-private */
    public void backOnClickListener(View view) {
        onBackPressed();
    }

    /* access modifiers changed from: package-private */
    public void popBgOnClickListener(View view) {
        this.binding.popBg.setVisibility(View.GONE);
    }

    /* access modifiers changed from: package-private */
    public void moreOnClickListener(View view) {
        this.binding.popBg.setVisibility(View.VISIBLE);
    }

    /* access modifiers changed from: package-private */
    public void importTvOnClickListener(View view) {
        if (SystemClock.elapsedRealtime() - this.mLastClickTime >= 1500) {
            this.mLastClickTime = SystemClock.elapsedRealtime();
            this.binding.popBg.setVisibility(View.GONE);
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("*/*");
            this.someActivityResultLauncher.launch(intent);
        }
    }

    /* access modifiers changed from: package-private */
    public void exportTvOnClickListener(View view) {
        if (SystemClock.elapsedRealtime() - this.mLastClickTime >= 1500) {
            this.mLastClickTime = SystemClock.elapsedRealtime();
            if (!StartActivity.multiDbModelList.isEmpty()) {
                this.binding.popBg.setVisibility(View.GONE);
                this.exportList.clear();
                this.exportList.addAll(StartActivity.multiDbModelList);
                new ExportAsyncTask().execute(StorageUtils.create_folder(getResources().getString(R.string.app_name)), "Auto_click_" + System.currentTimeMillis() + ".json");
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.ac89), LENGTH_SHORT).show();
        }
    }

    @Override
    public void click(int i, MultiDbModel multiDbModel2, int i2) {
        if (i == 0) {
            StartActivity.position = i2;
            StartActivity.demoMultiDbModel = multiDbModel2;
            setResult(-1, new Intent().putExtra("Choice", 1));
            finish();
        } else if (i == 1) {
            if (SystemClock.elapsedRealtime() - this.mLastClickTime >= 1500) {
                this.mLastClickTime = SystemClock.elapsedRealtime();
                StartActivity.multiDbModelList.add(new MultiDbModel(multiDbModel2.getName(), multiDbModel2.getIntervalType(), multiDbModel2.getIntervalCount(), multiDbModel2.getSwipeCount(), multiDbModel2.getStopType(), multiDbModel2.getHour(), multiDbModel2.getMinute(), multiDbModel2.getSecond(), multiDbModel2.getNocCount(), multiDbModel2.getArrayList()));
                this.configurationAdapter.updateList(StartActivity.multiDbModelList);
                Toast.makeText(this, getResources().getString(R.string.ac81), LENGTH_SHORT).show();
                new SaveAsyncTask().execute(new String[0]);
            }
        } else if (i == 2 && SystemClock.elapsedRealtime() - this.mLastClickTime >= 1500) {
            this.mLastClickTime = SystemClock.elapsedRealtime();
            this.multiDbModel = multiDbModel2;
            this.pos = i2;
            openPcmDialog();
        }
    }

    public void openPcmDialog() {
        PcmMoreDialogBinding inflate = PcmMoreDialogBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        inflate.editName.setOnClickListener(view -> ConfigurationActivity.this.editNameOnClick(dialog, view));

        inflate.delete.setOnClickListener(view -> ConfigurationActivity.this.deleteOnClickListener(dialog, view));

        inflate.export.setOnClickListener(view -> ConfigurationActivity.this.exportOnClickListener(dialog, view));
    }

    public void editNameOnClick(Dialog dialog, View view) {
        dialog.dismiss();
        openEditDialog();
    }

    /* access modifiers changed from: package-private */
    public void deleteOnClickListener(Dialog dialog, View view) {
        dialog.dismiss();
        openDeleteDialog();
    }

    /* access modifiers changed from: package-private */
    public void exportOnClickListener(Dialog dialog, View view) {
        dialog.dismiss();
        this.exportList.clear();
        this.exportList.add(this.multiDbModel);
        new ExportAsyncTask().execute(StorageUtils.create_folder(getResources().getString(R.string.app_name)), this.multiDbModel.getName() + ".json");
    }

    public void openEditDialog() {
        final EditConfigurationDialogBinding inflate = EditConfigurationDialogBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        inflate.name.setText(this.multiDbModel.getName());
        inflate.ok.setOnClickListener(view -> okOnClickListener(inflate, dialog, view));
        inflate.cancel.setOnClickListener(view -> {
        });
        inflate.name.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    inflate.name.setError(null);
                } else {
                    inflate.name.setError(ConfigurationActivity.this.getResources().getString(R.string.ac83));
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void okOnClickListener(EditConfigurationDialogBinding editConfigurationDialogBinding, Dialog dialog, View view) {
        if (SystemClock.elapsedRealtime() - this.mLastClickTime >= 1500) {
            this.mLastClickTime = SystemClock.elapsedRealtime();
            if (editConfigurationDialogBinding.name.getText().length() > 0) {
                StartActivity.multiDbModelList.get(this.pos).setName(editConfigurationDialogBinding.name.getText().toString());
                this.configurationAdapter.updateList(StartActivity.multiDbModelList);
                Toast.makeText(this, getResources().getString(R.string.ac84), LENGTH_SHORT).show();
                new SaveAsyncTask().execute(new String[0]);
                dialog.dismiss();
                return;
            }
            editConfigurationDialogBinding.name.setError(getResources().getString(R.string.ac83));
        }
    }

    public void openDeleteDialog() {
        DeleteDialogBinding inflate = DeleteDialogBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(1);
        dialog.setContentView(inflate.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        inflate.ok.setOnClickListener(ConfigurationActivity.this::okOnClickListener);
        inflate.cancel.setOnClickListener(ConfigurationActivity.this::okOnClickListener);
    }

    /* access modifiers changed from: package-private */
    public void okOnClickListener(View view) {
        if (SystemClock.elapsedRealtime() - this.mLastClickTime >= 1500) {
            this.mLastClickTime = SystemClock.elapsedRealtime();
            StartActivity.multiDbModelList.remove(this.pos);
            this.configurationAdapter.updateList(StartActivity.multiDbModelList);
            if (!StartActivity.multiDbModelList.isEmpty()) {
                this.binding.noData.setVisibility(View.GONE);
                this.binding.recycleView.setVisibility(View.VISIBLE);
            } else {
                this.binding.recycleView.setVisibility(View.GONE);
                this.binding.noData.setVisibility(View.VISIBLE);
            }
            Toast.makeText(this, getResources().getString(R.string.ac86), Toast.LENGTH_SHORT).show();
            new SaveAsyncTask().execute(new String[0]);
        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void adShow() {

    }

    /* access modifiers changed from: package-private */
    public class SaveAsyncTask extends AsyncTask<String, Void, String> {
        public void onPostExecute(String str) {
        }

        SaveAsyncTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String[] strArr) {
            JsonWriter.writeListToJsonFile(ConfigurationActivity.this, "Data.json", StartActivity.multiDbModelList);
            return null;
        }
    }

    public class ExportAsyncTask extends AsyncTask<String, Void, String> {
        ExportAsyncTask() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String[] strArr) {
            ConfigurationActivity configurationActivity = ConfigurationActivity.this;
            JsonWriter.writeListToJsonFile(configurationActivity, strArr[0], strArr[1], configurationActivity.exportList);
            return null;
        }

        public void onPostExecute(String str) {
            ConfigurationActivity configurationActivity = ConfigurationActivity.this;
            Toast.makeText(configurationActivity, configurationActivity.getResources().getString(R.string.ac87), Toast.LENGTH_SHORT).show();
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if (DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                return Environment.getExternalStorageDirectory() + "/" + DocumentsContract.getDocumentId(uri).split(":")[1];
            }
            if (isDownloadsDocument(uri)) {
                uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue());
            } else if (isMediaDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                String str = split[0];
                if ("image".equals(str)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String[] strArr = {split[1]};
                if (!FirebaseAnalytics.Param.CONTENT.equalsIgnoreCase(uri.getScheme())) {
                    try {
                        try (Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, "_id=?", strArr, null)) {
                            assert query != null;
                            int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
                            if (query.moveToFirst()) {
                                return query.getString(columnIndexOrThrow);
                            }
                        }
                    } catch (Exception unused) {
                        Log.e(TAG, "getPath: " + unused.getMessage());
                    }
                } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                    return uri.getPath();
                }
                return null;
            }
        }
        FirebaseAnalytics.Param.CONTENT.equalsIgnoreCase(uri.getScheme());
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static List<MultiDbModel> parseJson(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            int i = 0;
            while (i < jSONArray.length()) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
                int i2 = jSONObject.getInt("intervalType");
                int i3 = jSONObject.getInt("intervalCount");
                int i4 = jSONObject.getInt("swipeCount");
                int i5 = jSONObject.getInt("stopType");
                int i6 = jSONObject.getInt("hour");
                int i7 = jSONObject.getInt("minute");
                int i8 = jSONObject.getInt("second");
                int i9 = jSONObject.getInt("nocCount");
                JSONArray jSONArray2 = jSONObject.getJSONArray("targets");
                ArrayList arrayList2 = new ArrayList();
                int i10 = 0;
                while (i10 < jSONArray2.length()) {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i10);
                    try {
                        arrayList2.add(new MultiModelTwo(jSONObject2.getInt("type"), (float) jSONObject2.getInt("clickOneX"), (float) jSONObject2.getInt("clickOneY"), (float) jSONObject2.getInt("clickTwoX"), (float) jSONObject2.getInt("clickTwoY")));
                        i10++;
                        jSONArray = jSONArray;
                        jSONArray2 = jSONArray2;
                        i = i;
                        i7 = i7;
                        i6 = i6;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("TAG", "parseJson: " + e.getMessage());
                        return arrayList;
                    }
                }
                arrayList.add(new MultiDbModel(string, i2, i3, i4, i5, i6, i7, i8, i9, arrayList2));
                i++;
                jSONArray = jSONArray;
            }
            return arrayList;
        } catch (JSONException e2) {
            e2.printStackTrace();
            Log.d("TAG", "parseJson: " + e2.getMessage());
            return arrayList;
        }
    }

    public String LoadData(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            fileInputStream.close();
            return new String(bArr, Key.STRING_CHARSET_NAME);
        } catch (IOException unused) {
            return "";
        }
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        setResult(-1, new Intent().putExtra("Choice", 0));
        finish();
    }
}
