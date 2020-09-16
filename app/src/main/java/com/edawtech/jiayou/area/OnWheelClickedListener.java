package com.edawtech.jiayou.area;

/**
 * ClassName:      OnWheelClickedListener
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 11:51
 * <p>
 * Description:
 */
public interface OnWheelClickedListener {
    /**
     * Callback method to be invoked when current item clicked
     * @param wheel the wheel view
     * @param itemIndex the index of clicked item
     */
    void onItemClicked(WheelView wheel, int itemIndex);
}
