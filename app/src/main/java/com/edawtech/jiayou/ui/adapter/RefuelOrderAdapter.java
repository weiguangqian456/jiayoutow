package com.edawtech.jiayou.ui.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.BaseRecycleAdapter;
import com.edawtech.jiayou.config.bean.RefuelOrder;
import com.edawtech.jiayou.utils.tool.ValidClickListener;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;


import butterknife.BindView;
import butterknife.ButterKnife;

public class RefuelOrderAdapter extends BaseRecycleAdapter {

    public RefuelOrderAdapter(Context mContext) {
        super(mContext);
    }

    private RecycleItemClick mRecycleItemClick;

    public void setmRecycleItemClick(RecycleItemClick mRecycleItemClick) {
        this.mRecycleItemClick = mRecycleItemClick;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refuel_order, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    protected void onBindHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder holder = (ItemViewHolder) viewHolder;

        String json = getItem(position).toString();
        final RefuelOrder item = new Gson().fromJson(json, RefuelOrder.class);

        holder.mTvOrderTitle.setText(item.litre + "升 " + item.oilNo + " " + item.gasName);
        holder.mTvOrderPrice.setText("￥" + item.amountPay);
        holder.mTvOrderTime.setText(item.payTime);
        holder.mTvPayStatus.setText(item.orderStatusName);

     //   holder.mRtvOrderStatus.getDelegate().setBackgroundColor(mContext.getResources().getColor("已支付".equals(item.orderStatusName) ? R.color.public_color_00CC8E : R.color.public_color_EC6941));
    //   holder.mTvPayStatus.setTextColor(mContext.getResources().getColor("已支付".equals(item.orderStatusName) ? R.color.public_color_00CC8E : R.color.public_color_EC6941));

        holder.itemView.setOnClickListener(new ValidClickListener() {
            @Override
            public void onValidClick() {
                if (mRecycleItemClick != null) {
                    mRecycleItemClick.onItemClick(position);
                }
            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rtv_order_status)
        RoundTextView mRtvOrderStatus;
        @BindView(R.id.tv_order_title)
        TextView mTvOrderTitle;
        @BindView(R.id.tv_order_price)
        TextView mTvOrderPrice;
        @BindView(R.id.tv_order_time)
        TextView mTvOrderTime;
        @BindView(R.id.tv_pay_status)
        TextView mTvPayStatus;

        private ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
