package com.edawtech.jiayou.utils.tool;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.utils.tool.unit.DensityUtil;


@SuppressLint("NewApi")
public class ViewSetUtils {

    /**
     * @功能描述 : 调用按钮点击放大缩小动画效果Button click zoom in animation effects
     */
    public static void ButtonClickZoomInAnimation(View view_lay, float numerical) {
        if (view_lay != null) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(ObjectAnimator.ofFloat(view_lay, "scaleX", numerical, 1f),
                    ObjectAnimator.ofFloat(view_lay, "scaleY", numerical, 1f));
            set.setDuration(1 * 200).start();
        }
    }

    /**
     * @功能描述 : 设置按钮点击背景颜色改变动画。
     */
    public static void ButtonClickBackground(View view_lay) {
        if (view_lay != null) {
            view_lay.setBackgroundResource(R.drawable.linerlayout_water_selector);
        }
    }

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param left   左边距
     * @param top    上边距
     * @param right  右边距
     * @param bottom 下边距
     * @return
     */
    public static ViewGroup.LayoutParams setViewMargin(View view, int left, int top, int right, int bottom) {
        if (view == null) {
            return null;
        }
        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        // 获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            // 不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }
        // 设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        return marginParams;
    }

    /**
     * 封装成方法:动态设置margin。
     *
     * @param v 控件
     * @param l 左
     * @param t 上
     * @param r 右
     * @param b 下
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 封装成方法:动态设置height。
     *
     * @param v      控件
     * @param height 高
     */
    public static void setHeight(View v, int height) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.height = height;
            v.requestLayout();
        }
    }

    /**
     * 封装成方法:动态设置Width。
     *
     * @param v      控件
     * @param width  宽
     */
    public static void setWidth(View v, int width) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.width = width;
            v.requestLayout();
        }
    }

    /**
     * 封装成方法:动态设置height--width。
     *
     * @param v      控件
     * @param height 高
     * @param width  宽
     */
    public static void setHeightAndWidth(View v, int height, int width) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.height = height;
            p.width = width;
            v.requestLayout();
        }
    }

    /**
     * 定义函数动态控制listView的高度.
     * 在每次listView的adapter发生变化后，要调用setListViewHeightBasedOnChildren(listView)更新界面.
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;
        // 获取listview的适配器
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // item的总高度
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem == null)
                continue;

            if (listItem instanceof LinearLayout) {
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            } else {
                try {
                    listItem.measure(0, 0);
                    totalHeight += listItem.getMeasuredHeight();
                } catch (NullPointerException e) {
                    // TODO: handle exception
                    totalHeight += DensityUtil.dip2px(MyApplication.getContext(), 80);// 这里自己随便写个大小做容错处理吧
                    // LogOut.e("bobge","NullPointerException");
                }
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取listView的高度.
     */
    public static int getListViewHeight(ListView listView) {
        if (listView == null)
            return 0;
        // 获取listview的适配器
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        // item的总高度
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem == null)
                continue;

            if (listItem instanceof LinearLayout) {
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            } else {
                try {
                    listItem.measure(0, 0);
                    totalHeight += listItem.getMeasuredHeight();
                } catch (NullPointerException e) {
                    totalHeight += DensityUtil.dip2px(MyApplication.getContext(), 80);// 这里自己随便写个大小做容错处理吧
                    // LogOut.e("bobge","NullPointerException");
                }
            }
        }
        return totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }

    /**
     * 定义函数动态控制GridView的高度.
     * 在每次GridView的adapter发生变化后，要调用setGridViewHeightBasedOnChildren(GridView)更新界面.
     */
    public static void setGridViewHeightBasedOnChildren(GridView mygrid, int column) {
        if (mygrid == null)
            return;
        // 获取listview的适配器
        ListAdapter listAdapter = mygrid.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = column;// listView.getNumColumns();
        // item的总高度
        int totalHeight = 0;
        // i每次加3，相当于listAdapter.getCount()小于等于3时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于6时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取GridView的每一个item
            View listItem = listAdapter.getView(i, null, mygrid);
            if (listItem == null)
                continue;

            if (listItem instanceof LinearLayout) {
                listItem.measure(0, 0);
                // 获取item的高度和
                totalHeight += listItem.getMeasuredHeight();
            } else {
                try {
                    listItem.measure(0, 0);
                    // 获取item的高度和
                    totalHeight += listItem.getMeasuredHeight();
                } catch (NullPointerException e) {
                    // TODO: handle exception
                    totalHeight += DensityUtil.dip2px(MyApplication.getContext(), 80);// 这里自己随便写个大小做容错处理吧
                    // LogOut.e("bobge","NullPointerException");
                }
            }
        }
        // 获取GridView的布局参数
        ViewGroup.LayoutParams params = mygrid.getLayoutParams();
        int HSpacing = 0;
        if (listAdapter.getCount() % col == 0) {
            HSpacing = listAdapter.getCount() / col;
        } else {
            HSpacing = listAdapter.getCount() / col + 1;
        }
        // 设置高度
        params.height = totalHeight + (mygrid.getHorizontalSpacing() * (HSpacing - 1));
        // 设置参数
        mygrid.setLayoutParams(params);
    }

    // 将View转化成Bitmap图像
    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = null;
        if (v != null) {
            bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            v.draw(canvas);
        }
        return bitmap;
    }

    // 将View转化成Bitmap图像(有点问题)
    public static Bitmap convertViewToBitmap(View v) {
        Bitmap bitmap = null;
        if (v != null) {
            v.setDrawingCacheEnabled(true);
            v.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.buildDrawingCache();
            bitmap = v.getDrawingCache();
        }
        return bitmap;
    }

}
