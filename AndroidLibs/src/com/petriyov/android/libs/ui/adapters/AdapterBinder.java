package com.petriyov.android.libs.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class AdapterBinder implements IAdapterBinder {

    private final LayoutInflater mInflater;

    public AdapterBinder(Context context) {
        super();
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public abstract View bindData(View convertView, Object itemData);

    public LayoutInflater getInflater() {
        return this.mInflater;
    }

}
