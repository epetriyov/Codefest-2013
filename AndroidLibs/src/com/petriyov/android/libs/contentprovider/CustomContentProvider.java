package com.petriyov.android.libs.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.petriyov.android.libs.db.CustomDatabaseHelper;
import com.petriyov.android.libs.utils.UriUtils;

public class CustomContentProvider extends ContentProvider {

	protected static final int ALL_ROWS = 1;

	protected static final int SINGLE_ROW = 2;

	private final String KEY_ID = "_ID";

	protected static UriMatcher uriMatcher;

	protected CustomDatabaseHelper dbHelper;

	protected String typePrefix;

	private SQLiteDatabase db;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case ALL_ROWS:
			count = db.delete(UriUtils.getTableFromUri(uri, false), selection,
					selectionArgs);
			break;
		case SINGLE_ROW:
			String segment = uri.getPathSegments().get(1);
			count = db.delete(UriUtils.getTableFromUri(uri, true), KEY_ID
					+ "="
					+ segment
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case ALL_ROWS:
			return typePrefix
					+ UriUtils.getTableFromUri(uri, false).toLowerCase();
		case SINGLE_ROW:
			return typePrefix
					+ UriUtils.getTableFromUri(uri, true).toLowerCase();
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert(UriUtils.getTableFromUri(uri, false), null,
				values);
		if (rowId > 0) {
			Uri newUri = ContentUris.withAppendedId(uri, rowId);
			getContext().getContentResolver().notifyChange(newUri, null);
			return newUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			queryBuilder.setTables(UriUtils.getTableFromUri(uri, true));
			queryBuilder.appendWhere(KEY_ID + "="
					+ uri.getPathSegments().get(1));
			break;
		case ALL_ROWS:
			queryBuilder.setTables(UriUtils.getTableFromUri(uri, false));
			break;
		default:
			break;
		}
		db = dbHelper.getReadableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count;
		db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case ALL_ROWS:
			count = db.update(UriUtils.getTableFromUri(uri, false), values,
					selection, selectionArgs);
			break;
		case SINGLE_ROW:
			String segment = uri.getPathSegments().get(1);
			count = db.update(UriUtils.getTableFromUri(uri, true), values,
					KEY_ID
							+ "="
							+ segment
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
