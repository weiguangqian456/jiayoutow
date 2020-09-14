package com.edawtech.jiayou.config.bean;

import java.util.List;

public class MoreReListBean {
    private MoreReListData data;
    private int code;
    private String msg;


    public MoreReListData getData() {
        return data;
    }

    public void setData(MoreReListData data) {
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

    public static class MoreReListData {
        private List<MoreReListRecords> records;
        private int total;
        private int pages;
        private int size;

        public List<MoreReListRecords> getRecords() {
            return records;
        }

        public void setRecords(List<MoreReListRecords> records) {
            this.records = records;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class MoreReListRecords {
        private String gasId;
        private String gasName;
        private String gasAddress;
        private Double gasAddressLongitude;
        private Double gasAddressLatitude;
        private String gasLogoSmall;
        private String gasLogoBig;
        private Double priceYfq;
        private Double priceOfficial;
        private String priceGun;
        private String distance;
        private String source;

        public String getGasId() {
            return gasId;
        }

        public void setGasId(String gasId) {
            this.gasId = gasId;
        }

        public String getGasName() {
            return gasName;
        }

        public void setGasName(String gasName) {
            this.gasName = gasName;
        }

        public String getGasAddress() {
            return gasAddress;
        }

        public void setGasAddress(String gasAddress) {
            this.gasAddress = gasAddress;
        }

        public String getGasLogoSmall() {
            return gasLogoSmall;
        }

        public void setGasLogoSmall(String gasLogoSmall) {
            this.gasLogoSmall = gasLogoSmall;
        }

        public String getGasLogoBig() {
            return gasLogoBig;
        }

        public void setGasLogoBig(String gasLogoBig) {
            this.gasLogoBig = gasLogoBig;
        }

        public Double getGasAddressLongitude() {
            return gasAddressLongitude;
        }

        public void setGasAddressLongitude(Double gasAddressLongitude) {
            this.gasAddressLongitude = gasAddressLongitude;
        }

        public Double getGasAddressLatitude() {
            return gasAddressLatitude;
        }

        public void setGasAddressLatitude(Double gasAddressLatitude) {
            this.gasAddressLatitude = gasAddressLatitude;
        }

        public Double getPriceYfq() {
            return priceYfq;
        }

        public void setPriceYfq(Double priceYfq) {
            this.priceYfq = priceYfq;
        }

        public Double getPriceOfficial() {
            return priceOfficial;
        }

        public void setPriceOfficial(Double priceOfficial) {
            this.priceOfficial = priceOfficial;
        }

        public String getPriceGun() {
            return priceGun;
        }

        public void setPriceGun(String priceGun) {
            this.priceGun = priceGun;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

}
