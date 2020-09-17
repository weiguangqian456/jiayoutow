package com.edawtech.jiayou.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseLazyFragment;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.config.home.dialog.CustomProgressDialog;
import com.edawtech.jiayou.retrofit.RetrofitUtils;
import com.edawtech.jiayou.retrofit.SeckillTab;
import com.edawtech.jiayou.ui.activity.GrowMoneyActivity;
import com.edawtech.jiayou.ui.adapter.InviteAdapter;
import com.edawtech.jiayou.utils.LogUtils;
import com.edawtech.jiayou.widgets.SimpleDividerDecoration;
import com.luck.picture.lib.decoration.WrapContentLinearLayoutManager;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InviteFragment  extends BaseLazyFragment {

    @BindView(R.id.rv_records)
    RecyclerView recyclerView;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    private List<SeckillTab.Records> recordsList = new ArrayList<>();
    private InviteAdapter adapter;
    private CustomProgressDialog loadingDialog;
    /**
     * 加载更多
     */
    private int pageNum = 1;
    private GrowMoneyActivity activity;
    /**
     * 加载更多
     */
    int lastVisibleItem;
    boolean isLoading = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    protected void loadLazyData() {
        activity = (GrowMoneyActivity) getContext();

        loadingDialog = new CustomProgressDialog(getContext(), "正在加载中...", R.drawable.loading_frame);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        initData();
    }

    private void initData() {
        if(!activity.isFinishing()) {
            loadingDialog.setLoadingDialogShow();
        }
        Map<String,String> params = new HashMap<>();
        String phone = VsUserConfig.getDataString(getContext().getApplicationContext(), VsUserConfig.JKey_PhoneNumber);
        String uid = VsUserConfig.getDataString(getActivity(), VsUserConfig.JKey_KcId, "");
        params.put("phone", phone);
        LogUtils.e("phone,",phone);
        LogUtils.e("uid",uid);
        params.put("appId", "dudu");
        params.put("pageNum",pageNum+"");
        params.put("level","1");
        RetrofitUtils.getInstance().inviteNum(params).enqueue(new Callback<SeckillTab>() {
            @Override
            public void onResponse(Call<SeckillTab> call, Response<SeckillTab> response) {
                if(!activity.isFinishing()) {
                    loadingDialog.setLoadingDialogDismiss();
                }
                if(response.body() != null &&response.body().records != null) {
                    recordsList.addAll(response.body().records);
                    if(recordsList.size() > 0) {
                        rlEmpty.setVisibility(View.GONE);
                        initAdapter();
                    }else {
                        rlEmpty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SeckillTab> call, Throwable t) {
                LogUtils.e("出错了",t.getMessage());
                if(!activity.isFinishing()) {
                    loadingDialog.setLoadingDialogDismiss();
                }
            }
        });
    }

    private void initAdapter() {
        adapter = new InviteAdapter(getActivity(),recordsList);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SimpleDividerDecoration(getContext()));
        recyclerView.setAdapter(adapter);
        //       recyclerView.setNestedScrollingEnabled(false);

       /* if(!activity.flag.equals("redbag")) {
            activity. nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    //判断是否滑到的底部
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        Log.e("nestedScrollView", "nestedScrollView已经滑到底了");
                        pageNum++;
                        initData();
                    }
                }
            });
        }*/

       /* if(recordsList.size() < 1) {
            activity.nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }else {
            activity. nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    //判断是否滑到的底部
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        Log.e("nestedScrollView", "nestedScrollView已经滑到底了");
                        pageNum++;
                        initData();
                    }
                }
            });
        }*/
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !isLoading) {
                    Log.e("recyclerView", "当前界面已经滑到底了");
                    pageNum++;
                    isLoading = true;
                    initData();
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

    @Override
    protected void initView(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }
}
