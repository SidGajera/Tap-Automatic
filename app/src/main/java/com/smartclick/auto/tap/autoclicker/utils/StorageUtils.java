package com.smartclick.auto.tap.autoclicker.utils;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StorageUtils {
    public static String TAG = StorageUtils.class.getName();

    public static String create_folder(String str) {
        if (Build.VERSION.SDK_INT <= 29) {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + str);
            Log.d(TAG, "createDir:" + file);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        File file2 = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + File.separator + "/" + str)));
        Log.d(TAG, "createDir:" + file2);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        return file2.getAbsolutePath();
    }

    public static String create_hidden_folder(String str) {
        if (Build.VERSION.SDK_INT <= 29) {
            File file = new File(Environment.getExternalStorageDirectory() + "/." + str);
            Log.d(TAG, "createDir:" + file);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        File file2 = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + File.separator + "/." + str)));
        Log.d(TAG, "createDir:" + file2);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        return file2.getAbsolutePath();
    }

    public static String create_folder_with_sub_folder(String str, String str2) {
        if (Build.VERSION.SDK_INT <= 29) {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + str + "/" + str2);
            Log.d(TAG, "createDir:" + file);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        File file2 = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + File.separator + str + File.separator + str2)));
        Log.d(TAG, "createDir:" + file2);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        return file2.getAbsolutePath();
    }

    public static void saveTextContent(String str, String str2) {
        Log.i(TAG, "Saving text : " + str2);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
    }

    public static void scanFile(Context context, String str) {
        MediaScannerConnection.scanFile(context, new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() {


            public void onScanCompleted(String str, Uri uri) {
                Log.i("TAG", "Finished scanning " + str);
            }
        });
    }

    public static String saveimage(Context context, Bitmap bitmap, String str, String str2) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, str2);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast(context, "Saved Successfully");
            String absolutePath = file2.getAbsolutePath();
            MediaScannerConnection.scanFile(context, new String[]{file2.getPath()}, null, null);
            return absolutePath;
        } catch (Exception unused) {
            Toast(context, "Failed to Save");
            return null;
        }
    }



    public static void Toast(Context context, String str) {
        Toast(context, str, 0);
    }

    public static void Toast(Context context, String str, int i) {
        Toast.makeText(context, str, i).show();
    }

    public static ArrayList<File> getfolderdata(String str) {
        ArrayList<File> arrayList = new ArrayList<>();
        if (!new File(str).exists()) {
            return null;
        }
        File file = new File(str);
        Log.d(TAG, "onResume: " + file);
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        for (File file2 : listFiles) {
            arrayList.add(file2);
        }
        return arrayList;
    }

    public static void shareImageFile(Context context, String str) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("Image/*");
        intent.addFlags(1);
        intent.putExtra("android.intent.extra.STREAM", parse);
        context.startActivity(intent);
    }

    public static void shareVideoFile(Context context, String str) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("Video/*");
        intent.addFlags(1);
        intent.putExtra("android.intent.extra.STREAM", parse);
        context.startActivity(intent);
    }

    public static Bitmap getBitmapFromFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

    public static boolean deletefile(Context context, String str) {
        File file = new File(str);
        if (!file.exists() || !file.delete()) {
            return false;
        }
        MediaScannerConnection.scanFile(context, new String[]{file.getPath()}, null, null);
        return true;
    }

