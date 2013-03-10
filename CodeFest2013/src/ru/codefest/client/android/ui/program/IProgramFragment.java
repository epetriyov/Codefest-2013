package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public interface IProgramFragment {

    SherlockFragmentActivity getSherlockActivity();

    void updateProgramList(List<Lecture> lectures, List<Category> categories);

}
