package com.edawtech.jiayou.config.bean;

/**
 * ClassName:      ShareItemBean
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 15:58
 * <p>
 * Description:
 */
public class ShareItemBean {
    private int imgId;
    private String name;

    public ShareItemBean(int imgId, String name) {
        this.imgId = imgId;
        this.name = name;
    }

    public int getImgId() {

        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

