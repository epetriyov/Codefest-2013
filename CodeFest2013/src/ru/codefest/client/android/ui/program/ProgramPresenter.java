package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.dao.CodeFestDao;
import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import android.os.AsyncTask;

import com.petriyov.android.libs.bindings.BinderHelper;

public class ProgramPresenter {

    private IProgramFragment fragment;

    public ProgramPresenter(IProgramFragment fragment) {
        this.fragment = fragment;
    }

    public void initProgramList() {
        new AsyncTask<Void, Void, Void>() {

            private List<LecturePeriod> lecturePeriods;

            @Override
            protected Void doInBackground(Void... params) {
                if (fragment.getSherlockActivity() != null) {
                    CodeFestDao dao = new CodeFestDao(
                            fragment.getSherlockActivity(), new BinderHelper());
                    lecturePeriods = dao.getList(LecturePeriod.class,
                            LecturePeriod.TABLE_NAME);
                    for (LecturePeriod period : lecturePeriods) {
                        List<Lecture> lecturesList = dao
                                .getLecturesByPeriodId(period.id);
                        for (Lecture lecture : lecturesList) {
                            Category category = dao
                                    .getCategoryById(lecture.categoryId);
                            lecture.categoryName = category.name;
                            lecture.categoryColor = category.color;
                        }
                        period.setLectureList(lecturesList);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (fragment.getSherlockActivity() != null) {
                    fragment.hideProgress();
                    fragment.updateProgramList(lecturePeriods);
                }
            };

            @Override
            protected void onPreExecute() {
                if (fragment.getSherlockActivity() != null) {
                    fragment.showProgress();
                }
            };
        }.execute();
    }
}
