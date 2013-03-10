package ru.codefest.client.android.ui.twitter;

import java.util.List;

import ru.codefest.client.android.model.Tweet;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public interface ITwitterFeedFragment {

    SherlockFragmentActivity getSherlockActivity();

    void updateTwitterFeed(List<Tweet> tweets);

}
