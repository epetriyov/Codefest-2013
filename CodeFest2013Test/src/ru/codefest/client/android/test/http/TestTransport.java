package ru.codefest.client.android.test.http;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;

import ru.codefest.client.android.http.Transport;
import ru.codefest.client.android.test.TestUtils;
import android.test.InstrumentationTestCase;

public class TestTransport extends InstrumentationTestCase {

    public void testMakeHttpGetRequest() {
        Transport transport = new Transport(new DefaultHttpClient());
        try {
            String actualResult = transport
                    .makeHttpGetRequest("http://search.twitter.com/search.json?q=@CodeFestRu&rpp=2&page=1");
            String expectedResult = TestUtils.getTestJsonFeedFromAssets(
                    getInstrumentation().getContext(), "test_transport.json");
            assertEquals(actualResult, expectedResult);
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
            fail("error in the HTTP protocol!");
        } catch (IOException e1) {
            e1.printStackTrace();
            fail("I/O-related error!");
        }
    }
}
