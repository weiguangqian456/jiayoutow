package com.edawtech.jiayou.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.bean.InviteDetailInfo;
import com.edawtech.jiayou.config.bean.InviteInfo;
import com.edawtech.jiayou.config.home.dialog.CustomProgressDialog;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.retrofit.RetrofitUtils;
import com.edawtech.jiayou.retrofit.SeckillTab;
import com.edawtech.jiayou.ui.adapter.InviteDetailAdapter;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.FitStateUtils;
import com.edawtech.jiayou.utils.tool.LogUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;
import com.edawtech.jiayou.widgets.SimpleDividerDecoration;
import com.luck.picture.lib.decoration.WrapContentLinearLayoutManager;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 单个被邀请人加油明细
 */
public class InviteDetailActivity extends BaseMvpActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    //页数
    private int page = 1;
    //默认请求条数
    private final int pageSize = 10;
    //被邀请人手机号
    private String phone;
    //是否下拉刷新
    private boolean isRefresh = false;
    //是否加载更多
    private boolean isLoadMore = false;
    private PublicPresenter mPresenter;
    private InviteDetailAdapter adapter;
    private List<InviteDetailInfo.RecordsBean> recordsList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        FitStateUtils.setImmersionStateMode(this, R.color.activity_title_color);
        mPresenter = new PublicPresenter(this, true, "加载中...");
        mPresenter.attachView(this);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        LogUtils.e("fxx", "手机=" + phone);
        tvTitle.setText("加油明细");
        initAdapter();
        //获取列表
        getInviteDetail();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new InviteDetailAdapter(getApplicationContext(), recordsList);
        }
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SimpleDividerDecoration(this));
        recyclerView.setAdapter(adapter);

        //下拉刷新  加载更多
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mPresenter.setShowLoadingDialog(false);
                page = 1;
                isRefresh = true;
                getInviteDetail();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (recordsList.size() > 0 && recordsList.size() % 10 == 0) {
                    mPresenter.setShowLoadingDialog(false);
                    isLoadMore = true;
                    page++;
                    getInviteDetail();
                } else {
                    ToastUtil.showMsg("没有更多数据了");
                    refreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    /**
     * 获取邀请用户加油明细列表
     */
    private void getInviteDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
//        map.put("phone", phone);
        map.put("phone", "13612810111");
        map.put("limit", pageSize);
        mPresenter.netWorkRequestGet(CommonParam.GET_INVITE_USER_CHEER_DETAILS, map);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(String data) {
        LogUtils.i("fxx", "邀请用户加油明细成功    data=" + data);
        InviteDetailInfo info = JSON.parseObject(data, InviteDetailInfo.class);
        List<InviteDetailInfo.RecordsBean> list = info.getRecords();
        if (list.size() > 0) {
            recordsList.addAll(list);
            adapter.notifyDataSetChanged();
        }
        if (recordsList.size() > 0) {
            rlEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            rlEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        if (isLoadMore) {
            page--;
            isLoadMore = false;
            refreshLayout.finishRefreshLoadMore();
            ToastUtil.showMsg("没有更多数据了");
        }
        if (isRefresh) {
            isRefresh = false;
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.e("fxx", "邀请用户加油明细失败    code=" + code + "    msg=" + msg + "    isNetWorkError=" + isNetWorkError);
        if (isLoadMore) {
            page--;
            isLoadMore = false;
            refreshLayout.finishRefreshLoadMore();
        }
        if (isRefresh) {
            isRefresh = false;
            refreshLayout.finishRefresh();
        }
        ToastUtil.showMsg(msg);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
