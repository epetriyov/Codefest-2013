package com.petriyov.android.libs.bindings;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.petriyov.android.libs.contentprovider.CustomContentProvider;

public class BinderHelper {

    /**
     * Returns a Single Domain Object from the Cursor's first row. All other
     * rows will be ignored.
     * 
     * @param cursor
     *            returned data from the database query.
     * @param clazz
     *            Domain object Class to put all the data from the cursor
     * @param <T>
     *            Domain Object type of the Class
     * @return T domain object populated with data from the first row in the
     *         Cursor
     */
    public <T> T adaptFromCursor(Cursor cursor, Class<T> clazz) {
        return getSingleObjectValuesFromCursor(cursor, clazz);
    }

    /**
     * convert a Cursor with a multitude of date rows and return a List of
     * instances of Class That is mapped to the corresponding database table
     * that the Cursor has data from
     * 
     * @param cursor
     *            returned data from the database query
     * @param clazz
     *            Domain object Class to put all the data from the cursor
     * @param <T>
     *            Domain Object type
     * @return List<T> returns a list of populated domain objects based on the
     *         cursor data.
     */
    public <T> List<T> adaptListFromCursor(Cursor cursor, Class<T> clazz) {
        if (cursor.getCount() > 0) {
            return getValuesFromCursor(cursor, clazz);
        }
        return new ArrayList<T>();
    }

    public <T> ContentValues[] adaptValuesFromList(List<T> list) {
        List<ContentValues> listValues = new ArrayList<ContentValues>(
                list.size());
        for (T object : list) {
            listValues.add(adaptValuesFromObject(object));
        }
        return listValues.toArray(new ContentValues[listValues.size()]);
    }

    public <T> ContentValues adaptValuesFromObject(T object) {
        ContentValues values = new ContentValues();
        setFieldValues(object, values);
        return values;
    }

    private <T> T getSingleObjectValuesFromCursor(Cursor cursor, Class<T> clazz) {
        T newInstance = null;
        try {
            newInstance = clazz.newInstance();
            setFieldValues(clazz, newInstance, cursor);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return newInstance;
    }

    private Object getValue(Class<?> clazz, Cursor cursor, int columnIndex) {
        if (clazz.getName().endsWith("Integer")
                || clazz.getName().endsWith("int")) {
            return cursor.getInt(columnIndex);
        } else if (clazz.getName().endsWith("Long")) {
            return cursor.getLong(columnIndex);
        } else if (clazz.getName().endsWith("Double")) {
            return cursor.getDouble(columnIndex);
        } else if (clazz.getName().endsWith("Float")) {
            return cursor.getFloat(columnIndex);
        } else if (clazz.getName().endsWith("String")) {
            return cursor.getString(columnIndex);
        }
        return null;
    }

    private <T> List<T> getValuesFromCursor(Cursor cursor, Class<T> clazz) {
        List<T> values = new ArrayList<T>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    T newInstance = getSingleObjectValuesFromCursor(cursor,
                            clazz);
                    values.add(newInstance);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return values;
    }

    private <T> void setFieldValue(Field fieldToSet, T object, Cursor cursor) {
        int columnIndex = cursor.getColumnIndex(fieldToSet.getName().equals(
                "id") ? CustomContentProvider.KEY_ID : fieldToSet.getName());
        fieldToSet.setAccessible(true);
        Class<?> clazz = fieldToSet.getType();
        Object value = getValue(clazz, cursor, columnIndex);
        if (value != null) {
            try {
                fieldToSet.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private <T> void setFieldValues(Class<?> clazz, T object, Cursor cursor) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        // fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())
                    && field.getDeclaredAnnotations().length > 0) {
                setFieldValue(field, object, cursor);
            }
        }
    }

    private <T> void setFieldValues(T object, ContentValues values) {
        Class<?> clazz = object.getClass();
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        // fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        for (Field field : fields) {
            try {
                if (!Modifier.isStatic(field.getModifiers())
                        && field.getDeclaredAnnotations().length > 0) {
                    setValue(field, object, values);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private <T> void setValue(Field fieldToSet, T object, ContentValues values)
            throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = fieldToSet.getType();
        String key = fieldToSet.getName();
        fieldToSet.setAccessible(true);
        if (clazz.getName().endsWith("Integer")
                || clazz.getName().endsWith("int")) {
            if (!key.equals("id")) {
                values.put(key, fieldToSet.getInt(object));
            }
        } else if (clazz.getName().endsWith("Long")) {
            values.put(key, fieldToSet.getLong(object));
        } else if (clazz.getName().endsWith("Double")) {
            values.put(key, fieldToSet.getDouble(object));
        } else if (clazz.getName().endsWith("Float")) {
            values.put(key, fieldToSet.getFloat(object));
        } else if (clazz.getName().endsWith("String")) {
            values.put(key, (String) fieldToSet.get(object));
        }
    }

}
