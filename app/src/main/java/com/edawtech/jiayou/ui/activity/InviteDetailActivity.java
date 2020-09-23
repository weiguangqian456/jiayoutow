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

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseMvpActivity;
import com.edawtech.jiayou.config.base.TempAppCompatActivity;
import com.edawtech.jiayou.config.home.dialog.CustomProgressDialog;
import com.edawtech.jiayou.mvp.presenter.PublicPresenter;
import com.edawtech.jiayou.retrofit.RetrofitUtils;
import com.edawtech.jiayou.retrofit.SeckillTab;
import com.edawtech.jiayou.ui.adapter.InviteDetailAdapter;
import com.edawtech.jiayou.utils.CommonParam;
import com.edawtech.jiayou.utils.tool.LogUtils;
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
 * 加油明细
 */
public class InviteDetailActivity extends BaseMvpActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int pageSize = 1;
    private Map<String, String> params;
    private String phone;
    private CustomProgressDialog loadingDialog;
    private List<SeckillTab.Records> recordsList = new ArrayList<>();
    private InviteDetailAdapter adapter;
    int lastVisibleItem;
    boolean isLoading = false;
    private PublicPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPresenter = new PublicPresenter(this, true, "加载中...");
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        LogUtils.e("fxx", "手机=" + phone);

        initView();
    }

    private void initView() {
        tvTitle.setText("加油明细");
        params = new HashMap<>();
        loadingDialog = new CustomProgressDialog(this, "正在加载中...", R.drawable.loading_frame);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable());

        iniData();
    }

    private void iniData() {

        params.put("page", pageSize + "");
        params.put("phone", phone);
        if (!this.isFinishing()) {
            loadingDialog.setLoadingDialogShow();
        }
        RetrofitUtils.getInstance().refuelMoney(params).enqueue(new Callback<SeckillTab>() {
            @Override
            public void onResponse(Call<SeckillTab> call, Response<SeckillTab> response) {
                loadingDialog.setLoadingDialogDismiss();
                if (response.body() != null && response.body().records != null) {
                    recordsList.addAll(response.body().records);
                    LogUtils.e("recordsList:", recordsList.toString());
                    if (recordsList.size() > 0) {
                        rlEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        initAdapter();
                    } else {
                        rlEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SeckillTab> call, Throwable t) {
                loadingDialog.setLoadingDialogDismiss();
            }
        });
    }

    private void initAdapter() {
        adapter = new InviteDetailAdapter(getApplicationContext(), recordsList);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SimpleDividerDecoration(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !isLoading) {
                    Log.e("recyclerView", "当前界面已经滑到底了");
                    pageSize++;
                    isLoading = true;
                    iniData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 获取邀请用户加油明细列表
     */
    private void getInviteDetail(){
        Map<String,Object> map = new HashMap<>();
        map.put("page",pageSize);
        map.put("phone",phone);
        mPresenter.netWorkRequestGet(CommonParam.GET_INVITE_USER_CHEER_DETAILS,map);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(String data) {
        LogUtils.i("fxx","邀请用户加油明细成功    data="+data);

    }

    @Override
    public void onFailure(Throwable e, int code, String msg, boolean isNetWorkError) {
        LogUtils.e("fxx","邀请用户加油明细失败    code="+code+"    msg="+msg+"    isNetWorkError="+isNetWorkError);

    }
}
