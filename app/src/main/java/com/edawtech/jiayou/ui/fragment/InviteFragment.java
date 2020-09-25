package com.edawtech.jiayou.ui.fragment;

import android.content.Intent;
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
import com.edawtech.jiayou.config.bean.InviteInfo;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.ui.activity.InviteDetailActivity;
import com.edawtech.jiayou.ui.adapter.InviteAdapter;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.SharePreferencesHelper;
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
 * 邀请记录
 */
public class InviteFragment extends BaseMvpFragment {

    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.rv_records)
    MyRecyclerView list;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    //默认页数
    private int page = 1;
    //默认请求数量
    private final int count = 10;
    private PublicPresenter mPresenter;
    private InviteAdapter adapter;
    //是否下拉刷新
    private boolean isRefresh = false;
    //是否加载更多
    private boolean isLoadMore = false;
    private List<InviteInfo.DataBean.RecordsBean> mData = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    protected void initView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        mPresenter = new PublicPresenter(getContext(), true, "加载中...");
        mPresenter.attachView(this);
        initAdapter();
        //获取邀请列表
        getInviteList();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new InviteAdapter(getContext(), mData);
        }
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        //item 点击事件
        adapter.setOnItemClickListener(new InviteAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Intent intent = new Intent(getContext(), InviteDetailActivity.class);
                intent.putExtra("phone", mData.get(position).getActualPhone());
                startActivity(intent);
            }
        });

        //下拉刷新   加载更多
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mPresenter.setShowLoadingDialog(false);
                isRefresh = true;
                page = 1;
                mData.clear();
                getInviteList();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (mData.size() > 0 && mData.size()%10 ==0) {
                    mPresenter.setShowLoadingDialog(false);
                    isLoadMore = true;
                    page++;
                    getInviteList();
                } else {
                    ToastUtil.showMsg("没有更多数据了");
                    refreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    /**
     * 获取邀请列表
     */
    private void getInviteList() {
        SharePreferencesHelper sp = new SharePreferencesHelper(getContext(), CommonParam.SP_NAME);
        int level = (int) sp.getSharePreference("level", -1);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", MyApplication.MOBILE);
        map.put("uid", MyApplication.UID);
        map.put("appId", CommonParam.APP_ID);
        map.put("pageNum", page);
        map.put("pageSize", count);
        map.put("level", level);
        mPresenter.netWorkRequestGet(CommonParam.GET_INVITE_LIST, map);
    }

    @Override
    public void onSuccess(String data) {
        LogUtils.i("fxx", "获取邀请人列表成功       data=" + data);
        InviteInfo info = JSON.parseObject(data, InviteInfo.class);
        List<InviteInfo.DataBean.RecordsBean> bean = info.getData().getRecords();
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
        if (isRefresh) {
            isRefresh = false;
            refreshLayout.finishRefresh();
        }
        if (isLoadMore) {
            isLoadMore = false;
            refreshLayout.finishRefreshLoadMore();
        }
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.i("fxx", "获取邀请人列表失败       code=" + code + "    msg=" + msg + "     isNetWorkError=" + isNetWorkError);
        ToastUtil.showMsg(msg);
        rlEmpty.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        if (page > 1) {
            page--;
        }
        if (isRefresh) {
            isRefresh = false;
            refreshLayout.finishRefresh();
        }
        if (isLoadMore) {
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
