package ru.codefest.client.android.ui.lecture;

import ru.codefest.client.android.dao.CodeFestDao;
import ru.codefest.client.android.model.Lecture;
import android.os.AsyncTask;

import com.petriyov.android.libs.bindings.BinderHelper;

public class LectureInfoPresenter {

    private ILectureInfoActivity activity;

    private static final String HTML_PRE_TAGS = " <!DOCTYPE HTML> <HTML> <BODY> <center><h3>";

    private static final String HTML_HEADER_POST_TAGS = "</h3></center>";

    private static final String HTML_REPORTER_PRE_TAGS = "<center><h4>Докладчик - ";

    private static final String HTML_REPORTER_IMAGE_PRE_TAGS = "</h4></center><img src=\"";

    private static final String HTML_REPORTER_IMAGE_POST_TAGS = "\"/><br/><br/>";

    private static final String HTML_POST_TAGS = "</BODY> </HTML>";

    public LectureInfoPresenter(ILectureInfoActivity activity) {
        this.activity = activity;
    }

    public void loadLectureInfo(final int lectureId) {
        new AsyncTask<Void, Void, Void>() {

            Lecture lecture;

            @Override
            protected Void doInBackground(Void... params) {
                lecture = new CodeFestDao(activity.getContext(),
                        new BinderHelper()).getLectureById(lectureId);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                activity.setProgressBarIndeterminateVisibility(false);
                StringBuilder htmlBuilder = new StringBuilder(HTML_PRE_TAGS);
                htmlBuilder.append(lecture.name);
                htmlBuilder.append(HTML_HEADER_POST_TAGS);
                htmlBuilder.append(lecture.lectureDescription);
                htmlBuilder.append(HTML_REPORTER_PRE_TAGS);
                htmlBuilder.append(lecture.reporterName);
                htmlBuilder.append(HTML_REPORTER_IMAGE_PRE_TAGS);
                htmlBuilder.append(lecture.reporterPhotoUrl);
                htmlBuilder.append(HTML_REPORTER_IMAGE_POST_TAGS);
                htmlBuilder.append(lecture.reporterDescription);
                htmlBuilder.append(HTML_POST_TAGS);
                activity.showLectureInfo(htmlBuilder.toString());
            };

            @Override
            protected void onPreExecute() {
                activity.setProgressBarIndeterminateVisibility(true);
            };
        }.execute();
    }
}
