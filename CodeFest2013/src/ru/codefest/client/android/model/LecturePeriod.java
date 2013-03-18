package ru.codefest.client.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.petriyov.android.libs.contentprovider.CustomContentProvider;

@DatabaseTable(tableName = LecturePeriod.TABLE_NAME)
public class LecturePeriod extends CodeFestItem {

    public static final String TABLE_NAME = "LecturePeriod";

    @DatabaseField(generatedId = true, columnName = CustomContentProvider.KEY_ID)
    public int id;

    @DatabaseField
    public String period;

    @DatabaseField
    public int dayNumber;

    public LecturePeriod() {
        super();
    }

    public LecturePeriod(String period, int dayNumber) {
        this.period = period;
        this.dayNumber = dayNumber;
    }

}
