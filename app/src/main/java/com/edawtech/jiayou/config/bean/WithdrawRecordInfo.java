package com.edawtech.jiayou.config.bean;

import java.util.List;

/**
 * ClassName:      WithdrawRecordInfo
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/22 15:08
 * <p>
 * Description:     成长金提现记录实体类
 */
public class WithdrawRecordInfo {

    /**
     * data : {"offset":0,"limit":2147483647,"total":8,"size":10,"pages":1,"current":1,"searchCount":true,"openSort":true,"ascs":null,"descs":null,"orderByField":null,"records":[{"id":10022547,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"23123","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022548,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASDAS","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022549,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASD","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022550,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASDASD","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022551,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASDASD","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022552,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASDAS","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022553,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASD","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022554,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASD","reason":"","paySn":"","mtime":null,"ctime":null}],"condition":{"uid":"812363","appId":"JIA_YOU"},"asc":true}
     * code : 0
     * msg : 查询成功
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
         * limit : 2147483647
         * total : 8
         * size : 10
         * pages : 1
         * current : 1
         * searchCount : true
         * openSort : true
         * ascs : null
         * descs : null
         * orderByField : null
         * records : [{"id":10022547,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"23123","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022548,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASDAS","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022549,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASD","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022550,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASDASD","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022551,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASDASD","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022552,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASDAS","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022553,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASD","reason":"","paySn":"","mtime":null,"ctime":null},{"id":10022554,"appId":"JIA_YOU","uid":"812363","way":"WX","amount":10,"status":0,"remark":"ASD","reason":"","paySn":"","mtime":null,"ctime":null}]
         * condition : {"uid":"812363","appId":"JIA_YOU"}
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
             * uid : 812363
             * appId : JIA_YOU
             */

            private String uid;
            private String appId;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }
        }

        public static class RecordsBean {
            /**
             * id : 10022547
             * appId : JIA_YOU
             * uid : 812363
             * way : WX
             * amount : 10
             * status : 0
             * remark : 23123
             * reason :
             * paySn :
             * mtime : null
             * ctime : null
             */

            private int id;
            private String appId;
            private String uid;
            private String way;
            private int amount;
            private int status;
            private String remark;
            private String reason;
            private String paySn;
            private Object mtime;
            private Object ctime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public String getWay() {
                return way;
            }

            public void setWay(String way) {
                this.way = way;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getPaySn() {
                return paySn;
            }

            public void setPaySn(String paySn) {
                this.paySn = paySn;
            }

            public Object getMtime() {
                return mtime;
            }

            public void setMtime(Object mtime) {
                this.mtime = mtime;
            }

            public Object getCtime() {
                return ctime;
            }

            public void setCtime(Object ctime) {
                this.ctime = ctime;
            }
        }
    }
}
