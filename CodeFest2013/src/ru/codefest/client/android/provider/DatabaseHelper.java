package ru.codefest.client.android.provider;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.petriyov.android.libs.log.CustomLog;
import com.petriyov.android.libs.utils.DatabaseUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static void copyDbFromAssets(Context context) {
        File dbFile = new File(context.getDatabasePath(
                CodeFestProvider.DATABASE_NAME).getAbsolutePath());
        if (!dbFile.exists()) {
            // there are no database file
            try {
                DatabaseUtils.copyPrecompiledDatabase(context, dbFile,
                        "codefest_%d.db");
            } catch (IOException e) {
                e.printStackTrace();
                CustomLog.i("DatabaseHelper",
                        "Failed to copy database from assets!");
            }
        }
    }

    private Class<?>[] tables;

    public DatabaseHelper(Context context, String databaseName,
            int databaseVersion, Class<?>[] tables) {
        super(context, databaseName, null, databaseVersion);
        this.tables = tables;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        for (int i = 0; i < tables.length; i++) {
            try {
                TableUtils.createTable(arg1, tables[i]);
            } catch (SQLException e1) {
                e1.printStackTrace();
                CustomLog.e(tables[i].getName(), "Could'n create table");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
            int arg3) {
        for (int i = 0; i < tables.length; i++) {
            try {
                TableUtils.dropTable(arg1, tables[i], true);
            } catch (SQLException e) {
                CustomLog.e(tables[i].getName(), "Could'n delete table");
            }
        }
        onCreate(arg0, arg1);

    }

}
