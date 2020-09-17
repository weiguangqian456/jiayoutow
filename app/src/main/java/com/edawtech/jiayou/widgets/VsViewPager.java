package com.edawtech.jiayou.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class VsViewPager extends ViewPager {

    private static final String TAG = VsViewPager.class.getSimpleName();
    private boolean isTouch = false;


    public VsViewPager(Context context) {
        this(context, null);
    }

    public VsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isCanSnap() {

        return !isTouch;
    }

    public void snapToNextScreen() {
        int i = getChildCount();
        if (i >= 2) {
            int index = getCurrentItem();
//            index = (index + 1) % i;
            setCurrentItem(index + 1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
        }

        return super.onTouchEvent(event);
    }



}
