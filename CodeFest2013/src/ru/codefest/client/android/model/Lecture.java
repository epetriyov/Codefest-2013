package ru.codefest.client.android.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * lecture info
 */
@DatabaseTable(tableName = Lecture.TABLE_NAME)
public class Lecture extends BaseObject implements IStorable {

    public static final String TABLE_NAME = "Lecture";

    @DatabaseField(index = true)
    public String name;

    @DatabaseField
    public String reporterInfo;

    @DatabaseField
    public String reporterPhotoUrl;

    @DatabaseField
    public String descriptionHtml;

    @DatabaseField(index = true)
    public int categoryId;

    @DatabaseField
    public String categoryName;

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
        builder.append(", reporterPhotoUrl=");
        builder.append(reporterPhotoUrl);
        builder.append(", descriptionHtml=");
        builder.append(descriptionHtml);
        builder.append(", categoryId=");
        builder.append(categoryId);
        builder.append(", categoryName=");
        builder.append(categoryName);
        builder.append("]");
        return builder.toString();
    }

}
