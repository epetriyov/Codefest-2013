package ru.codefest.client.android.dao;

import java.util.ArrayList;
import java.util.List;

import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.provider.CodeFestProvider;
import android.content.Context;
import android.database.Cursor;

import com.petriyov.android.libs.bindings.BinderHelper;

public class LectureDao {

    private Context context;

    private BinderHelper binderHelper;

    public LectureDao(Context context, BinderHelper binderHelper) {
        this.context = context;
        this.binderHelper = binderHelper;
    }

    public void bulkInsertLectures(List<Lecture> lectureList) {
        context.getContentResolver().bulkInsert(
                CodeFestProvider.getUri(Lecture.TABLE_NAME),
                binderHelper.adaptValuesFromList(lectureList));
    }

    public Lecture getLectureById(int lectureId) {
        List<Lecture> list = new ArrayList<Lecture>();
        Cursor cursor = context.getContentResolver().query(
                CodeFestProvider.getUri(Lecture.TABLE_NAME), null,
                "WHERE ID = ?1", new String[] { String.valueOf(lectureId) },
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

    public List<Lecture> getLectureList() {
        List<Lecture> list = new ArrayList<Lecture>();
        Cursor cursor = context.getContentResolver().query(
                CodeFestProvider.getUri(Lecture.TABLE_NAME), null, null, null,
                null);
        if (cursor != null) {
            list = binderHelper.adaptListFromCursor(cursor, Lecture.class);
            cursor.close();
        }
        return list;
    }
}
