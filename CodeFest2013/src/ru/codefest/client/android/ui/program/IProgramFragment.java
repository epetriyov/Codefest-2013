package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.model.LecturePeriod;
import ru.codefest.client.android.ui.ICodeFestFragment;

public interface IProgramFragment extends ICodeFestFragment {

    void updateProgramList(List<LecturePeriod> lecturePeriods);

}
