package com.edawtech.jiayou.config.bean;

/**
 * ClassName:      UploadBean
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/19 18:56
 * <p>
 * Description:     图片上传成功返回类
 */
public class UploadBean {


    /**
     * data : {"uid":"1","phone":"123456"}
     * code : 0
     * msg : 请求成功
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * uid : 1
         * phone : 123456
         */

        private String uid;
        private String phone;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
