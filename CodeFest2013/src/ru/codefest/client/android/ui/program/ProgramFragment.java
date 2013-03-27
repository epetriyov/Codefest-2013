package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import ru.codefest.client.android.ui.ActivityTransition;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.actionbarsherlock.app.SherlockFragment;

public final class ProgramFragment extends SherlockFragment implements
        IProgramFragment, OnChildClickListener {

    private ExpandableListView programListView;

    private ProgramAdapter programAdapter;

    private View progressBar;

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onChildClick(ExpandableListView arg0, View arg1,
            int groupPosition, int childPosition, long id) {
        LecturePeriod period = programAdapter.getGroup(groupPosition);
        Lecture lecture = period.getLectureList().get(childPosition);
        ActivityTransition.openLectureInfo(getSherlockActivity(), lecture.id);
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_program, container, false);
        programListView = (ExpandableListView) view
                .findViewById(R.id.programList);
        progressBar = view.findViewById(R.id.progressBarLayout);
        programAdapter = new ProgramAdapter(getActivity());
        programListView.setAdapter(programAdapter);
        programListView.setOnChildClickListener(this);
        ProgramPresenter presenter = new ProgramPresenter(this);
        presenter.initProgramList();
        return view;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateProgramList(List<LecturePeriod> lecturePeriods) {
        programAdapter.setPeriods(lecturePeriods);
        int count = programAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            programListView.expandGroup(i);
        }
        programAdapter.notifyDataSetChanged();

    }

}
