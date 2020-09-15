package com.edawtech.jiayou.config.bean;

import java.util.List;

public class RefuelDetail {
    public String source;
    public String gasId;
    public String gasName;
    public List<OilPriceList> oilPriceList;

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

    public List<OilPriceList> getOilPriceList() {
        return oilPriceList;
    }

    public void setOilPriceList(List<OilPriceList> oilPriceList) {
        this.oilPriceList = oilPriceList;
    }

    public static class OilPriceList {
        public String oilNo;
        public String oilName;
        public String oilType;
        public Double priceYfq;
        public Double priceOfficial;
        public Double priceGun;
        public List<GunNos> gunNos;
        public boolean check;

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

        public Double getPriceOfficial() {
            return priceOfficial;
        }

        public void setPriceOfficial(Double priceOfficial) {
            this.priceOfficial = priceOfficial;
        }

        public Double getPriceGun() {
            return priceGun;
        }

        public void setPriceGun(Double priceGun) {
            this.priceGun = priceGun;
        }

        public List<GunNos> getGunNos() {
            return gunNos;
        }

        public void setGunNos(List<GunNos> gunNos) {
            this.gunNos = gunNos;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public static class GunNos {
            public String gunNo;
            public boolean check;

            public String getGunNo() {
                return gunNo;
            }

            public void setGunNo(String gunNo) {
                this.gunNo = gunNo;
            }

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }
        }
    }
}
