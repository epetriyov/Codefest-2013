package ru.codefest.client.android.ui.program;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Lecture;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.petriyov.android.libs.ui.adapters.AdapterBinder;
import com.petriyov.android.libs.ui.views.VerticalTextView;

public class LectureBinder extends AdapterBinder {

    static class ChildViewHolder {
        protected TextView lectureName;

        protected TextView lecturerInfo;

        protected VerticalTextView categoryName;

        protected ImageView favoriteCheckBox;

    }

    private Activity context;

    private int selectionIconVisibility;

    public LectureBinder(Activity context, int selectionIconVisibility) {
        super(context);
        this.context = context;
        this.selectionIconVisibility = selectionIconVisibility;
    }

    @Override
    public View bindData(View convertView, Object itemData) {
        ChildViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ChildViewHolder();
            convertView = inflator.inflate(R.layout.adt_lecture, null);
            viewHolder.lectureName = (TextView) convertView
                    .findViewById(R.id.lectureName);
            viewHolder.lecturerInfo = (TextView) convertView
                    .findViewById(R.id.lecturerInfo);
            viewHolder.categoryName = (VerticalTextView) convertView
                    .findViewById(R.id.categoryName);
            viewHolder.favoriteCheckBox = (ImageView) convertView
                    .findViewById(R.id.favoriteCheckBox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        Lecture currentLecture = (Lecture) itemData;
        viewHolder.lectureName.setText(currentLecture.name);
        StringBuilder reporterInfoBuilder = new StringBuilder();
        reporterInfoBuilder.append(currentLecture.reporterName);
        reporterInfoBuilder.append(", ");
        reporterInfoBuilder.append(currentLecture.reporterCompany);
        viewHolder.lecturerInfo.setText(reporterInfoBuilder.toString());
        viewHolder.categoryName.setText(currentLecture.categoryName);
        viewHolder.categoryName.setBackgroundColor(Color
                .parseColor(currentLecture.categoryColor));
        if (selectionIconVisibility == View.VISIBLE) {
            viewHolder.favoriteCheckBox.setVisibility(View.VISIBLE);
        } else {
            viewHolder.favoriteCheckBox.setVisibility(View.GONE);
        }
        if (currentLecture.isFavorite == Lecture.FAVORITE) {
            viewHolder.favoriteCheckBox
                    .setImageResource(R.drawable.btn_check_buttonless_on);
        } else {
            viewHolder.favoriteCheckBox
                    .setImageResource(R.drawable.btn_check_buttonless_off);
        }
        return convertView;
    }
}
