package com.edawtech.jiayou.config.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder unBinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        // 绑定事件ButterKnife.bind(this)必须在setContentView();之后。
        unBinder = ButterKnife.bind(this, view);

        // 初始化视图
        initView(view, savedInstanceState);

        return view;
    }

    /**
     * 设置布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

}
