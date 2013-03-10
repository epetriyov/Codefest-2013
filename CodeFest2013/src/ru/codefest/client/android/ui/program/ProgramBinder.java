package ru.codefest.client.android.ui.program;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Lecture;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.petriyov.android.libs.ui.adapters.AdapterBinder;

public class ProgramBinder extends AdapterBinder {
    static class ViewHolder {
        protected TextView lectureName;

        protected TextView lecturerInfo;

    }

    private Activity context;

    public ProgramBinder(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public View bindData(View convertView, Object itemData) {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            viewHolder = new ViewHolder();
            convertView = inflator.inflate(R.layout.adt_program, null);
            viewHolder.lectureName = (TextView) convertView
                    .findViewById(R.id.lectureName);
            viewHolder.lecturerInfo = (TextView) convertView
                    .findViewById(R.id.lecturerInfo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Lecture currentLecture = (Lecture) itemData;
        viewHolder.lectureName.setText(currentLecture.name);
        viewHolder.lecturerInfo.setText(currentLecture.reporterInfo);
        return convertView;
    }

}
