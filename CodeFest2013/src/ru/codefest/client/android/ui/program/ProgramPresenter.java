package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.dao.LectureDao;
import ru.codefest.client.android.model.Lecture;
import android.os.AsyncTask;

import com.petriyov.android.libs.bindings.BinderHelper;

public class ProgramPresenter {

    private IProgramFragment fragment;

    public ProgramPresenter(IProgramFragment fragment) {
        this.fragment = fragment;
    }

    public void initProgramList() {
        new AsyncTask<Void, Void, Void>() {

            private List<Lecture> lectures;

            @Override
            protected Void doInBackground(Void... params) {
                lectures = new LectureDao(fragment.getSherlockActivity(),
                        new BinderHelper()).getLectureList();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                fragment.getSherlockActivity()
                        .setProgressBarIndeterminateVisibility(false);
                fragment.updateProgramList(lectures);
            };

            @Override
            protected void onPreExecute() {
                fragment.getSherlockActivity()
                        .setProgressBarIndeterminateVisibility(true);
            };
        }.execute();
    }
}