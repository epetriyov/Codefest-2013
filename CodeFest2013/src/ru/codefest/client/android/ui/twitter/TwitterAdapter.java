package ru.codefest.client.android.ui.twitter;

import ru.codefest.client.android.R;
import android.app.Activity;

import com.petriyov.android.libs.ui.adapters.MultiTypeViewAdapter;

public class TwitterAdapter extends MultiTypeViewAdapter {

    public TwitterAdapter(Activity context) {
        addBinder(R.layout.adt_tweet, new TwitterBinder(context));
    }
}
