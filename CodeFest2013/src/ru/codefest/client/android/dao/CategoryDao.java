package ru.codefest.client.android.dao;

import java.util.ArrayList;
import java.util.List;

import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.provider.CodeFestProvider;
import android.content.Context;
import android.database.Cursor;

import com.petriyov.android.libs.bindings.BinderHelper;

public class CategoryDao {

    private Context context;

    private BinderHelper binderHelper;

    public CategoryDao(Context context, BinderHelper binderHelper) {
        this.context = context;
        this.binderHelper = binderHelper;
    }

    public void bulkInsertCategories(List<Category> categoryList) {
        context.getContentResolver().bulkInsert(
                CodeFestProvider.getUri(Category.TABLE_NAME),
                binderHelper.adaptValuesFromList(categoryList));
    }

    public List<Category> getCategoryList() {
        List<Category> list = new ArrayList<Category>();
        Cursor cursor = context.getContentResolver().query(
                CodeFestProvider.getUri(Category.TABLE_NAME), null, null, null,
                null);
        if (cursor != null) {
            list = binderHelper.adaptListFromCursor(cursor, Category.class);
            cursor.close();
        }
        return list;
    }
}
