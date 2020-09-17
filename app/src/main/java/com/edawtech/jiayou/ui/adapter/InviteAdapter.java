package com.edawtech.jiayou.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.retrofit.SeckillTab;
import com.edawtech.jiayou.ui.activity.InviteDetailActivity;
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

    private List<SeckillTab.Records> recordsList;
    private Activity mContent;
    public InviteAdapter(Activity mContent,List<SeckillTab.Records> recordsList) {
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
        final SeckillTab.Records records = recordsList.get(position);
        holder.tvMobile.setText(records.phone);
        if(!StringUtils.isEmpty(records.regTime + "")) {
            holder.tvTime.setText(TimeUtils.stampToDate(records.regTime));
        }
        holder.tvPhone.setText(records.invitationPhone);
        holder.tvMoney.setText(records.totalAmountGun+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContent, InviteDetailActivity.class);
                intent.putExtra("phone",records.actualPhone);
                mContent.startActivity(intent);
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
}
