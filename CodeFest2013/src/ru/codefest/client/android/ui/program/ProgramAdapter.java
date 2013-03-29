package ru.codefest.client.android.ui.program;

import ru.codefest.client.android.R;
import android.app.Activity;
import android.view.View;

import com.petriyov.android.libs.ui.adapters.MultiTypeViewAdapter;

public class ProgramAdapter extends MultiTypeViewAdapter {

    private Activity context;

    public ProgramAdapter(Activity context) {
        this.context = context;
        addBinder(R.layout.adt_lecture_period, new PeriodBinder(context));
        addBinder(R.layout.adt_lecture, new LectureBinder(context, View.GONE));
    }

    public void setSelectionIconVisibility(int visibility) {
        addBinder(R.layout.adt_lecture, new LectureBinder(context, visibility));
    }

}