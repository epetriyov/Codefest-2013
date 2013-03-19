package ru.codefest.client.android.service;

import ru.codefest.client.android.parser.DatabaseUpdater;
import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

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
            DatabaseUpdater updater = new DatabaseUpdater(this);
            int result = updater.updateDatabaseFromSite();
            if (result == DatabaseUpdater.SUCCESS_UDPATE) {
                Message msg = Message.obtain();
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
