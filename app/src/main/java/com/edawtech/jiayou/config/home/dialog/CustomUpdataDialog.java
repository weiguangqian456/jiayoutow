package com.edawtech.jiayou.config.home.dialog;

import android.content.Intent;
import android.view.Gravity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.ui.activity.VipMemberActivity;
import com.edawtech.jiayou.ui.activity.VsRechargeActivity;
import com.edawtech.jiayou.utils.tool.VsUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName:      CustomUpdataDialog
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 15:53
 * <p>
 * Description:     自定义的升级弹窗(你以为是更新 其实是会员升级)
 */
public class CustomUpdataDialog extends BaseDefaultDialog {

    @BindView(R.id.iv_image)
    ImageView iv_image;

    private int mDrawableId = R.drawable.imgae_up_member;

    public static CustomUpdataDialog getInstance(){
        return new CustomUpdataDialog();
    }

    public CustomUpdataDialog setDrawableId(int mDrawableId){
        this.mDrawableId = mDrawableId;
        return this;
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_updata_member;
    }

    @Override
    protected void initView() {
        Glide.with(mContext).load(mDrawableId).into(iv_image);
    }

    @OnClick(R.id.iv_up)
    public void toSkipMember(){
        if (VsUtil.isLogin(mContext.getResources().getString(R.string.login_prompt3), mContext)) {
            //升级VIP

//            startActivity(new Intent(mContext, VsRechargeActivity.class));
//            SimBackActivity.launch(mContext, SimBackEnum.RECRUIT_REGION, null);
        }
//        SimBackActivity.launch(mContext, SimBackEnum.RECRUIT_REGION,null);
    }

    @OnClick(R.id.tv_look_member)
    public void toSkipLook(){
        startActivity(new Intent(mContext, VipMemberActivity.class));
    }

    @OnClick(R.id.iv_close)
    public void toClose(){
        dismiss();
    }

}

