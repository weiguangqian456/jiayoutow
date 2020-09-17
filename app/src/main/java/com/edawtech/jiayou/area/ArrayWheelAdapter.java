package com.edawtech.jiayou.area;

import android.content.Context;

/**
 * ClassName:      ArrayWheelAdapter
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 11:57
 * <p>
 * Description:
 */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {

    // items
    private T items[];

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public ArrayWheelAdapter(Context context, T items[]) {
        super(context);

        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.length) {
            T item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.length;
    }

}
