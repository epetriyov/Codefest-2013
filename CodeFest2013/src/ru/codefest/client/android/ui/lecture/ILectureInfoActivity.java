package ru.codefest.client.android.ui.lecture;

import ru.codefest.client.android.model.Lecture;
import android.content.Context;

public interface ILectureInfoActivity {

    Context getContext();

    void setProgressBarIndeterminateVisibility(boolean b);

    void showLectureInfo(Lecture lecture);

}
