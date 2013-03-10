package ru.codefest.client.android.ui.lecture;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.ui.CodeFestBaseActivity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

import com.actionbarsherlock.view.Window;

public class LectureInfoActivity extends CodeFestBaseActivity implements
        ILectureInfoActivity {

    private WebView webView;

    private LectureInfoPresenter presenter;

    public static final String LECTURE_ID_EXTRA = "lectureId";

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLectureInfo(Lecture lecture) {
        webView.loadData(lecture.descriptionHtml, "'text/html'", "UTF-8");

    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.act_lect_info);
        webView = (WebView) findViewById(R.id.lectInfoView);
        presenter = new LectureInfoPresenter(this);
        presenter
                .loadLectureInfo(getIntent().getIntExtra(LECTURE_ID_EXTRA, -1));
    }

}
