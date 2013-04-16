package com.petriyov.android.libs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import android.content.Context;
import android.content.res.AssetManager;

public class DatabaseUtils {

    /**
     * @param context
     * @param dbFile       - file where to copy
     * @param dbFileFormat - string format of db-files from assets (for example,
     *                     database_%d.db) <br>
     *                     Important: <br>
     *                     db-files must be numbered from 1 to ... <br>
     *                     size of db-files must be <= 1 Mb.
     * @throws IOException
     */
    public static void copyPrecompiledDatabase(Context context, File dbFile,
                                               String dbFileFormat) throws IOException {
        AssetManager assetManager = context.getAssets();
        new File(dbFile.getParentFile().getAbsolutePath()).mkdirs();

        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[32 * 1024];
        int r;
        String[] files = assetManager.list("");
        Arrays.sort(files);
        for (int i = 1; i < 50; i++) {
            String fn = String.format(dbFileFormat, i);
            if (Arrays.binarySearch(files, fn) < 0) {
                break;
            }
            InputStream is = assetManager.open(fn);
            while ((r = is.read(buffer)) != -1) {
                os.write(buffer, 0, r);
            }
            os.flush();
            is.close();
        }
        os.close();

    }
}
