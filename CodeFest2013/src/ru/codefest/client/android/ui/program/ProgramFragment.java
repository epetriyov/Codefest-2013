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
        programListView = (ExpandableListView) inflater.inflate(
                R.layout.frag_program, container, false);
        programAdapter = new ProgramAdapter(getActivity());
        programListView.setAdapter(programAdapter);
        programListView.setOnChildClickListener(this);
        ProgramPresenter presenter = new ProgramPresenter(this);
        presenter.initProgramList();
        return programListView;
    }

    @Override
    public void updateProgramList(List<LecturePeriod> lecturePeriods) {
        programAdapter.setPeriods(lecturePeriods);
        programAdapter.notifyDataSetChanged();

    }

}
