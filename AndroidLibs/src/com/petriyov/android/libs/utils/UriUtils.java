package com.petriyov.android.libs.utils;

import android.net.Uri;


public class UriUtils {
    public static String getTableFromUri(Uri uri, boolean hasId) {
        int lastIndex;
        if (!hasId) {
            lastIndex = uri.getPath().length();
        } else {
            lastIndex = uri.getPath().length()
                    - uri.getLastPathSegment().length() - 1;
        }
        return uri.getPath().substring(1, lastIndex);
    }
}
