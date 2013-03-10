package ru.codefest.client.android.twitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.codefest.client.android.http.Transport;
import ru.codefest.client.android.model.Tweet;

public class CodeFestTwitterApi {

    private static final String TWITTER_SEARCH_JSON_URL = "http://search.twitter.com/search.json";

    public static final String CODEFEST_USER = "CodeFestRu";

    private static final String TAG_FROM_USER = "from_user";

    private static final String TAG_TEXT = "text";

    private static final String TAG_PROFILE_IMAGE_URL = "profile_image_url";

    private static final String TAG_RESULTS = "results";

    private Transport transport;

    public CodeFestTwitterApi(Transport transport) {
        this.transport = transport;
    }

    public List<Tweet> getTweets(String searchTerm, int limit, int page)
            throws ClientProtocolException, IOException {
        StringBuilder feedUrl = new StringBuilder(TWITTER_SEARCH_JSON_URL);
        feedUrl.append("?q=@");
        feedUrl.append(searchTerm);
        feedUrl.append("&rpp=");
        feedUrl.append(limit);
        feedUrl.append("&page=");
        feedUrl.append(page);
        String searchUrl = feedUrl.toString();
        String jsonResult = transport.makeHttpGetRequest(searchUrl);
        return parseJsonTwitterFeed(jsonResult);
    }

    private List<Tweet> parseJsonTwitterFeed(String json) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            JSONArray jsonArray = (JSONArray) jsonObject.get(TAG_RESULTS);
            JSONObject currentObject = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                currentObject = (JSONObject) jsonArray.get(i);
                Tweet tweet = new Tweet(currentObject.get(TAG_FROM_USER)
                        .toString(), currentObject.get(TAG_TEXT).toString(),
                        currentObject.get(TAG_PROFILE_IMAGE_URL).toString());
                tweets.add(tweet);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            new RuntimeException(e);
        }
        return tweets;

    }
}
