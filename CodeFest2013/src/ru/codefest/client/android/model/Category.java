package ru.codefest.client.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.petriyov.android.libs.contentprovider.CustomContentProvider;

@DatabaseTable(tableName = Category.TABLE_NAME)
public class Category extends CodeFestItem {

    public static final String TABLE_NAME = "Category";

    @DatabaseField(generatedId = true, columnName = CustomContentProvider.KEY_ID)
    public int id;

    @DatabaseField
    public String name;

    @DatabaseField
    public String color;

    public Category() {
        super();
    }

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
