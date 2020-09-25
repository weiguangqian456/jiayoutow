package com.edawtech.jiayou.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpFragment;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.bean.WithdrawRecordInfo;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.ui.adapter.WithdrawRecordAdapter;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.edawtech.jiayou.widgets.MyRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 成长金列表
 */
public class RedListFragment extends BaseMvpFragment {

    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.list)
    MyRecyclerView list;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    //默认页数
    private int page = 1;
    //默认请求数量
    private final int count = 10;
    private PublicPresenter mPresenter;
    private WithdrawRecordAdapter adapter;
    //是否下拉刷新
    private boolean isRefresh = false;
    //是否上拉加载
    private boolean isLoadMore = false;
    private List<WithdrawRecordInfo.DataBean.RecordsBean> mData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_red_list;
    }

    @Override
    protected void initView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        mPresenter = new PublicPresenter(getContext(), true, "加载中...");
        mPresenter.attachView(this);
        initAdapter();
        //获取成长金列表
        getRedList();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new WithdrawRecordAdapter(getContext(), mData);
        }
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        //下拉刷新，上拉加载更多
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mPresenter.setShowLoadingDialog(false);
                isRefresh = true;
                page = 1;
                mData.clear();
                getRedList();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (mData.size() > 0 && mData.size()%10 == 0) {
                    mPresenter.setShowLoadingDialog(false);
                    isLoadMore = true;
                    page++;
                    getRedList();
                } else {
                    ToastUtil.showMsg("没有更多数据了");
                    refreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    /**
     * 获取成长金列表
     */
    private void getRedList() {
        Map<String, Object> map = new HashMap<>();
        map.put("appId", CommonParam.APP_ID);
        map.put("uid", MyApplication.UID);
        map.put("page", page);
        map.put("limit", count);
        mPresenter.netWorkRequestGet(CommonParam.GET_USER_RED_LIST, map);
    }

    @Override
    public void onSuccess(String data) {
        LogUtils.i("fxx", "获取成长金提现列表成功   data=" + data);
        WithdrawRecordInfo info = JSON.parseObject(data, WithdrawRecordInfo.class);
        List<WithdrawRecordInfo.DataBean.RecordsBean> bean = info.getData().getRecords();
        if (bean.size() > 0) {
            mData.addAll(bean);
            list.setVisibility(View.VISIBLE);
            rlEmpty.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            if (mData.size() == 0) {
                rlEmpty.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
            }
            if (page > 1) {
                page--;
            }
        }
        if (isRefresh){
            isRefresh = false;
            refreshLayout.finishRefresh();
        }
        if (isLoadMore){
            isLoadMore = false;
            refreshLayout.finishRefreshLoadMore();
        }
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.i("fxx", "获取成长金提现列表失败   code=" + code + "    msg=" + msg + "    isNetWorkError=" + isNetWorkError);
        ToastUtil.showMsg(msg);
        rlEmpty.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        if (page > 1) {
            page--;
        }
        if (isRefresh){
            isRefresh = false;
            refreshLayout.finishRefresh();
        }
        if (isLoadMore){
            isLoadMore = false;
            refreshLayout.finishRefreshLoadMore();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}