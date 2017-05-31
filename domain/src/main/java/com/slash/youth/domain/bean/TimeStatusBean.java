package com.slash.youth.domain.bean;

/**
 * @author lenovo
 * @createDate 2017/4/10
 * @description
 */

public class TimeStatusBean {


    /**
     * data : {"cts":1491807373661,"dnd":0,"endtime":"08:00","id":1,"starttime":"22:00","uid":10209,"uts":1491836648349}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cts : 1491807373661
         * dnd : 0
         * endtime : 08:00
         * id : 1
         * starttime : 22:00
         * uid : 10209
         * uts : 1491836648349
         */

        private long cts;
        private int dnd;
        private String endtime;
        private int id;
        private String starttime;
        private int uid;
        private long uts;

        public long getCts() {
            return cts;
        }

        public void setCts(long cts) {
            this.cts = cts;
        }

        public int getDnd() {
            return dnd;
        }

        public void setDnd(int dnd) {
            this.dnd = dnd;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public long getUts() {
            return uts;
        }

        public void setUts(long uts) {
            this.uts = uts;
        }
    }
}
