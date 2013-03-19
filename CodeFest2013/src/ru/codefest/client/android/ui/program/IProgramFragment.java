package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.model.LecturePeriod;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public interface IProgramFragment {

    SherlockFragmentActivity getSherlockActivity();

    void updateProgramList(List<LecturePeriod> lecturePeriods);

}
