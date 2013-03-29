package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.dao.CodeFestDao;
import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import android.os.AsyncTask;
import android.util.SparseIntArray;

import com.petriyov.android.libs.bindings.BinderHelper;

public class ProgramPresenter {

    private IProgramFragment fragment;

    private boolean isFavorites;

    public ProgramPresenter(IProgramFragment fragment, boolean isFavorites) {
        this.fragment = fragment;
        this.isFavorites = isFavorites;
    }

    public void batchFavorite(SparseIntArray favoritesArray) {
        CodeFestDao dao = new CodeFestDao(fragment.getCodeFestActivity(),
                new BinderHelper());
        dao.batchFavorite(favoritesArray);
    }

    public void initProgramList() {
        new AsyncTask<Void, Void, Void>() {

            private List<LecturePeriod> lecturePeriods;

            @Override
            protected Void doInBackground(Void... params) {
                if (fragment.getCodeFestActivity() != null) {
                    CodeFestDao dao = new CodeFestDao(
                            fragment.getCodeFestActivity(), new BinderHelper());
                    lecturePeriods = dao.getList(LecturePeriod.class,
                            LecturePeriod.TABLE_NAME);
                    for (LecturePeriod period : lecturePeriods) {
                        List<Lecture> lecturesList = null;
                        if (isFavorites) {
                            lecturesList = dao
                                    .getFavoritesByPeriodId(period.id);
                        } else {
                            lecturesList = dao.getLecturesByPeriodId(period.id);
                        }
                        if (lecturesList != null) {
                            for (Lecture lecture : lecturesList) {
                                Category category = dao
                                        .getCategoryById(lecture.categoryId);
                                lecture.categoryName = category.name;
                                lecture.categoryColor = category.color;
                            }

                        }
                        period.setLectureList(lecturesList);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (fragment.getCodeFestActivity() != null) {
                    fragment.hideProgress();
                    fragment.updateProgramList(lecturePeriods);
                }
            };

            @Override
            protected void onPreExecute() {
                if (fragment.getCodeFestActivity() != null) {
                    fragment.showProgress();
                }
            };
        }.execute();
    }
}