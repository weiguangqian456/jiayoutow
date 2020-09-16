package com.edawtech.jiayou.config.bean;

/**
 * ClassName:      CollectionDataBean
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 16:34
 * <p>
 * Description:     收藏Data层
 *
 */
public class CollectionDataBean {
    private String uid;
    private String appId;
    private Object data;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
