package ru.codefest.client.android.ui.twitter;

import java.util.List;

import ru.codefest.client.android.model.Tweet;
import ru.codefest.client.android.ui.ICodeFestFragment;

public interface ITwitterFeedFragment extends ICodeFestFragment {

    void updateTwitterFeed(List<Tweet> tweets);

}
