package com.edawtech.jiayou.config.bean;

import java.util.List;

/**
 * ClassName:      InviteDetailInfo
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/25 15:57
 * <p>
 * Description:     邀请人加油明细
 */
public class InviteDetailInfo {

    /**
     * offset : 0
     * limit : 2147483647
     * total : 1
     * size : 10
     * pages : 1
     * current : 1
     * searchCount : true
     * openSort : true
     * ascs : null
     * descs : null
     * orderByField : null
     * records : [{"id":"asdasdasdasdasdasdasdasdasd","orderId":"asdasdasdasd","userId":"","username":"","agentName":"","gasStationId":"sadasdsdasd","paySn":null,"phone":"13612810111","orderTime":"2020-04-18 13:15:25","payTime":null,"gasName":"深圳南山加油站","province":"深圳市","city":"市辖区","county":"南山区","gunNo":null,"oilNo":null,"amountPay":null,"amountGas":"7","amountGun":null,"amountDiscounts":null,"orderStatusName":"已支付","couponMoney":"0","couponCode":null,"couponId":null,"litre":"11","payType":"微信支付","payChannel":"wxpay","priceUnit":"4.51","priceOfficial":"5.11","priceGun":"5.11","priceGas":"4.00","orderSource":"佳油","duduPhone":"13612810111","qrCode4PetroChina":null,"source":"1002","paymentType":"1001","orderState":"1001","gasUserId":"1248","gasUsername":"JIA_YOU","gasAgentName":"佳油","preDepositState":"1001","totalAmount":null}]
     * condition : {"phone":"13612810111"}
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
    private ConditionBean condition;
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

    public ConditionBean getCondition() {
        return condition;
    }

    public void setCondition(ConditionBean condition) {
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

    public static class ConditionBean {
        /**
         * phone : 13612810111
         */

        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class RecordsBean {
        /**
         * id : asdasdasdasdasdasdasdasdasd
         * orderId : asdasdasdasd
         * userId :
         * username :
         * agentName :
         * gasStationId : sadasdsdasd
         * paySn : null
         * phone : 13612810111
         * orderTime : 2020-04-18 13:15:25
         * payTime : null
         * gasName : 深圳南山加油站
         * province : 深圳市
         * city : 市辖区
         * county : 南山区
         * gunNo : null
         * oilNo : null
         * amountPay : null
         * amountGas : 7
         * amountGun : null
         * amountDiscounts : null
         * orderStatusName : 已支付
         * couponMoney : 0
         * couponCode : null
         * couponId : null
         * litre : 11
         * payType : 微信支付
         * payChannel : wxpay
         * priceUnit : 4.51
         * priceOfficial : 5.11
         * priceGun : 5.11
         * priceGas : 4.00
         * orderSource : 佳油
         * duduPhone : 13612810111
         * qrCode4PetroChina : null
         * source : 1002
         * paymentType : 1001
         * orderState : 1001
         * gasUserId : 1248
         * gasUsername : JIA_YOU
         * gasAgentName : 佳油
         * preDepositState : 1001
         * totalAmount : null
         */

        private String id;
        private String orderId;
        private String userId;
        private String username;
        private String agentName;
        private String gasStationId;
        private Object paySn;
        private String phone;
        private String orderTime;
        private String payTime;
        private String gasName;
        private String province;
        private String city;
        private String county;
        private Object gunNo;
        private Object oilNo;
        private Object amountPay;
        private String amountGas;
        private double amountGun;
        private Object amountDiscounts;
        private String orderStatusName;
        private String couponMoney;
        private Object couponCode;
        private Object couponId;
        private String litre;
        private String payType;
        private String payChannel;
        private String priceUnit;
        private String priceOfficial;
        private String priceGun;
        private String priceGas;
        private String orderSource;
        private String duduPhone;
        private Object qrCode4PetroChina;
        private String source;
        private String paymentType;
        private String orderState;
        private String gasUserId;
        private String gasUsername;
        private String gasAgentName;
        private String preDepositState;
        private Object totalAmount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public String getGasStationId() {
            return gasStationId;
        }

        public void setGasStationId(String gasStationId) {
            this.gasStationId = gasStationId;
        }

        public Object getPaySn() {
            return paySn;
        }

        public void setPaySn(Object paySn) {
            this.paySn = paySn;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getGasName() {
            return gasName;
        }

        public void setGasName(String gasName) {
            this.gasName = gasName;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public Object getGunNo() {
            return gunNo;
        }

        public void setGunNo(Object gunNo) {
            this.gunNo = gunNo;
        }

        public Object getOilNo() {
            return oilNo;
        }

        public void setOilNo(Object oilNo) {
            this.oilNo = oilNo;
        }

        public Object getAmountPay() {
            return amountPay;
        }

        public void setAmountPay(Object amountPay) {
            this.amountPay = amountPay;
        }

        public String getAmountGas() {
            return amountGas;
        }

        public void setAmountGas(String amountGas) {
            this.amountGas = amountGas;
        }

        public double getAmountGun() {
            return amountGun;
        }

        public void setAmountGun(double amountGun) {
            this.amountGun = amountGun;
        }

        public Object getAmountDiscounts() {
            return amountDiscounts;
        }

        public void setAmountDiscounts(Object amountDiscounts) {
            this.amountDiscounts = amountDiscounts;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public Object getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(Object couponCode) {
            this.couponCode = couponCode;
        }

        public Object getCouponId() {
            return couponId;
        }

        public void setCouponId(Object couponId) {
            this.couponId = couponId;
        }

        public String getLitre() {
            return litre;
        }

        public void setLitre(String litre) {
            this.litre = litre;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getPriceUnit() {
            return priceUnit;
        }

        public void setPriceUnit(String priceUnit) {
            this.priceUnit = priceUnit;
        }

        public String getPriceOfficial() {
            return priceOfficial;
        }

        public void setPriceOfficial(String priceOfficial) {
            this.priceOfficial = priceOfficial;
        }

        public String getPriceGun() {
            return priceGun;
        }

        public void setPriceGun(String priceGun) {
            this.priceGun = priceGun;
        }

        public String getPriceGas() {
            return priceGas;
        }

        public void setPriceGas(String priceGas) {
            this.priceGas = priceGas;
        }

        public String getOrderSource() {
            return orderSource;
        }

        public void setOrderSource(String orderSource) {
            this.orderSource = orderSource;
        }

        public String getDuduPhone() {
            return duduPhone;
        }

        public void setDuduPhone(String duduPhone) {
            this.duduPhone = duduPhone;
        }

        public Object getQrCode4PetroChina() {
            return qrCode4PetroChina;
        }

        public void setQrCode4PetroChina(Object qrCode4PetroChina) {
            this.qrCode4PetroChina = qrCode4PetroChina;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
            this.orderState = orderState;
        }

        public String getGasUserId() {
            return gasUserId;
        }

        public void setGasUserId(String gasUserId) {
            this.gasUserId = gasUserId;
        }

        public String getGasUsername() {
            return gasUsername;
        }

        public void setGasUsername(String gasUsername) {
            this.gasUsername = gasUsername;
        }

        public String getGasAgentName() {
            return gasAgentName;
        }

        public void setGasAgentName(String gasAgentName) {
            this.gasAgentName = gasAgentName;
        }

        public String getPreDepositState() {
            return preDepositState;
        }

        public void setPreDepositState(String preDepositState) {
            this.preDepositState = preDepositState;
        }

        public Object getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Object totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
}
