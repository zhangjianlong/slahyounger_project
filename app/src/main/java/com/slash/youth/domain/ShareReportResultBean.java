package com.slash.youth.domain;

/**
 * Created by zhouyifeng on 2017/2/21.
 */
public class ShareReportResultBean {


    /**
     * data : {"evaluation":{"amount":0,"status":0}}
     * rescode : 0
     */

    private DataBean data;
    private int rescode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getRescode() {
        return rescode;
    }

    public void setRescode(int rescode) {
        this.rescode = rescode;
    }

    public static class DataBean {
        /**
         * evaluation : {"amount":0,"status":0}
         */

        private EvaluationBean evaluation;

        public EvaluationBean getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(EvaluationBean evaluation) {
            this.evaluation = evaluation;
        }

        public static class EvaluationBean {
            /**
             * amount : 0
             * status : 0
             */

            private int amount;
            private int status;

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
        }
    }
}
