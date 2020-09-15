package com.edawtech.jiayou.config.bean;

import java.util.List;

public class RefuelDetailBean {
    private RefuelDetailData data;
    private int code;
    private String msg;

    public RefuelDetailData getData() {
        return data;
    }

    public void setData(RefuelDetailData data) {
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

    public static class RefuelDetailData{
        private String source;
        private String gasId;
        private String gasName;
        private List<RefuelDetaiOilPriceList> oilPriceList;


        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

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

        public List<RefuelDetaiOilPriceList> getOilPriceList() {
            return oilPriceList;
        }

        public void setOilPriceList(List<RefuelDetaiOilPriceList> oilPriceList) {
            this.oilPriceList = oilPriceList;
        }
    }
    public static class RefuelDetaiOilPriceList{
        private String oilNo;
        private String oilName;
        private String oilType;
        private Double priceYfq;
        private Double priceGun;
        private Double priceOfficial;
        private List<RefuelDetaiOilGunNos> gunNos;
        public boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getOilNo() {
            return oilNo;
        }

        public void setOilNo(String oilNo) {
            this.oilNo = oilNo;
        }

        public String getOilName() {
            return oilName;
        }

        public void setOilName(String oilName) {
            this.oilName = oilName;
        }

        public String getOilType() {
            return oilType;
        }

        public void setOilType(String oilType) {
            this.oilType = oilType;
        }

        public Double getPriceYfq() {
            return priceYfq;
        }

        public void setPriceYfq(Double priceYfq) {
            this.priceYfq = priceYfq;
        }

        public Double getPriceGun() {
            return priceGun;
        }

        public void setPriceGun(Double priceGun) {
            this.priceGun = priceGun;
        }

        public Double getPriceOfficial() {
            return priceOfficial;
        }

        public void setPriceOfficial(Double priceOfficial) {
            this.priceOfficial = priceOfficial;
        }

        public List<RefuelDetaiOilGunNos> getGunNos() {
            return gunNos;
        }

        public void setGunNos(List<RefuelDetaiOilGunNos> gunNos) {
            this.gunNos = gunNos;
        }
    }

    public static class RefuelDetaiOilGunNos{
        private String gunNo;
        private String oilNo;
        private String source;
        public boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getGunNo() {
            return gunNo;
        }

        public void setGunNo(String gunNo) {
            this.gunNo = gunNo;
        }

        public String getOilNo() {
            return oilNo;
        }

        public void setOilNo(String oilNo) {
            this.oilNo = oilNo;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }


}
