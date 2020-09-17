package com.edawtech.jiayou.area;

/**
 * ClassName:      OnWheelChangedListener
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 11:45
 * <p>
 * Description:
 */
public interface OnWheelChangedListener {

    /**
     * Callback method to be invoked when current item changed
     * @param wheel the wheel view whose state has changed
     * @param oldValue the old value of current item
     * @param newValue the new value of current item
     */
    void onChanged(WheelView wheel, int oldValue, int newValue);
}
