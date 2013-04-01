package ru.codefest.client.android.ui;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bugsense.trace.BugSenseHandler;

public class CodeFestBaseActivity extends SherlockFragmentActivity {

    private static final String BUGSENSE_KEY = "e0b76f6d";

    @Override
    protected void onCreate(Bundle arg0) {
        BugSenseHandler.initAndStartSession(CodeFestBaseActivity.this,
                BUGSENSE_KEY);
        super.onCreate(arg0);
    }

}
