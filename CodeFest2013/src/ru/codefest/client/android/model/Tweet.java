package ru.codefest.client.android.model;

public class Tweet {
    public String userName;

    public String message;

    public String imageUrl;

    public Tweet(String username, String message, String url) {
        this.userName = username;
        this.message = message;
        this.imageUrl = url;
    }
}
