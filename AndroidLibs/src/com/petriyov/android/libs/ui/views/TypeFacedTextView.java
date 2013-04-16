package com.petriyov.android.libs.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.petriyov.android.libs.AndroidLib;
import com.petriyov.android.libs.utils.Typefaces;

public class TypeFacedTextView extends TextView {

    public TypeFacedTextView(Context context) {
        super(context);
    }

    public TypeFacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TypeFacedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        String customFont = attrs.getAttributeValue(AndroidLib.XMLNS, "custom_font");
        if (customFont != null) {
            setTypeface(Typefaces.get(ctx, customFont));
        }
    }
}