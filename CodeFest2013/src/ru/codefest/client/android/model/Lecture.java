package ru.codefest.client.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.petriyov.android.libs.contentprovider.CustomContentProvider;

/**
 * lecture info
 */
@DatabaseTable(tableName = Lecture.TABLE_NAME)
public class Lecture extends CodeFestItem {

    public static final int FAVORITE = 1;

    public static final String TABLE_NAME = "Lecture";

    public static final String PERIOD_ID = "periodId";

    public static final String CATEGORY_ID = "categoryId";

    public static final String IS_FAVORITE = "isFavorite";

    @DatabaseField(generatedId = true, columnName = CustomContentProvider.KEY_ID)
    public int id;

    @DatabaseField(index = true)
    public String name;

    @DatabaseField
    public String reporterName;

    @DatabaseField
    public String reporterCompany;

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

    @DatabaseField
    public int isFavorite;

    public Lecture() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Lecture [name=");
        builder.append(name);
        builder.append(", \nreporterName=");
        builder.append(reporterName);
        builder.append(", \nreporterCompany=");
        builder.append(reporterCompany);
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
