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

            @Override
            protected Void doInBackground(Void... params) {
                CodeFestTwitterApi twitterApi = new CodeFestTwitterApi(
                        new Transport(new DefaultHttpClient()));
                try {
                    tweets = twitterApi.getTweets(
                            CodeFestTwitterApi.CODEFEST_USER, 10, 1);
                } catch (ClientProtocolException e) {
                    Toast.makeText(fragment.getSherlockActivity(),
                            R.string.error_client_protocol, Toast.LENGTH_LONG)
                            .show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(fragment.getSherlockActivity(),
                            R.string.error_io, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                fragment.getSherlockActivity()
                        .setProgressBarIndeterminateVisibility(false);
                fragment.updateTwitterFeed(tweets);
            };

            @Override
            protected void onPreExecute() {
                fragment.getSherlockActivity()
                        .setProgressBarIndeterminateVisibility(true);
            };
        }.execute();
    }
}
