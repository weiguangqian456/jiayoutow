package com.edawtech.jiayou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.bean.InviteDetailInfo;
import com.edawtech.jiayou.retrofit.SeckillTab;
import com.edawtech.jiayou.utils.StringUtils;

import java.util.List;

/**
 * ClassName:      InviteDetailAdapter
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 14:25
 * <p>
 * Description:     邀请详情适配器
 */
public class InviteDetailAdapter extends RecyclerView.Adapter<InviteDetailAdapter.MyViewHolder> {

    private List<InviteDetailInfo.RecordsBean> recordsList;
    private Context context;
    public InviteDetailAdapter(Context context,List<InviteDetailInfo.RecordsBean> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_detail_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InviteDetailInfo.RecordsBean records = recordsList.get(position);
        holder.tvMoney.setText(String .valueOf( records.getAmountGun()));
        if(!StringUtils.isEmpty(records.getPayTime())) {
            holder.tvTime.setText(records.getPayTime().substring(0,10));
            holder.tvHour.setText(records.getPayTime().substring(10,records.getPayTime().length()));
        }
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvHour;
        private TextView tvMoney;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvHour = (TextView) itemView.findViewById(R.id.tv_hour);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
        }
    }
}
