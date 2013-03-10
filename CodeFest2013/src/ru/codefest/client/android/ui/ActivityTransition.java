package ru.codefest.client.android.ui;

import ru.codefest.client.android.ui.lecture.LectureInfoActivity;
import android.content.Context;
import android.content.Intent;

public class ActivityTransition {

    public static void openLectureInfo(Context context, int lectureId) {
        Intent lectureInfoIntent = new Intent(context,
                LectureInfoActivity.class);
        lectureInfoIntent.putExtra(LectureInfoActivity.LECTURE_ID_EXTRA,
                lectureId);
        context.startActivity(lectureInfoIntent);
    }
}
