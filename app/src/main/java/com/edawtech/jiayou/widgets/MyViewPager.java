package com.edawtech.jiayou.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;


import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * ClassName:      MyViewPager
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/22 16:39
 * <p>
 * Description:     自适应高度ViewPager
 */
public class MyViewPager extends ViewPager {

    private int position = 0;

    private HashMap<Integer, Integer> maps = new LinkedHashMap<Integer, Integer>();

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            maps.put(i, h);
        }
        if (getChildCount() > 0) {
            height = getChildAt(position).getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 在切换tab的时候，重置viewPager的高度
     */
    public void resetHeight(int position) {
        this.position = position;
        if (maps.size() > position) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, maps.get(position));
            } else {
                layoutParams.height = maps.get(position);
            }
            setLayoutParams(layoutParams);
        }
    }
}
