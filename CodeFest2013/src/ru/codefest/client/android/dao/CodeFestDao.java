package ru.codefest.client.android.dao;

import java.util.ArrayList;
import java.util.List;

import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.CodeFestItem;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import ru.codefest.client.android.provider.CodeFestProvider;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;

import com.petriyov.android.libs.bindings.BinderHelper;

public class CodeFestDao {

    private Context context;

    private BinderHelper binderHelper;

    public CodeFestDao(Context context, BinderHelper binderHelper) {
        this.context = context;
        this.binderHelper = binderHelper;
    }

    public <T extends CodeFestItem> void bulkInsertItems(List<T> list,
            String tableName) {
        context.getContentResolver().bulkInsert(
                CodeFestProvider.getUri(tableName),
                binderHelper.adaptValuesFromList(list));
    }

    public void deleteAllEntities() {
        ArrayList<ContentProviderOperation> deleteOperations = new ArrayList<ContentProviderOperation>();
        deleteOperations.add(ContentProviderOperation.newDelete(
                CodeFestProvider.getUri(Category.TABLE_NAME)).build());
        deleteOperations.add(ContentProviderOperation.newDelete(
                CodeFestProvider.getUri(LecturePeriod.TABLE_NAME)).build());
        deleteOperations.add(ContentProviderOperation.newDelete(
                CodeFestProvider.getUri(Lecture.TABLE_NAME)).build());
        try {
            context.getContentResolver().applyBatch(
                    CodeFestProvider.CONTENT_URI, deleteOperations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
            new RuntimeException(e);
        }
    }

    public Lecture getLectureById(int lectureId) {
        List<Lecture> list = new ArrayList<Lecture>();
        Cursor cursor = context.getContentResolver().query(
                CodeFestProvider.getUri(Lecture.TABLE_NAME), null,
                "WHERE _ID = ?1", new String[] { String.valueOf(lectureId) },
                null);
        if (cursor != null) {
            list = binderHelper.adaptListFromCursor(cursor, Lecture.class);
            cursor.close();
        }
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public <T extends CodeFestItem> List<T> getList(Class<T> clazz,
            String tableName) {
        List<T> list = new ArrayList<T>();
        Cursor cursor = context.getContentResolver().query(
                CodeFestProvider.getUri(tableName), null, null, null, null);
        if (cursor != null) {
            list = binderHelper.adaptListFromCursor(cursor, clazz);
            cursor.close();
        }
        return list;
    }
}
