package com.petriyov.android.libs.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.util.Log;

public class CustomLog {
    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

    public static void logToFile(String url, String message) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(new File(url), true), "UTF-8"));
            out.write(message);
            out.newLine();
            out.close();
        } catch (IOException e) {
            Log.e("LOG ERROR!!!", "something didn't logged");
            e.printStackTrace();
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
