package com.edawtech.jiayou.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.bean.WithdrawRecordInfo;

import java.util.List;

/**
 * ClassName:      WithdrawRecordAdapter
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/22 15:13
 * <p>
 * Description:     成长金提现记录适配器
 */
public class WithdrawRecordAdapter extends RecyclerView.Adapter<WithdrawRecordAdapter.MyHolder> {

    private Context context;
    private List<WithdrawRecordInfo.DataBean.RecordsBean> data;

    public WithdrawRecordAdapter(Context context, List<WithdrawRecordInfo.DataBean.RecordsBean> data) {
        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consumption, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        WithdrawRecordInfo.DataBean.RecordsBean info = data.get(position);
        String remark = info.getRemark();
        int amount = info.getAmount();
        String ctime = (String) info.getCtime();
        int status = info.getStatus();

        if (!TextUtils.isEmpty(remark) && remark.contains("用户充值")) {
            int index = remark.indexOf("用户充值");
            remark = remark.substring(0, index + 4);
        }
        holder.remark.setText(remark);
        holder.time.setText(ctime);
        holder.amount.setText(String.valueOf(amount));
        switch (status) {
            case 0:
                holder.status.setText("未处理");
                break;
            case 1:
                holder.status.setText("处理中");
                break;
            case 2:
                holder.status.setText("提现成功");
                break;
            case 3:
                holder.status.setText("提现失败");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView remark;
        private TextView amount;
        private TextView time;
        private TextView status;
        private RelativeLayout bottomRl;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            remark = (TextView) itemView.findViewById(R.id.tv_name);
            amount = (TextView) itemView.findViewById(R.id.tv_amount);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            status = (TextView) itemView.findViewById(R.id.tv_status);
            bottomRl = (RelativeLayout) itemView.findViewById(R.id.rl_bottom);
        }
    }
}
