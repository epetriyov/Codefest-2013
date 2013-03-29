package com.petriyov.android.libs.ui.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter for multy type lists
 * 
 * Simple usage :<code>
 *    adapter = new MultyTypeAdapter();  
        
      adapter.addBinder(R.layout.row_mail_list, new MyAdapterBinder1(view));
      adapter.addBinder(R.layout.row_mail_separator,new MyAdapterBinder2(view));
      
      adapter.clear();
      
      adapter.addItem(myData, R.layout.row_mail_list, true);
      adapter.addItem(myData, R.layout.row_mail_list, true);
       
      adapter.notifyDataSetChanged();
       </code>
 */
public class MultiTypeViewAdapter extends BaseAdapter {
    private class Item {
        final Object itemData;

        final int itemLayout;

        final boolean isEnable;

        public Item(Object itemData, int itemLayout, boolean isEnable) {
            super();
            this.itemData = itemData;
            this.itemLayout = itemLayout;
            this.isEnable = isEnable;
        }
    }

    private HashMap<Integer, IAdapterBinder> binders = new HashMap<Integer, IAdapterBinder>();

    private List<IAdapterBinder> bindersList = new ArrayList<IAdapterBinder>();

    private List<Item> items = new ArrayList<Item>();

    public void addBinder(int layout, IAdapterBinder binder) {
        IAdapterBinder oldBinder = binders.get(layout);
        if (oldBinder != null) {
            this.bindersList.remove(oldBinder);
        }
        this.bindersList.add(binder);
        this.binders.put(layout, binder);
    }

    public void addItem(Object data, int layout, boolean isEnable) {
        this.items.add(new Item(data, layout, isEnable));
    }

    public void addItemAtPosition(int position, Object data, int layout,
            boolean isEnable) {
        int index = position;
        if (index >= items.size()) {
            index = items.size() - 1;
        }
        if (index < 0) {
            index = 0;
        }
        this.items.add(index, new Item(data, layout, isEnable));
    }

    public void clear() {
        this.items.clear();
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        if (items != null && items.size() > position && position >= 0) {
            Item item = this.items.get(position);
            if (item != null) {
                return item.itemData;
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    };

    @Override
    public int getItemViewType(int position) {
        if (items != null && items.size() > position && position >= 0) {
            Item item = this.items.get(position);
            if (item != null) {
                IAdapterBinder binder = binders.get(item.itemLayout);
                return bindersList.indexOf(binder);
            }
        }
        return IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int viewType = getItemViewType(position);
        if (viewType == IGNORE_ITEM_VIEW_TYPE) {
            throw new IllegalStateException("Failed to get object at position "
                    + position);
        }

        if (!(items != null && items.size() > position && position >= 0)) {
            throw new IllegalStateException("Failed to get object at position "
                    + position + (items != null ? " from " + items.size() : ""));
        }

        View convertedView = convertView;
        Item item = this.items.get(position);
        if (item != null) {
            convertedView = getViewByType(position, convertedView, parent,
                    item.itemLayout, item.itemData);
        } else {
            throw new IllegalStateException("Failed to get object at position "
                    + position);
        }
        return convertedView;

    }

    @Override
    public int getViewTypeCount() {
        return Math.max(1, this.binders.size());
    }

    public void insertItem(int position, Object data, int layout,
            boolean isEnable) {
        addItemAtPosition(position, data, layout, isEnable);
    }

    @Override
    public boolean isEnabled(int position) {
        if (items != null && items.size() > position && position >= 0) {
            Item item = this.items.get(position);
            if (item != null) {
                return item.isEnable;
            }
        }
        return false;
    }

    public void remove(int position) {
        this.items.remove(position);
    }

    protected View getViewByType(int position, View convertView,
            ViewGroup parent, int layout, Object itemData) {
        View convertedView = convertView;
        IAdapterBinder binder = this.binders.get(layout);
        if (binder != null) {
            convertedView = binder.bindData(convertedView, itemData);
        } else {
            throw new IllegalArgumentException("Binder fo layout " + layout
                    + " no setted");
        }
        return convertedView;
    };
}