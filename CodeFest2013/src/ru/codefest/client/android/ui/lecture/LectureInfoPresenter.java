package ru.codefest.client.android.ui.lecture;

import ru.codefest.client.android.R;
import ru.codefest.client.android.dao.CodeFestDao;
import ru.codefest.client.android.model.Lecture;
import android.os.AsyncTask;

import com.petriyov.android.libs.bindings.BinderHelper;

public class LectureInfoPresenter {

    private ILectureInfoActivity activity;

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
                StringBuilder htmlBuilder = new StringBuilder();
                htmlBuilder
                        .append("<!DOCTYPE HTML> <HTML> <BODY> <center> <h3>");
                htmlBuilder.append(lecture.name);
                htmlBuilder.append("</h3> </center>");
                htmlBuilder.append(lecture.lectureDescription);
                htmlBuilder.append("<center> <h4>");
                htmlBuilder.append(activity.getContext().getString(
                        R.string.about_reporter));
                htmlBuilder.append("</h4> </center>");
                htmlBuilder.append("<img src=\"");
                htmlBuilder.append(lecture.reporterPhotoUrl);
                htmlBuilder.append("\" width = \"100%\" height = \"auto\"/>");
                htmlBuilder.append("<center>");
                htmlBuilder.append(lecture.reporterName);
                htmlBuilder.append(", ");
                htmlBuilder.append(lecture.reporterCompany);
                htmlBuilder.append("</center>");
                htmlBuilder.append(lecture.reporterDescription);
                htmlBuilder.append("</BODY> </HTML>");
                activity.showLectureInfo(htmlBuilder.toString());
            };

            @Override
            protected void onPreExecute() {
                activity.setProgressBarIndeterminateVisibility(true);
            };
        }.execute();
    }
}
