package ru.codefest.client.android.test.twitter;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.mockito.Mockito;

import ru.codefest.client.android.http.Transport;
import ru.codefest.client.android.model.Tweet;
import ru.codefest.client.android.test.TestUtils;
import ru.codefest.client.android.twitter.CodeFestTwitterApi;
import android.test.InstrumentationTestCase;

public class TestCodeFestTwitterApi extends InstrumentationTestCase {

    public void testParseJsonTwitterFeed() {
        Transport mockedTransport = Mockito.mock(Transport.class);
        try {
            Mockito.when(
                    mockedTransport.makeHttpGetRequest(Mockito.anyString()))
                    .thenReturn(
                            TestUtils.getTestJsonFeedFromAssets(
                                    getInstrumentation().getContext(),
                                    "test_twitter_feed.json"));
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
            fail("error in the HTTP protocol!");
        } catch (IOException e1) {
            e1.printStackTrace();
            fail("I/O-related error!");
        }
        CodeFestTwitterApi twitterApi = new CodeFestTwitterApi(mockedTransport);
        try {
            List<Tweet> tweetsList = twitterApi.getTweets("CodeFestRu", 100, 1);
            assertEquals(tweetsList.size(), 1);
            assertEquals(tweetsList.get(0).userName, "illbullet");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            fail("error in the HTTP protocol!");
        } catch (IOException e) {
            e.printStackTrace();
            fail("I/O-related error!");
        } catch (JSONException e) {
            e.printStackTrace();
            fail("JSONException!");
        }
    }
}
