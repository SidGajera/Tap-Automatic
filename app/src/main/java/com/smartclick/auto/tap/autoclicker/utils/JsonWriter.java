package com.smartclick.auto.tap.autoclicker.utils;

import android.content.Context;
import com.smartclick.auto.tap.autoclicker.model.MultiDbModel;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class JsonWriter {
    static Exception th;

    public static void writeListToJsonFile(Context context, String str, List<MultiDbModel> list) {
        String json = new Gson().toJson(list);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(context.getFilesDir(), str))));
            try {
                bufferedWriter.write(json);
                bufferedWriter.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (Throwable th2) {
                th2.addSuppressed(th2);
                try {
                    throw th;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void writeListToJsonFile(Context context, String str, String str2, List<MultiDbModel> list) {
        String json = new Gson().toJson(list);
        File file = new File(new File(str), str2);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            try {
                bufferedWriter.write(json);
                bufferedWriter.close();
                StorageUtils.scanFile(context, file.getAbsolutePath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (Throwable th2) {
                th2.addSuppressed(th2);
                try {
                    throw th;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
