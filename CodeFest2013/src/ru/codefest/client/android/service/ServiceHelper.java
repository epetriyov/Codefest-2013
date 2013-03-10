package ru.codefest.client.android.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Messenger;

public class ServiceHelper {

    public static final String REFRESH_COMMAND = "refresh";

    public static final String COMMAND_INTENT_NAME = "commandName";

    public static final String MESSENGER_INTENT_NAME = "messengerName";

    public static void refreshProgram(Context context, Handler handler) {
        Intent refreshIntent = new Intent(context, CodeFestService.class);
        refreshIntent.putExtra(COMMAND_INTENT_NAME, REFRESH_COMMAND);
        refreshIntent.putExtra(MESSENGER_INTENT_NAME, new Messenger(handler));
        context.startService(refreshIntent);
    }

}
