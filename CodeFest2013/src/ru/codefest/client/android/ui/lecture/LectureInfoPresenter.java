package ru.codefest.client.android.ui.lecture;

import ru.codefest.client.android.dao.LectureDao;
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
                lecture = new LectureDao(activity.getContext(),
                        new BinderHelper()).getLectureById(lectureId);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                activity.setProgressBarIndeterminateVisibility(false);
                activity.showLectureInfo(lecture);
            };

            @Override
            protected void onPreExecute() {
                activity.setProgressBarIndeterminateVisibility(true);
            };
        }.execute();
    }
}
