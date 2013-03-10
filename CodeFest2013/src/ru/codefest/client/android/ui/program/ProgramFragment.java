package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.ui.ActivityTransition;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public final class ProgramFragment extends SherlockFragment implements
        IProgramFragment, OnItemClickListener {

    private ListView programListView;

    private ProgramAdapter programAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        programListView = (ListView) inflater.inflate(
                R.layout.view_abstract_list, container, false);
        programAdapter = new ProgramAdapter(getActivity());
        programListView.setAdapter(programAdapter);
        programListView.setOnItemClickListener(this);
        ProgramPresenter presenter = new ProgramPresenter(this);
        presenter.initProgramList();
        return programListView;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ActivityTransition.openLectureInfo(getSherlockActivity(),
                ((Lecture) programAdapter.getItem(arg2)).getId());
    }

    @Override
    public void updateProgramList(List<Lecture> lectures,
            List<Category> categories) {
        programAdapter.clear();
        if (categories.size() > 0 && lectures.size() > 0) {
            int categoryCounter = 0;
            programAdapter.addItem(categories.get(categoryCounter),
                    R.layout.adt_category, false);
            for (int i = 0; i < lectures.size(); i++) {
                if (i > 0
                        && !lectures.get(i).categoryName.equals(lectures
                                .get(i - 1).categoryName)) {
                    programAdapter.addItem(categories.get(++categoryCounter),
                            R.layout.adt_category, false);
                }
                programAdapter.addItem(lectures.get(i), R.layout.adt_program,
                        true);
            }
        }
        programAdapter.notifyDataSetChanged();

    }

}
