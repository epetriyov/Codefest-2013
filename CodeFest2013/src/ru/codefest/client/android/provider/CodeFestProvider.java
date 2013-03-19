package ru.codefest.client.android.provider;

import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import android.content.UriMatcher;
import android.net.Uri;

import com.petriyov.android.libs.contentprovider.CustomContentProvider;

public class CodeFestProvider extends CustomContentProvider {

    static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "codefest.db";

    public static final String CONTENT_URI = "ru.codefest.provider.data";

    private static final Uri URI_PREFIX = Uri.parse("content://" + CONTENT_URI
            + "/");

    private static final Class<?>[] TABLES = { Category.class,
            LecturePeriod.class, Lecture.class };

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_URI, "*/#", SINGLE_ROW);
        uriMatcher.addURI(CONTENT_URI, "*", ALL_ROWS);
    }

    public static Uri getUri(String table) {
        return Uri.parse(URI_PREFIX + table);
    }

    public static Uri getUri(String table, int id) {
        return Uri.parse(URI_PREFIX + table + "/" + id);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext(), DATABASE_NAME,
                DATABASE_VERSION, TABLES);
        typePrefix = "vnd.android.cursor.dir/vnd.privatemanager.";
        return super.onCreate();
    }
}
