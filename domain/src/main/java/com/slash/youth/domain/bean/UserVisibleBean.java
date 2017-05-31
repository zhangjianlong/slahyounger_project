package com.slash.youth.domain.bean;

/**
 * @author lenovo
 * @createDate 2017/4/10
 * @description
 */

public class UserVisibleBean {


    /**
     * data : {"company":1,"evalution":1,"id":1,"servicepower":1,"uid":10008}
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
         * company : 1
         * evalution : 1
         * id : 1
         * servicepower : 1
         * uid : 10008
         */

        private int company;
        private int evalution;
        private int id;
        private int servicepower;
        private int uid;

        public int getCompany() {
            return company;
        }

        public void setCompany(int company) {
            this.company = company;
        }

        public int getEvalution() {
            return evalution;
        }

        public void setEvalution(int evalution) {
            this.evalution = evalution;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getServicepower() {
            return servicepower;
        }

        public void setServicepower(int servicepower) {
            this.servicepower = servicepower;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