//    private static class DownloadFileFromURL extends AsyncTask<String, Integer, String> {
//        Context context;
//        ProgressDialog f193pd;
//        String nameoffile;
//        String pathFile = "";
//        String pathFolder;
//
//        public DownloadFileFromURL(Context context2, String str, String str2) {
//            this.context = context2;
//            this.pathFolder = str;
//            this.nameoffile = str2;
//        }
//
//        public String doInBackground(String... strArr) {
//            InputStream inputStream;
//            String str = this.pathFolder + "/" + this.nameoffile;
//            this.pathFile = str;
//            int i = 1;
//            MediaScannerConnection.scanFile(this.context, new String[]{str}, null, null);
//            File file = new File(this.pathFolder);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
//            try {
//                httpURLConnection.connect();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Log.d(StorageUtils.TAG, "doInBackground: connectioncode " + httpURLConnection.getResponseCode());
//            if (httpURLConnection.getResponseCode() == 200) {
//                int contentLength = httpURLConnection.getContentLength();
//                InputStream inputStream2 = httpURLConnection.getInputStream();
//                try {
//                    Log.e(StorageUtils.TAG, "doInBackground: " + this.pathFile);
//                    FileOutputStream fileOutputStream = new FileOutputStream(this.pathFile);
//                    byte[] bArr = new byte[4096];
//                    long j = 0;
//                    while (true) {
//                        int read = inputStream2.read(bArr);
//                        if (read == -1) {
//                            try {
//                                fileOutputStream.close();
//                                if (inputStream2 != null) {
//                                    inputStream2.close();
//                                }
//                            } catch (IOException unused) {
//                            }
//                            if (httpURLConnection != null) {
//                                httpURLConnection.disconnect();
//                            }
//                            return this.pathFile;
//                        } else if (isCancelled()) {
//                            if (httpURLConnection != null) {
//                                httpURLConnection.disconnect();
//                            }
//                            return null;
//                        } else {
//                            j += (long) read;
//                            if (contentLength > 0) {
//                                Integer[] numArr = new Integer[i];
//                                inputStream = inputStream2;
//                                numArr[0] = Integer.valueOf((int) ((100 * j) / ((long) contentLength)));
//                                publishProgress(numArr);
//                            } else {
//                                inputStream = inputStream2;
//                            }
//                            fileOutputStream.write(bArr, 0, read);
//                            inputStream2 = inputStream;
//                            i = 1;
//                        }
//                    }
//                } catch (Exception e) {
//                    String exc = e.toString();
//                    if (inputStream2 != null) {
//                        inputStream2.close();
//                    }
//                    if (httpURLConnection != null) {
//                        httpURLConnection.disconnect();
//                    }
//                    return exc;
//                } catch (Throwable th) {
//                    if (inputStream2 != null) {
//                        inputStream2.close();
//                    }
//                    if (httpURLConnection != null) {
//                        httpURLConnection.disconnect();
//                    }
//                    throw th;
//                }
//            } else if (httpURLConnection == null) {
//                return "Please Enter Valid Name";
//            } else {
//                httpURLConnection.disconnect();
//                return "Please Enter Valid Name";
//            }
//        }
//
//        /* access modifiers changed from: protected */
//        public void onPreExecute() {
//            super.onPreExecute();
//            ProgressDialog progressDialog = new ProgressDialog(this.context);
//            this.f193pd = progressDialog;
//            progressDialog.setTitle("Processing...");
//            this.f193pd.setMessage("Please wait.");
//            this.f193pd.setMax(100);
//            this.f193pd.setProgressStyle(1);
//            this.f193pd.setCancelable(false);
//            this.f193pd.show();
//        }
//
//        public void onProgressUpdate(Integer... numArr) {
//            super.onProgressUpdate(numArr);
//            this.f193pd.setProgress(Integer.parseInt(String.valueOf(numArr[0])));
//        }
//
//        public void onPostExecute(String str) {
//            ProgressDialog progressDialog = this.f193pd;
//            if (progressDialog != null) {
//                progressDialog.dismiss();
//                if (str != null) {
//                    MediaScannerConnection.scanFile(this.context, new String[]{str}, null, null);
//                }
//                Toast.makeText(this.context, "Video Downloaded" + str, 0).show();
//            }
//        }
//    }

    public static ArrayList<String> getasset_folder_data(Context context, String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        try {
            for (String str2 : context.getAssets().list(str)) {
                arrayList.add(str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void share_app(Context context) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        context.startActivity(Intent.createChooser(intent, "Share via"));
    }

    public static void rate_app(Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static String getTimeString(long j, String str) {
        return new SimpleDateFormat(str).format(Long.valueOf(j));
    }

    public static void shareImage(Context context, Uri uri) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addFlags(524288);
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", uri);
        context.startActivity(Intent.createChooser(intent, "Share Image Using"));
    }

    public static String create_folder_in_app_package_dir(Context context, String str) {
        File file = new File(context.getFilesDir(), str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String timeConversion(long j) {
        return String.format("%02d:%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toHours(j) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(j))), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j))), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j))));
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss-SSS", Locale.getDefault()).format(Calendar.getInstance().getTime()).replace(" ", "");
    }
}
