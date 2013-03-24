package ru.codefest.client.android.ui.lecture;

import android.content.Context;

public interface ILectureInfoActivity {

    Context getContext();

    void setProgressBarIndeterminateVisibility(boolean b);

    void showLectureInfo(String lectureDescription);

}
