package com.edawtech.jiayou.ui.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * ClassName:      MyPageAdapter
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/22 11:17
 * <p>
 * Description:     ViewPager 适配器
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private String [] titles;
    private Fragment [] fs;

    public MyPagerAdapter(FragmentManager fm, String[] titles, Fragment [] fs) {
        super(fm);
        this.titles = titles;
        this.fs = fs;
    }

    @Override
    public Fragment getItem(int i) {
        return fs[i];
    }

    @Override
    public int getCount() {
        return fs == null ? 0 : fs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
