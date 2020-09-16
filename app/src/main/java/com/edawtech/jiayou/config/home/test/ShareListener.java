package com.edawtech.jiayou.config.home.test;

import com.edawtech.jiayou.utils.LogUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners;

/**
 * @author : hc
 * @date : 2019/3/22.
 * @description: 实现分享方法  自己实现的话用ShareListener2
 */

public class ShareListener implements SocializeListeners.SnsPostListener{
    @Override
    public void onStart() {

    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
        if(i == 200){
            LogUtils.e("分享成功");
        }else{
            LogUtils.e("分享失败：" + i);
        }
    }
}
