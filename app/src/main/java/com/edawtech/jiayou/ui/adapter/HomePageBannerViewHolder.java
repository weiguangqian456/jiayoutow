package com.edawtech.jiayou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;


import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.Const;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.bean.BannerImageEntity;
import com.edawtech.jiayou.config.constant.VsUserConfig;
import com.edawtech.jiayou.utils.CustomSkipUtils;
import com.edawtech.jiayou.utils.tool.ToastUtil;

/**
 * @author Created by EDZ on
 * 商品详情中图片轮播适配器
 */
public class HomePageBannerViewHolder extends Holder<BannerImageEntity> {

    private ImageView imageView;

    public HomePageBannerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        imageView =itemView.findViewById(R.id.sdv_item_head_img);
    }

    @Override
    public void updateUI(BannerImageEntity data) {
        Glide.with(MyApplication.getContext()).load(Const.BASE_IMAGE_URL + data.getImageUrl()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMsg("点击了轮播图");
            }
        });

    }
}
