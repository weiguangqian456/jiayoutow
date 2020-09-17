package com.edawtech.jiayou.config.bean;

/**
 * ClassName:      LoginInfo
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/17 13:56
 * <p>
 * Description:     登录成功返回
 */
public class LoginInfo {

    /**
     * data : {"appId":"JIA_YOU","uid":"812347","phone":"137777777","level":0,"levelName":"注册用户","headUrl":null}
     * code : 0
     * msg : 登录成功
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
         * appId : JIA_YOU
         * uid : 812347
         * phone : 137777777
         * level : 0
         * levelName : 注册用户
         * headUrl : null
         */

        private String appId;
        private String uid;
        private String phone;
        private int level;
        private String levelName;
        private Object headUrl;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public Object getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(Object headUrl) {
            this.headUrl = headUrl;
        }
    }
}
