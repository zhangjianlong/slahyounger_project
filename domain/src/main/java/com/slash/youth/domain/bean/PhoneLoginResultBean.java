package com.slash.youth.domain.bean;

/**
 * Created by zhouyifeng on 2016/12/12.
 */
public class PhoneLoginResultBean {
        public int getRescode() {
                return rescode;
        }

        public void setRescode(int rescode) {
                this.rescode = rescode;
        }

        public Data getData() {
                return data;
        }

        public void setData(Data data) {
                this.data = data;
        }

        public int rescode;
        public Data data;

        public class Data {
                public String getRongToken() {
                        return rongToken;
                }

                public void setRongToken(String rongToken) {
                        this.rongToken = rongToken;
                }

                public String getToken() {
                        return token;
                }

                public void setToken(String token) {
                        this.token = token;
                }

                public long getUid() {
                        return uid;
                }

                public void setUid(long uid) {
                        this.uid = uid;
                }

                public String rongToken;
                public String token;
                public long uid;
        }
}
