package ru.codefest.client.android.ui.twitter;

import java.util.List;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Tweet;
import ru.codefest.client.android.ui.CodeFestActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public final class TwitterFeedFragment extends SherlockFragment implements
        ITwitterFeedFragment {

    private ListView twitterFeedListView;

    private TwitterAdapter twitterAdapter;

    private TwitterFeedPresenter presenter;

    private View progressBar;

    private View noResults;

    @Override
    public CodeFestActivity getCodeFestActivity() {
        return (CodeFestActivity) super.getSherlockActivity();
    }

    @Override
    public void hideNoResults() {
        noResults.setVisibility(View.GONE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_twitter, container, false);
        twitterFeedListView = (ListView) view.findViewById(R.id.twitterList);
        progressBar = view.findViewById(R.id.progressBarLayout);
        noResults = view.findViewById(R.id.noResultsLayout);
        twitterAdapter = new TwitterAdapter(getActivity());
        twitterFeedListView.setAdapter(twitterAdapter);
        presenter = new TwitterFeedPresenter(this);
        return view;
    }

    @Override
    public void onResume() {
        presenter.loadTwitterFeed();
        super.onResume();
    }

    @Override
    public void showNoResults() {
        noResults.setVisibility(View.VISIBLE);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateList() {
        // No impementation

    }

    @Override
    public void updateTwitterFeed(List<Tweet> tweets) {
        twitterAdapter.clear();
        for (Tweet tweet : tweets) {
            twitterAdapter.addItem(tweet, R.layout.adt_tweet, true);
        }
        twitterAdapter.notifyDataSetChanged();

    }

}
