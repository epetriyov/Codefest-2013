package ru.codefest.client.android;

import android.app.Application;

public class CodeFestApplication extends Application {

    private static CodeFestApplication instance;

    public static CodeFestApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ImageLoaderSingleton.initImageLoader(getApplicationContext());
    }

}
