package com.edawtech.jiayou.ui.adapter;

import android.content.Context;
import android.graphics.Paint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseRecycleAdapter;
import com.edawtech.jiayou.config.bean.RefuelList;
import com.edawtech.jiayou.utils.tool.ArmsUtils;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreRefuelAdapter extends BaseRecycleAdapter {

    public MoreRefuelAdapter(Context mContext) {
        super(mContext);
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(View view, int position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_refuel, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    protected void onBindHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder holder = (ItemViewHolder) viewHolder;

        String json = getItem(position).toString();
        final RefuelList item = new Gson().fromJson(json, RefuelList.class);

        holder.mTvInternationalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.mTvInternationalPrice.getPaint().setAntiAlias(true);//抗锯齿

        holder.mTvOilStationName.setText(item.gasName);
        holder.mTvOilStationAddress.setText(item.gasAddress);
        holder.mTvDepreciate.setText(item.distance + "");
        holder.mTvInternationalPrice.setText("国标价￥" + item.priceOfficial);
        holder.mTvDepreciate.setText("已降￥" + ArmsUtils.formatting(item.priceOfficial > item.priceYfq ? (item.priceOfficial - item.priceYfq) : 0.00));
        holder.mTvNavigation.setText(item.distance + "km");
        holder.mTvOilPrice.setText(item.priceYfq + "");

        holder.mTvNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v, position);
                }
            }
        });
        holder.mRllItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v, position);
                }
            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_oil_station_name)
        TextView mTvOilStationName;
        @BindView(R.id.tv_oil_station_address)
        TextView mTvOilStationAddress;
        @BindView(R.id.tv_oil_price)
        TextView mTvOilPrice;
        @BindView(R.id.tv_depreciate)
        RoundTextView mTvDepreciate;
        @BindView(R.id.tv_international_price)
        TextView mTvInternationalPrice;
        @BindView(R.id.tv_navigation)
        TextView mTvNavigation;
        @BindView(R.id.rll_item)
        RoundLinearLayout mRllItem;

        private ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
