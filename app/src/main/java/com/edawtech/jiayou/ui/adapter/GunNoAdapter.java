package com.edawtech.jiayou.ui.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.bean.RefuelDetail;
import com.edawtech.jiayou.config.bean.RefuelDetailBean;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GunNoAdapter extends RecyclerView.Adapter<GunNoAdapter.ItemViewHolder> {

    private Context mContext;
    private List<RefuelDetailBean.RefuelDetaiOilGunNos> mList = new ArrayList<>();
    private RecycleItemClick mRecycleItemClick;

    public List<RefuelDetailBean.RefuelDetaiOilGunNos> getList() {
        return mList;
    }

    public void setList(List<RefuelDetailBean.RefuelDetaiOilGunNos> list) {
        this.mList = list;
    }


    public GunNoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gun_no, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final RefuelDetailBean.RefuelDetaiOilGunNos item = mList.get(position);

        holder.mRtvFiltrateName.setText(item.getGunNo() + "号枪");

        RoundViewDelegate delegate = holder.mRtvFiltrateName.getDelegate();

        if (item.check) {
            holder.mRtvFiltrateName.setTextColor(mContext.getResources().getColor(R.color.public_color_white));
            delegate.setBackgroundColor(mContext.getResources().getColor(R.color.public_color_FF1086FF));
            delegate.setStrokeColor(mContext.getResources().getColor(R.color.transparency));
            delegate.setStrokeWidth(1);
        } else {
            holder.mRtvFiltrateName.setTextColor(mContext.getResources().getColor(R.color.public_color_FF1086FF));
            delegate.setBackgroundColor(mContext.getResources().getColor(R.color.public_color_white));
            delegate.setStrokeColor(mContext.getResources().getColor(R.color.public_color_FF1086FF));
            delegate.setStrokeWidth(1);
        }

        holder.mRtvFiltrateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCheck();
                item.check = true;

                if (mRecycleItemClick != null) {
                    mRecycleItemClick.onItemClick(position);
                }
                notifyDataSetChanged();
            }
        });

    }

    public void setRecycleClickListener(RecycleItemClick mRecycleItemClick) {
        this.mRecycleItemClick = mRecycleItemClick;
    }

    public void cancelCheck() {
        for (RefuelDetailBean.RefuelDetaiOilGunNos address : mList) {
            address.check = false;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rtv_filtrate_name)
        RoundTextView mRtvFiltrateName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
