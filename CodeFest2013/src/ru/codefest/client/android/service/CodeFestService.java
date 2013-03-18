package ru.codefest.client.android.service;

import java.io.IOException;
import java.util.List;

import ru.codefest.client.android.dao.CodeFestDao;
import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import ru.codefest.client.android.parser.CodeFestHtmlParser;
import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.petriyov.android.libs.bindings.BinderHelper;

public class CodeFestService extends IntentService {

    private static final String CODE_FEST_SERVICE = "CodeFestService";

    public CodeFestService() {
        super(CODE_FEST_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String commandName = intent
                .getStringExtra(ServiceHelper.COMMAND_INTENT_NAME);
        Messenger messenger = (Messenger) intent
                .getParcelableExtra(ServiceHelper.MESSENGER_INTENT_NAME);
        if (commandName.equals(ServiceHelper.REFRESH_COMMAND)) {
            // update db from web-site
            CodeFestHtmlParser parser = new CodeFestHtmlParser();
            CodeFestDao dao = new CodeFestDao(this, new BinderHelper());
            try {
                dao.deleteAllEntities();
                List<Category> categoryList = parser
                        .parseCodeFestCategories(CodeFestHtmlParser.CODEFEST_URL);
                dao.bulkInsertItems(categoryList, Category.TABLE_NAME);
                List<LecturePeriod> periodList = parser
                        .parseLecturePeriods(CodeFestHtmlParser.CODEFEST_URL);
                dao.bulkInsertItems(periodList, LecturePeriod.TABLE_NAME);
                List<Lecture> lectureList = parser.parseCodeFestProgram(
                        CodeFestHtmlParser.CODEFEST_URL, dao.getList(
                                Category.class, Category.TABLE_NAME), dao
                                .getList(LecturePeriod.class,
                                        LecturePeriod.TABLE_NAME));
                dao.bulkInsertItems(lectureList, Lecture.TABLE_NAME);
                Message msg = Message.obtain();
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
