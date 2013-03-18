package ru.codefest.client.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.petriyov.android.libs.contentprovider.CustomContentProvider;

/**
 * lecture info
 */
@DatabaseTable(tableName = Lecture.TABLE_NAME)
public class Lecture extends CodeFestItem {

    public static final String TABLE_NAME = "Lecture";

    @DatabaseField(generatedId = true, columnName = CustomContentProvider.KEY_ID)
    public int id;

    @DatabaseField(index = true)
    public String name;

    @DatabaseField
    public String reporterInfo;

    @DatabaseField
    public String reporterDescription;

    @DatabaseField
    public String reporterPhotoUrl;

    @DatabaseField
    public String lectureDescription;

    @DatabaseField
    public int categoryId;

    @DatabaseField
    public int periodId;

    public String categoryName;

    public String categoryColor;

    public Lecture() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Lecture [name=");
        builder.append(name);
        builder.append(", \nreporterInfo=");
        builder.append(reporterInfo);
        builder.append(", \nreporterDescription=");
        builder.append(reporterDescription);
        builder.append(", \nreporterPhotoUrl=");
        builder.append(reporterPhotoUrl);
        builder.append(", \nlectureDescription=");
        builder.append(lectureDescription);
        builder.append(", \ncategoryId=");
        builder.append(categoryId);
        builder.append(", \nperiodId=");
        builder.append(periodId);
        builder.append("]");
        return builder.toString();
    }

}
