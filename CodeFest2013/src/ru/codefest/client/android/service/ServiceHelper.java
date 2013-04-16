package ru.codefest.client.android.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Messenger;
import android.util.SparseIntArray;

import com.petriyov.android.libs.sparsearrays.SerializableSparseIntArrayContainer;

public class ServiceHelper {

    public static final String REFRESH_COMMAND = "refresh";

    public static final String FAVORITE_COMMAND = "favorite";

    public static final String COMMAND_INTENT_NAME = "commandName";

    public static final String MESSENGER_INTENT_NAME = "messengerName";

    public static final String FAVORITES_EXTRA = "favoritesExtra";

    public static void batchFavorite(Context context, Handler handler,
                                     SparseIntArray favorites) {
        Intent refreshIntent = new Intent(context, CodeFestService.class);
        refreshIntent.putExtra(COMMAND_INTENT_NAME, FAVORITE_COMMAND);
        refreshIntent.putExtra(MESSENGER_INTENT_NAME, new Messenger(handler));
        refreshIntent.putExtra(FAVORITES_EXTRA,
                new SerializableSparseIntArrayContainer(favorites));
        context.startService(refreshIntent);

    }

    public static void refreshProgram(Context context, Handler handler) {
        Intent refreshIntent = new Intent(context, CodeFestService.class);
        refreshIntent.putExtra(COMMAND_INTENT_NAME, REFRESH_COMMAND);
        refreshIntent.putExtra(MESSENGER_INTENT_NAME, new Messenger(handler));
        context.startService(refreshIntent);
    }

}
