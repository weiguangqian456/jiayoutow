package com.edawtech.jiayou.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.bean.InviteInfo;
import com.edawtech.jiayou.utils.StringUtils;
import com.edawtech.jiayou.utils.TimeUtils;

import java.util.List;

/**
 * ClassName:      InviteAdapter
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 14:18
 * <p>
 * Description:
 */
public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.MyViewHolder> {

    private List<InviteInfo.DataBean.RecordsBean> recordsList;
    private Context mContent;
    public InviteAdapter(Context mContent, List<InviteInfo.DataBean.RecordsBean> recordsList) {
        this.mContent = mContent;
        this.recordsList = recordsList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final InviteInfo.DataBean. RecordsBean records = recordsList.get(position);
        holder.tvMobile.setText(records.getPhone());
        if(!StringUtils.isEmpty(records.getRegTime() + "")) {
            holder.tvTime.setText(TimeUtils.stampToDate(records.getRegTime()));
        }
        holder.tvPhone.setText(records.getInvitationPhone());
        holder.tvMoney.setText(records.getTotalAmountGun()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMobile;
        private TextView tvTime;
        private TextView tvPhone;
        private TextView tvMoney;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvMobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
        }
    }

    public interface onItemClickListener{
        void onItemClickListener(View v, int position);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(InviteAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
