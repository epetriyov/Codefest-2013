package ru.codefest.client.android.ui.program;

import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Category;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.petriyov.android.libs.ui.adapters.AdapterBinder;

public class CategoryBinder extends AdapterBinder {
    static class ViewHolder {
        protected TextView categoryName;
    }

    private Activity context;

    public CategoryBinder(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public View bindData(View convertView, Object itemData) {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            viewHolder = new ViewHolder();
            convertView = inflator.inflate(R.layout.adt_category, null);
            viewHolder.categoryName = (TextView) convertView
                    .findViewById(R.id.categoryName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Category currentCategory = (Category) itemData;
        viewHolder.categoryName.setText(currentCategory.name);
        return convertView;
    }

}
