package com.petriyov.android.libs.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.petriyov.android.libs.log.CustomLog;

public class CustomDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private Class<?>[] tables; 
	
	public CustomDatabaseHelper(Context context, String databaseName,
			 int databaseVersion,Class<?>[] tables) {
		super(context, databaseName, null, databaseVersion);
		this.tables = tables;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		for(int i = 0; i < tables.length; i++)
		{
			try {
				TableUtils.createTable(arg1, tables[i]);
			} catch (SQLException e) {
				CustomLog.e(tables[i].getName(), "Could'n create table");
			}
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		for(int i = 0; i < tables.length; i++)
		{
			try {
				TableUtils.dropTable(arg1, tables[i],true);
			} catch (SQLException e) {
				CustomLog.e(tables[i].getName(), "Could'n delete table");
			}
		}
	}

}
