package ru.codefest.client.android.ui.program;

import java.util.List;

import ru.codefest.client.android.model.LecturePeriod;
import ru.codefest.client.android.ui.CodeFestActivity;

public interface IProgramFragment {

    CodeFestActivity getCodeFestActivity();

    void hideProgress();

    void showProgress();

    void updateProgramList(List<LecturePeriod> lecturePeriods);

}
