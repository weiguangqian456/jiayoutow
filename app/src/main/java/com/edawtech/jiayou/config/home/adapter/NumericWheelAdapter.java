package com.edawtech.jiayou.config.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edawtech.jiayou.area.AbstractWheelTextAdapter;

/**
 * ClassName:      NumericWheelAdapter
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 13:47
 * <p>
 * Description:     Wheel adapter
 */
public class NumericWheelAdapter extends AbstractWheelTextAdapter {

    /** The default min value */
    public static final int DEFAULT_MAX_VALUE = 9;

    /** The default max value */
    private static final int DEFAULT_MIN_VALUE = 0;

    // Values
    private int minValue;
    private int maxValue;

    // format
    private String format;

    private String label;

    /**
     * Constructor
     * @param context the current context
     */
    public NumericWheelAdapter(Context context) {
        this(context, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    /**
     * Constructor
     * @param context the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue) {
        this(context, minValue, maxValue, null);
    }

    /**
     * Constructor
     * @param context the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     * @param format the format string
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue, String format) {
        super(context);

        this.minValue = minValue;
        this.maxValue = maxValue;
        this.format = format;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return format != null ? String.format(format, value) : Integer.toString(value);
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        if (index >= 0 && index < getItemsCount()) {
            if (convertView == null) {
                convertView = getView(itemResourceId, parent);
            }
            TextView textView = getTextView(convertView, itemTextResourceId);
            if (textView != null) {
                textView.setPadding(0,18,0,18);
                CharSequence text = getItemText(index);
                if (text == null) {
                    text = "";
                }
                textView.setText(text+label);

                if (itemResourceId == TEXT_VIEW_ITEM_RESOURCE) {
                    configureTextView(textView);
                    //抱歉，我不想找了
                    textView.setTextColor(Color.parseColor("#000000"));
                }
            }
            return convertView;
        }
        return null;
    }

    public void setLabel(String label) {
        this.label=label;
    }

}

