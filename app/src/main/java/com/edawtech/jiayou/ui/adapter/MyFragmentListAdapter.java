package com.edawtech.jiayou.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edawtech.jiayou.R;

import java.util.ArrayList;

/**
 * ClassName:      MyFragmentListAdapter
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/18 11:14
 * <p>
 * Description:     我的页面列表适配器
 */
public class MyFragmentListAdapter extends RecyclerView.Adapter<MyFragmentListAdapter.MyHolder> {

    private Context context;
    private ArrayList<Integer> list;
    private String[] titles;

    public MyFragmentListAdapter(Context context, ArrayList<Integer> list,String[] titles) {
        this.context = context;
        this.list = list;
        this.titles = titles;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mine_list_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(titles[position]);
        Glide.with(context).load(list.get(position)).into(holder.icon);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClickListener(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public RelativeLayout layout;
        public ImageView icon;
        public TextView name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }

    public interface ItemClickListener{
        void onClickListener(View v,int position);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
