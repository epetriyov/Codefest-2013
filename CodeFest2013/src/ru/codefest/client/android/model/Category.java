package ru.codefest.client.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * category of lecture
 */
@DatabaseTable(tableName = Category.TABLE_NAME)
public class Category extends BaseObject implements IStorable {

    public static final String TABLE_NAME = "Category";

    @DatabaseField(index = true)
    public String name;

    public Category() {
        super();
    }

    public Category(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Category) {
            return name.contains(((Category) o).name);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Category [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
