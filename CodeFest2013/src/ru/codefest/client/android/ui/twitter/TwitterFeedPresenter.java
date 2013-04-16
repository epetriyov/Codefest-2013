package ru.codefest.client.android.ui.twitter;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;

import ru.codefest.client.android.R;
import ru.codefest.client.android.http.Transport;
import ru.codefest.client.android.model.Tweet;
import ru.codefest.client.android.twitter.CodeFestTwitterApi;
import android.os.AsyncTask;
import android.widget.Toast;

public class TwitterFeedPresenter {

    private ITwitterFeedFragment fragment;

    public TwitterFeedPresenter(ITwitterFeedFragment fragment) {
        this.fragment = fragment;
    }

    public void loadTwitterFeed() {
        new AsyncTask<Void, Void, Void>() {

            private List<Tweet> tweets;

            private IOException exception;

            @Override
            protected Void doInBackground(Void... params) {
                CodeFestTwitterApi twitterApi = new CodeFestTwitterApi(
                        new Transport(new DefaultHttpClient()));
                try {
                    tweets = twitterApi.getTweets(
                            CodeFestTwitterApi.CODEFEST_USER, 100, 1);
                } catch (ClientProtocolException e) {
                    if (fragment.getCodeFestActivity() != null) {
                        exception = new IOException(fragment
                                .getCodeFestActivity().getString(
                                        R.string.error_client_protocol));
                    }
                    e.printStackTrace();
                } catch (IOException e) {
                    if (fragment.getCodeFestActivity() != null) {
                        exception = new IOException(fragment
                                .getCodeFestActivity().getString(
                                        R.string.error_io));
                    }
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (fragment.getCodeFestActivity() != null) {
                    if (exception != null) {
                        Toast.makeText(fragment.getCodeFestActivity(),
                                exception.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                    fragment.hideProgress();
                    if (tweets == null || tweets.isEmpty()) {
                        fragment.showNoResults();
                    } else {
                        fragment.updateTwitterFeed(tweets);
                    }
                }
            }

            ;

            @Override
            protected void onPreExecute() {
                if (fragment.getCodeFestActivity() != null) {
                    fragment.showProgress();
                    fragment.hideNoResults();
                }
            }

            ;
        }.execute();
    }
}
