package com.edawtech.jiayou.config.bean;

/**
 * ClassName:      UserGrowthBean
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/18 16:55
 * <p>
 * Description:     用户成长金
 */
public class UserGrowthBean {

    /**
     * data : {"red":"0","amount":"0","coupon":"0","invitation":"0","exchangePoint":"0"}
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
         * red : 0
         * amount : 0
         * coupon : 0
         * invitation : 0
         * exchangePoint : 0
         */

        private String red;
        private String amount;          //成长金
        private String coupon;
        private String invitation;
        private String exchangePoint;

        public String getRed() {
            return red;
        }

        public void setRed(String red) {
            this.red = red;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getInvitation() {
            return invitation;
        }

        public void setInvitation(String invitation) {
            this.invitation = invitation;
        }

        public String getExchangePoint() {
            return exchangePoint;
        }

        public void setExchangePoint(String exchangePoint) {
            this.exchangePoint = exchangePoint;
        }
    }
}
