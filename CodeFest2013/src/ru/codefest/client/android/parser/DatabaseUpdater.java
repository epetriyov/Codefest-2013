package ru.codefest.client.android.parser;

import java.io.IOException;
import java.util.List;

import ru.codefest.client.android.dao.CodeFestDao;
import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import android.content.Context;

import com.petriyov.android.libs.bindings.BinderHelper;

public class DatabaseUpdater {

    private Context context;

    public static final int SUCCESS_UDPATE = 1;

    public static final int ERROR_UDPATE = 2;

    public DatabaseUpdater(Context context) {
        this.context = context;
    }

    /**
     * update database from codefest.ru
     * 
     * @return
     */
    public int updateDatabaseFromSite() {
        CodeFestHtmlParser parser = new CodeFestHtmlParser();
        CodeFestDao dao = new CodeFestDao(context, new BinderHelper());
        try {
            dao.deleteAllEntities();
            List<Category> categoryList = parser
                    .parseCodeFestCategories(CodeFestHtmlParser.CODEFEST_URL);
            dao.bulkInsertItems(categoryList, Category.TABLE_NAME);
            List<LecturePeriod> periodList = parser
                    .parseLecturePeriods(CodeFestHtmlParser.CODEFEST_URL);
            dao.bulkInsertItems(periodList, LecturePeriod.TABLE_NAME);
            List<Lecture> lectureList = parser.parseCodeFestProgram(
                    CodeFestHtmlParser.CODEFEST_URL,
                    dao.getList(Category.class, Category.TABLE_NAME),
                    dao.getList(LecturePeriod.class, LecturePeriod.TABLE_NAME));
            dao.bulkInsertItems(lectureList, Lecture.TABLE_NAME);
            return SUCCESS_UDPATE;
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR_UDPATE;
        }

    }

}
