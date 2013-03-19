package ru.codefest.client.android.model;

import java.util.List;

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

    private List<Lecture> lectureList;

    public LecturePeriod() {
        super();
    }

    public LecturePeriod(String period, int dayNumber) {
        this.period = period;
        this.dayNumber = dayNumber;
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

}
