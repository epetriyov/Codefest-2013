package ru.codefest.client.android.ui.program;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.LecturePeriod;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.petriyov.android.libs.ui.adapters.AdapterBinder;

public class PeriodBinder extends AdapterBinder {
    static class GroupViewHolder {
        protected TextView periodHeader;

    }

    private Activity context;

    public PeriodBinder(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public View bindData(View convertView, Object itemData) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new GroupViewHolder();
            convertView = inflator.inflate(R.layout.adt_lecture_period, null);
            viewHolder.periodHeader = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        LecturePeriod period = (LecturePeriod) itemData;
        StringBuilder groupHeaderBuilder = new StringBuilder();
        groupHeaderBuilder.append(period.dayNumber == 1 ? context
                .getString(R.string.first_day) : context
                .getString(R.string.second_day));
        groupHeaderBuilder.append(" ");
        groupHeaderBuilder.append(period.period);
        viewHolder.periodHeader.setText(groupHeaderBuilder.toString());
        return convertView;
    }

}
