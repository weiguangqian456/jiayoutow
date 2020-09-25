package com.edawtech.jiayou.config.bean;

import java.util.List;

/**
 * ClassName:      InviteInfo
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/22 18:40
 * <p>
 * Description:     邀请人列表
 */
public class InviteInfo {

    /**
     * data : {"offset":0,"limit":10,"total":5,"size":10,"pages":1,"current":1,"searchCount":true,"openSort":true,"ascs":null,"descs":null,"orderByField":null,"records":[{"appId":"JIA_YOU","uid":"100005","phone":"1213213","regTime":1600769468000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"1213213","invitationPhone":"136****0111"},{"appId":"JIA_YOU","uid":"100004","phone":"321321","regTime":1600768534000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"321321","invitationPhone":"136****0111"},{"appId":"JIA_YOU","uid":"100003","phone":"4545645545","regTime":1600768491000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"4545645545","invitationPhone":"136****0111"},{"appId":"JIA_YOU","uid":"100002","phone":"5646546556","regTime":1600768446000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"5646546556","invitationPhone":"136****0111"},{"appId":"JIA_YOU","uid":"100000","phone":"54554545","regTime":1600768272000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"54554545","invitationPhone":"136****0111"}],"condition":null,"asc":true}
     * code : 0
     * msg : 获取数据成功
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
         * offset : 0
         * limit : 10
         * total : 5
         * size : 10
         * pages : 1
         * current : 1
         * searchCount : true
         * openSort : true
         * ascs : null
         * descs : null
         * orderByField : null
         * records : [{"appId":"JIA_YOU","uid":"100005","phone":"1213213","regTime":1600769468000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"1213213","invitationPhone":"136****0111"},{"appId":"JIA_YOU","uid":"100004","phone":"321321","regTime":1600768534000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"321321","invitationPhone":"136****0111"},{"appId":"JIA_YOU","uid":"100003","phone":"4545645545","regTime":1600768491000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"4545645545","invitationPhone":"136****0111"},{"appId":"JIA_YOU","uid":"100002","phone":"5646546556","regTime":1600768446000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"5646546556","invitationPhone":"136****0111"},{"appId":"JIA_YOU","uid":"100000","phone":"54554545","regTime":1600768272000,"pv":null,"v":null,"isAgent":null,"agentId":null,"invitedBy":"812363","inviteCount":null,"userType":null,"firstPayTime":null,"lastPayTime":null,"payCount":null,"payAmount":null,"expireTime":null,"expireCount":null,"invitePay":null,"giftStatus":null,"autoDraw":null,"ctime":null,"rewardInvited":null,"totalAmountGun":"0","actualPhone":"54554545","invitationPhone":"136****0111"}]
         * condition : null
         * asc : true
         */

        private int offset;
        private int limit;
        private int total;
        private int size;
        private int pages;
        private int current;
        private boolean searchCount;
        private boolean openSort;
        private Object ascs;
        private Object descs;
        private Object orderByField;
        private Object condition;
        private boolean asc;
        private List<RecordsBean> records;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public boolean isOpenSort() {
            return openSort;
        }

        public void setOpenSort(boolean openSort) {
            this.openSort = openSort;
        }

        public Object getAscs() {
            return ascs;
        }

        public void setAscs(Object ascs) {
            this.ascs = ascs;
        }

        public Object getDescs() {
            return descs;
        }

        public void setDescs(Object descs) {
            this.descs = descs;
        }

        public Object getOrderByField() {
            return orderByField;
        }

        public void setOrderByField(Object orderByField) {
            this.orderByField = orderByField;
        }

        public Object getCondition() {
            return condition;
        }

        public void setCondition(Object condition) {
            this.condition = condition;
        }

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            /**
             * appId : JIA_YOU
             * uid : 100005
             * phone : 1213213
             * regTime : 1600769468000
             * pv : null
             * v : null
             * isAgent : null
             * agentId : null
             * invitedBy : 812363
             * inviteCount : null
             * userType : null
             * firstPayTime : null
             * lastPayTime : null
             * payCount : null
             * payAmount : null
             * expireTime : null
             * expireCount : null
             * invitePay : null
             * giftStatus : null
             * autoDraw : null
             * ctime : null
             * rewardInvited : null
             * totalAmountGun : 0
             * actualPhone : 1213213
             * invitationPhone : 136****0111
             */

            private String appId;
            private String uid;
            private String phone;
            private long regTime;
            private Object pv;
            private Object v;
            private Object isAgent;
            private Object agentId;
            private String invitedBy;
            private Object inviteCount;
            private Object userType;
            private Object firstPayTime;
            private Object lastPayTime;
            private Object payCount;
            private Object payAmount;
            private Object expireTime;
            private Object expireCount;
            private Object invitePay;
            private Object giftStatus;
            private Object autoDraw;
            private Object ctime;
            private Object rewardInvited;
            private String totalAmountGun;
            private String actualPhone;
            private String invitationPhone;

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

            public Object getPv() {
                return pv;
            }

            public void setPv(Object pv) {
                this.pv = pv;
            }

            public Object getV() {
                return v;
            }

            public void setV(Object v) {
                this.v = v;
            }

            public Object getIsAgent() {
                return isAgent;
            }

            public void setIsAgent(Object isAgent) {
                this.isAgent = isAgent;
            }

            public Object getAgentId() {
                return agentId;
            }

            public void setAgentId(Object agentId) {
                this.agentId = agentId;
            }

            public String getInvitedBy() {
                return invitedBy;
            }

            public void setInvitedBy(String invitedBy) {
                this.invitedBy = invitedBy;
            }

            public Object getInviteCount() {
                return inviteCount;
            }

            public void setInviteCount(Object inviteCount) {
                this.inviteCount = inviteCount;
            }

            public Object getUserType() {
                return userType;
            }

            public void setUserType(Object userType) {
                this.userType = userType;
            }

            public Object getFirstPayTime() {
                return firstPayTime;
            }

            public void setFirstPayTime(Object firstPayTime) {
                this.firstPayTime = firstPayTime;
            }

            public Object getLastPayTime() {
                return lastPayTime;
            }

            public void setLastPayTime(Object lastPayTime) {
                this.lastPayTime = lastPayTime;
            }

            public Object getPayCount() {
                return payCount;
            }

            public void setPayCount(Object payCount) {
                this.payCount = payCount;
            }

            public Object getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(Object payAmount) {
                this.payAmount = payAmount;
            }

            public Object getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(Object expireTime) {
                this.expireTime = expireTime;
            }

            public Object getExpireCount() {
                return expireCount;
            }

            public void setExpireCount(Object expireCount) {
                this.expireCount = expireCount;
            }

            public Object getInvitePay() {
                return invitePay;
            }

            public void setInvitePay(Object invitePay) {
                this.invitePay = invitePay;
            }

            public Object getGiftStatus() {
                return giftStatus;
            }

            public void setGiftStatus(Object giftStatus) {
                this.giftStatus = giftStatus;
            }

            public Object getAutoDraw() {
                return autoDraw;
            }

            public void setAutoDraw(Object autoDraw) {
                this.autoDraw = autoDraw;
            }

            public Object getCtime() {
                return ctime;
            }

            public void setCtime(Object ctime) {
                this.ctime = ctime;
            }

            public Object getRewardInvited() {
                return rewardInvited;
            }

            public void setRewardInvited(Object rewardInvited) {
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
        }
    }
}
