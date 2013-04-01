package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import ru.codefest.client.android.ui.ActivityTransition;
import ru.codefest.client.android.ui.CodeFestActivity;
import ru.codefest.client.android.ui.view.SherlockListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public final class ProgramFragment extends SherlockFragment implements
        IProgramFragment, OnItemClickListener {

    private SherlockListView programListView;

    private ProgramAdapter programAdapter;

    private View progressBar;

    private View noResults;

    private ProgramPresenter presenter;

    private static final String IS_FAVORITE = "isFavorites";

    public static ProgramFragment newInstance(boolean isFavorites) {
        ProgramFragment fragment = new ProgramFragment();
        Bundle b = new Bundle();
        b.putBoolean(IS_FAVORITE, isFavorites);
        fragment.setArguments(b);
        return fragment;
    }

    private boolean isFavorites;

    @Override
    public final CodeFestActivity getCodeFestActivity() {
        return (CodeFestActivity) super.getSherlockActivity();
    }

    @Override
    public void hideNoResults() {
        noResults.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            isFavorites = args.getBoolean(IS_FAVORITE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_program, container, false);
        programListView = (SherlockListView) view
                .findViewById(R.id.programList);
        progressBar = view.findViewById(R.id.progressBarLayout);
        noResults = view.findViewById(R.id.noResultsLayout);
        programAdapter = new ProgramAdapter(getCodeFestActivity());
        programListView.setAdapter(programAdapter);
        programListView.setOnItemClickListener(this);
        if (!isFavorites) {
            programListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            programListView
                    .setMultiChoiceModeListener(new SherlockListView.MultiChoiceModeListenerCompat() {

                        private boolean isCancelClicked = false;

                        @Override
                        public boolean onActionItemClicked(ActionMode mode,
                                MenuItem item) {
                            if (item.getItemId() == R.id.actionmode_cancel) {
                                presenter.initProgramList();
                                isCancelClicked = true;
                                mode.finish();
                            }
                            return true;
                        }

                        @Override
                        public boolean onCreateActionMode(ActionMode mode,
                                Menu menu) {
                            MenuInflater inflater = mode.getMenuInflater();
                            inflater.inflate(R.menu.context_menu, menu);
                            MenuItem item = menu.findItem(R.id.action_text);
                            View v = item.getActionView();
                            getCodeFestActivity().setViewPagerEnabled(false);
                            if (v instanceof TextView) {
                                ((TextView) v)
                                        .setText(R.string.contextual_selection);
                            }
                            showSelectionIcons();
                            isCancelClicked = false;
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            if (!isCancelClicked) {
                                presenter.saveSelection(programAdapter);
                            }
                            getCodeFestActivity().setViewPagerEnabled(true);
                            hideSelectionIcons();
                        }

                        @Override
                        public void onItemCheckedStateChanged(ActionMode mode,
                                int position, long id, boolean checked) {
                            updateFavoriteSelection(position);
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode,
                                Menu menu) {
                            return false;
                        }
                    });
        }
        presenter = new ProgramPresenter(this, isFavorites);
        presenter.initProgramList();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Lecture lecture = (Lecture) arg0.getItemAtPosition(arg2);
        ActivityTransition.openLectureInfo(getSherlockActivity(), lecture.id);

    }

    @Override
    public void showNoResults() {
        noResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateList() {
        presenter.initProgramList();

    }

    @Override
    public void updateProgramList(List<LecturePeriod> lecturePeriods) {
        programAdapter.clear();
        List<Lecture> lectures = null;
        for (LecturePeriod period : lecturePeriods) {
            lectures = period.getLectureList();
            if (!lectures.isEmpty()) {
                programAdapter.addItem(period, R.layout.adt_lecture_period,
                        false);
                if (lectures != null) {
                    for (Lecture lecture : lectures) {
                        programAdapter.addItem(lecture, R.layout.adt_lecture,
                                true);
                    }
                }
            }
        }
        programAdapter.notifyDataSetChanged();
    }

    protected void clearLecturesSelection() {
        for (int i = 0; i < programAdapter.getCount(); i++) {
            programListView.setItemChecked(i, false);
        }

    }

    private void hideSelectionIcons() {
        programAdapter.setSelectionIconVisibility(View.GONE);
    }

    private void showSelectionIcons() {
        programAdapter.setSelectionIconVisibility(View.VISIBLE);
        programAdapter.notifyDataSetChanged();
    }

    private void updateFavoriteSelection(int position) {
        Object object = programAdapter.getItem(position);
        if (object instanceof Lecture) {
            Lecture lecture = (Lecture) object;
            lecture.isFavorite = Lecture.FAVORITE - lecture.isFavorite;
            programAdapter.remove(position);
            programAdapter.addItemAtPosition(position, lecture,
                    R.layout.adt_lecture, true);
            programAdapter.notifyDataSetChanged();
        }
    }
}
