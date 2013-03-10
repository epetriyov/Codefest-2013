package ru.codefest.client.android.http;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

public class Transport {

    private HttpClient httpClient;

    public Transport(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String makeHttpGetRequest(String url)
            throws ClientProtocolException, IOException {
        String responseBody = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpClient.execute(httpGet, responseHandler);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return responseBody;
    }

}
