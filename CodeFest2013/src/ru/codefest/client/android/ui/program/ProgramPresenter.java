package ru.codefest.client.android.ui.program;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.codefest.client.android.dao.CodeFestDao;
import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import ru.codefest.client.android.service.ServiceHelper;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.SparseIntArray;

import com.petriyov.android.libs.bindings.BinderHelper;

public class ProgramPresenter {

    private static class IncomingHandler extends Handler {

        private final WeakReference<ProgramPresenter> presenterRef;

        public IncomingHandler(ProgramPresenter presenter) {
            presenterRef = new WeakReference<ProgramPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            ProgramPresenter presenter = presenterRef.get();
            if (presenter != null) {
                presenter.handleUpdateFavoritesListMessage();
            }
            super.handleMessage(msg);
        }
    }

    private IncomingHandler handler;

    private IProgramFragment fragment;

    private boolean isFavorites;

    public ProgramPresenter(IProgramFragment fragment, boolean isFavorites) {
        this.fragment = fragment;
        this.isFavorites = isFavorites;
        handler = new IncomingHandler(this);
    }

    public void batchFavorite(SparseIntArray favoritesArray) {
        CodeFestDao dao = new CodeFestDao(fragment.getCodeFestActivity(),
                new BinderHelper());
        dao.batchFavorite(favoritesArray);
    }

    public void initProgramList() {
        new AsyncTask<Void, Void, Void>() {

            private List<LecturePeriod> lecturePeriods;

            private boolean hasFavorites;

            @Override
            protected Void doInBackground(Void... params) {
                if (fragment.getCodeFestActivity() != null) {
                    CodeFestDao dao = new CodeFestDao(
                            fragment.getCodeFestActivity(), new BinderHelper());
                    lecturePeriods = dao.getList(LecturePeriod.class,
                            LecturePeriod.TABLE_NAME);
                    hasFavorites = false;
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
                            hasFavorites = true;
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
                    if (lecturePeriods == null || !hasFavorites) {
                        fragment.showNoResults();
                    } else {
                        fragment.updateProgramList(lecturePeriods);
                    }
                }
            }

            ;

            @Override
            protected void onPreExecute() {
                if (fragment.getCodeFestActivity() != null) {
                    fragment.showProgress();
                    fragment.hideNoResults();
                }
            }

            ;
        }.execute();
    }

    public void saveSelection(ProgramAdapter programAdapter) {
        fragment.showProgress();
        SparseIntArray favoriteArray = new SparseIntArray();
        for (int i = 0; i < programAdapter.getCount(); i++) {
            Object object = programAdapter.getItem(i);
            if (object instanceof Lecture) {
                Lecture lecture = (Lecture) object;
                favoriteArray.put(lecture.id, lecture.isFavorite);
            }
        }
        ServiceHelper.batchFavorite(fragment.getCodeFestActivity(), handler,
                favoriteArray);
    }

    private void handleUpdateFavoritesListMessage() {
        fragment.hideProgress();
        if (!isFavorites && fragment.getCodeFestActivity() != null) {
            fragment.getCodeFestActivity().sendUpdateFavoritesListCommand();
        }

    }
}