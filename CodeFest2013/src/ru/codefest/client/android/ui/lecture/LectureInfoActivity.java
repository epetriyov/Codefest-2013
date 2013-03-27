package ru.codefest.client.android.ui.lecture;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.ui.CodeFestBaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.ShareActionProvider;

public class LectureInfoActivity extends CodeFestBaseActivity implements
        ILectureInfoActivity {

    private WebView webView;

    private LectureInfoPresenter presenter;

    public static final String LECTURE_ID_EXTRA = "lectureId";

    private ShareActionProvider shareProvider;

    private Intent shareIntent;

    private int lectureId;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem actionItem = menu.findItem(R.id.shareItem);
        shareProvider = (ShareActionProvider) actionItem.getActionProvider();
        shareProvider
                .setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        shareProvider.setShareIntent(shareIntent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setShareInfo(Lecture lecture) {
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, lecture.name);
        if (shareProvider != null) {
            shareProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public void showLectureInfo(String lectureDescription) {
        webView.loadDataWithBaseURL(null, lectureDescription, "text/html",
                "UTF-8", null);

    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.act_lect_info);
        webView = (WebView) findViewById(R.id.lectInfoView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new LectureInfoPresenter(this);
        lectureId = getIntent().getIntExtra(LECTURE_ID_EXTRA, -1);
        if (lectureId == -1) {
            lectureId = arg0.getInt(LECTURE_ID_EXTRA);
        }
        presenter.loadLectureInfo(lectureId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LECTURE_ID_EXTRA, lectureId);
    }

}
