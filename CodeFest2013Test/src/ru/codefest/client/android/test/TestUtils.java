package ru.codefest.client.android.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;
import android.content.Context;

public class TestUtils extends TestCase {
    public static String getTestJsonFeedFromAssets(Context context,
                                                   String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader bufferrReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            StringBuilder testJsonBuilder = new StringBuilder();
            char[] buf = new char[1024];
            int length;
            while ((length = bufferrReader.read(buf)) != -1) {
                testJsonBuilder.append(buf, 0, length);
            }
            return testJsonBuilder.toString();
        } catch (IOException e2) {
            e2.printStackTrace();
            fail("Cant find json file at assets!");
        }
        return "";

    }

}
