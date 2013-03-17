package ru.codefest.client.android.service;

import java.io.IOException;
import java.util.List;

import ru.codefest.client.android.dao.LectureDao;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.parser.CodeFestHtmlParser;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
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
        if (commandName == ServiceHelper.REFRESH_COMMAND) {
            CodeFestHtmlParser parser = new CodeFestHtmlParser();
            try {
                List<Lecture> lectureList = parser
                        .parseCodeFestProgram(CodeFestHtmlParser.CODEFEST_URL);
                LectureDao dao = new LectureDao(this, new BinderHelper());
                dao.bulkInsertLectures(lectureList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Message msg = Message.obtain();
        Bundle data = new Bundle();
        data.putString("k", "value" + System.currentTimeMillis());
        msg.setData(data);

        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
