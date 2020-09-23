package com.edawtech.jiayou.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpFragment;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.bean.InviteInfo;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.ui.activity.InviteDetailActivity;
import com.edawtech.jiayou.ui.adapter.InviteAdapter;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.sp.SharePreferencesHelper;
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

    @BindView(R.id.rv_records)
    MyRecyclerView list;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    //默认页数
    private int page = 1;
    //默认请求数量
    private int count = 10;
    private PublicPresenter mPresenter;
    private InviteAdapter adapter;
    private List<InviteInfo.DataBean.RecordsBean> mData = new ArrayList<>();
    private boolean isShow;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e("fxx", "InviteFragment           setUserVisibleHint          " + isVisibleToUser);
        isShow = isVisibleToUser;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    protected void initView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        LogUtils.e("fxx", "InviteFragment       邀请记录");
        mPresenter = new PublicPresenter(getContext(), true, "加载中...");
        mPresenter.attachView(this);
        EventBus.getDefault().register(this);
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
                intent.putExtra("phone",mData.get(position).getActualPhone());
                startActivity(intent);
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
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 下拉刷新通知回调
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void refreshList(String msg) {
        if (msg.equals("inviteLoadMore")) {
            if (isShow) {
                if (mData.size() > 0) {
                    LogUtils.e("fxx", "邀请人列表加载更多");
                    page++;
                    getInviteList();
                }
            }
        }
    }
}
