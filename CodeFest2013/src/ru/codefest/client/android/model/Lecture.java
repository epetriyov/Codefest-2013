package ru.codefest.client.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * lecture info
 */
@DatabaseTable(tableName = Lecture.TABLE_NAME)
public class Lecture {

    public static final String TABLE_NAME = "Lecture";

    @DatabaseField(generatedId = true, columnName = "_ID")
    public Integer id;

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
    public String startDate;

    @DatabaseField
    public String endDate;

    @DatabaseField
    public String categoryName;

    @DatabaseField
    public int categoryColor;

    public Lecture() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Lecture [name=");
        builder.append(name);
        builder.append(", reporterInfo=");
        builder.append(reporterInfo);
        builder.append(", reporterDescription=");
        builder.append(reporterDescription);
        builder.append(", reporterPhotoUrl=");
        builder.append(reporterPhotoUrl);
        builder.append(", descriptionHtml=");
        builder.append(lectureDescription);
        builder.append(", categoryName=");
        builder.append(categoryName);
        builder.append(", categoryColor=");
        builder.append(categoryColor);
        builder.append(", starDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append("]");
        return builder.toString();
    }

}
