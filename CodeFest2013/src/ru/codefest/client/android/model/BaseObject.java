package ru.codefest.client.android.model;

import com.j256.ormlite.field.DatabaseField;

public abstract class BaseObject {

    @DatabaseField(generatedId = true, columnName = "_ID")
    protected Integer id;

    public BaseObject() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public abstract String toString();
}
