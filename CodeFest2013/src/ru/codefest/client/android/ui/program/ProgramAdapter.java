package ru.codefest.client.android.ui.program;

import ru.codefest.client.android.R;
import android.app.Activity;

import com.petriyov.android.libs.ui.adapters.MultiTypeViewAdapter;

public class ProgramAdapter extends MultiTypeViewAdapter {

    public ProgramAdapter(Activity context) {
        addBinder(R.layout.adt_program, new ProgramBinder(context));
        addBinder(R.layout.adt_category, new CategoryBinder(context));
    }
}
