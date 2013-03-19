package ru.codefest.client.android.ui.program;

import java.util.ArrayList;
import java.util.List;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ProgramAdapter extends BaseExpandableListAdapter {

    static class ChildViewHolder {
        protected TextView lectureName;

        protected TextView lecturerInfo;

        protected TextView categoryName;

    }

    static class GroupViewHolder {
        protected TextView periodHeader;

    }

    private Context context;

    private List<LecturePeriod> lecturePeriodsList;

    public ProgramAdapter(Context context) {
        this.context = context;
        lecturePeriodsList = new ArrayList<LecturePeriod>();
    }

    @Override
    public Lecture getChild(int groupPosition, int childPosition) {
        List<Lecture> lecturesList = lecturePeriodsList.get(groupPosition)
                .getLectureList();
        return lecturesList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Lecture> lecturesList = lecturePeriodsList.get(groupPosition)
                .getLectureList();
        return lecturesList.size();

    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View view, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflator = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ChildViewHolder();
            view = inflator.inflate(R.layout.adt_lecture, null);
            viewHolder.lectureName = (TextView) view
                    .findViewById(R.id.lectureName);
            viewHolder.lecturerInfo = (TextView) view
                    .findViewById(R.id.lecturerInfo);
            viewHolder.categoryName = (TextView) view
                    .findViewById(R.id.categoryName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) view.getTag();
        }
        Lecture currentLecture = getChild(groupPosition, childPosition);
        viewHolder.lectureName.setText(currentLecture.name);
        viewHolder.lecturerInfo.setText(currentLecture.reporterInfo);
        viewHolder.categoryName.setText(currentLecture.categoryName);
        viewHolder.categoryName.setBackgroundColor(Color
                .parseColor(currentLecture.categoryColor));
        return view;
    }

    @Override
    public LecturePeriod getGroup(int groupPosition) {
        return lecturePeriodsList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return lecturePeriodsList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
            ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflator = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new GroupViewHolder();
            view = inflator.inflate(R.layout.adt_lecture_period, null);
            viewHolder.periodHeader = (TextView) view;
            view.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) view.getTag();
        }
        LecturePeriod period = getGroup(groupPosition);
        StringBuilder groupHeaderBuilder = new StringBuilder();
        groupHeaderBuilder.append(period.dayNumber == 1 ? context
                .getString(R.string.first_day) : context
                .getString(R.string.second_day));
        groupHeaderBuilder.append(" ");
        groupHeaderBuilder.append(period.period);
        viewHolder.periodHeader.setText(groupHeaderBuilder.toString());
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setPeriods(List<LecturePeriod> lecturePeriods) {
        this.lecturePeriodsList = lecturePeriods;
    }
}
