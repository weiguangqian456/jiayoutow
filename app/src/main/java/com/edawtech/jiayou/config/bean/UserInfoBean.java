package com.edawtech.jiayou.config.bean;

import java.util.List;

/**
 * ClassName:      UserInfoBean
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/18 16:12
 * <p>
 * Description:     用户详情
 */
public class UserInfoBean {

    /**
     * data : {"level":0,"tUserInfo":[{"appId":"ctdh","uid":"888888","phone":"17000000000","regTime":1509552000000,"mtime":null,"pv":"","v":"","isAgent":"n","agentId":"","invitedBy":"","inviteCount":8,"userType":0,"firstPayTime":null,"lastPayTime":null,"payCount":0,"payAmount":0,"expireTime":null,"expireCount":0,"invitePay":0,"giftStatus":"n","autoDraw":"y","ctime":1509685854000,"rewardInvited":"0","totalAmountGun":null,"actualPhone":null,"invitationPhone":null}],"levelName":"注册用户"}
     * code : 0
     * msg : 成功
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
         * level : 0
         * tUserInfo : [{"appId":"ctdh","uid":"888888","phone":"17000000000","regTime":1509552000000,"mtime":null,"pv":"","v":"","isAgent":"n","agentId":"","invitedBy":"","inviteCount":8,"userType":0,"firstPayTime":null,"lastPayTime":null,"payCount":0,"payAmount":0,"expireTime":null,"expireCount":0,"invitePay":0,"giftStatus":"n","autoDraw":"y","ctime":1509685854000,"rewardInvited":"0","totalAmountGun":null,"actualPhone":null,"invitationPhone":null}]
         * levelName : 注册用户
         */

        private int level;
        private String levelName;
        private List<TUserInfoBean> tUserInfo;

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

        public List<TUserInfoBean> getTUserInfo() {
            return tUserInfo;
        }

        public void setTUserInfo(List<TUserInfoBean> tUserInfo) {
            this.tUserInfo = tUserInfo;
        }

        public static class TUserInfoBean {
            /**
             * appId : ctdh
             * uid : 888888
             * phone : 17000000000
             * regTime : 1509552000000
             * mtime : null
             * pv :
             * v :
             * isAgent : n
             * agentId :
             * invitedBy :
             * inviteCount : 8
             * userType : 0
             * firstPayTime : null
             * lastPayTime : null
             * payCount : 0
             * payAmount : 0
             * expireTime : null
             * expireCount : 0
             * invitePay : 0
             * giftStatus : n
             * autoDraw : y
             * ctime : 1509685854000
             * rewardInvited : 0
             * totalAmountGun : null
             * actualPhone : null
             * invitationPhone : null
             */

            private String appId;
            private String uid;
            private String phone;
            private long regTime;
            private String mtime;
            private String pv;
            private String v;
            private String isAgent;
            private String agentId;
            private String invitedBy;
            private int inviteCount;
            private int userType;
            private String firstPayTime;
            private String lastPayTime;
            private int payCount;
            private int payAmount;
            private String expireTime;
            private int expireCount;
            private int invitePay;
            private String giftStatus;
            private String autoDraw;
            private long ctime;
            private String rewardInvited;
            private String totalAmountGun;
            private String actualPhone;
            private String invitationPhone;
            private String userName;
            private String userGender;
            private String headLikeUrl;
            private String email;
            private String birthday;

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

            public long getRegTime() {
                return regTime;
            }

            public void setRegTime(long regTime) {
                this.regTime = regTime;
            }

            public String getMtime() {
                return mtime;
            }

            public void setMtime(String mtime) {
                this.mtime = mtime;
            }

            public String getPv() {
                return pv;
            }

            public void setPv(String pv) {
                this.pv = pv;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }

            public String getIsAgent() {
                return isAgent;
            }

            public void setIsAgent(String isAgent) {
                this.isAgent = isAgent;
            }

            public String getAgentId() {
                return agentId;
            }

            public void setAgentId(String agentId) {
                this.agentId = agentId;
            }

            public String getInvitedBy() {
                return invitedBy;
            }

            public void setInvitedBy(String invitedBy) {
                this.invitedBy = invitedBy;
            }

            public int getInviteCount() {
                return inviteCount;
            }

            public void setInviteCount(int inviteCount) {
                this.inviteCount = inviteCount;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public String getFirstPayTime() {
                return firstPayTime;
            }

            public void setFirstPayTime(String firstPayTime) {
                this.firstPayTime = firstPayTime;
            }

            public String getLastPayTime() {
                return lastPayTime;
            }

            public void setLastPayTime(String lastPayTime) {
                this.lastPayTime = lastPayTime;
            }

            public int getPayCount() {
                return payCount;
            }

            public void setPayCount(int payCount) {
                this.payCount = payCount;
            }

            public int getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(int payAmount) {
                this.payAmount = payAmount;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public int getExpireCount() {
                return expireCount;
            }

            public void setExpireCount(int expireCount) {
                this.expireCount = expireCount;
            }

            public int getInvitePay() {
                return invitePay;
            }

            public void setInvitePay(int invitePay) {
                this.invitePay = invitePay;
            }

            public String getGiftStatus() {
                return giftStatus;
            }

            public void setGiftStatus(String giftStatus) {
                this.giftStatus = giftStatus;
            }

            public String getAutoDraw() {
                return autoDraw;
            }

            public void setAutoDraw(String autoDraw) {
                this.autoDraw = autoDraw;
            }

            public long getCtime() {
                return ctime;
            }

            public void setCtime(long ctime) {
                this.ctime = ctime;
            }

            public String getRewardInvited() {
                return rewardInvited;
            }

            public void setRewardInvited(String rewardInvited) {
                this.rewardInvited = rewardInvited;
            }

            public String getTotalAmountGun() {
                return totalAmountGun;
            }

            public void setTotalAmountGun(String totalAmountGun) {
                this.totalAmountGun = totalAmountGun;
            }

            public String getActualPhone() {
                return actualPhone;
            }

            public void setActualPhone(String actualPhone) {
                this.actualPhone = actualPhone;
            }

            public String getInvitationPhone() {
                return invitationPhone;
            }

            public void setInvitationPhone(String invitationPhone) {
                this.invitationPhone = invitationPhone;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserGender() {
                return userGender;
            }

            public void setUserGender(String userGender) {
                this.userGender = userGender;
            }

            public String getHeadLikeUrl() {
                return headLikeUrl;
            }

            public void setHeadLikeUrl(String headLikeUrl) {
                this.headLikeUrl = headLikeUrl;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }
        }
    }
}
