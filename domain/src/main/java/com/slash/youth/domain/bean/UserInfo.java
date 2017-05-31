package com.slash.youth.domain.bean;

/**
 * @author lenovo
 * @createDate 2017/4/12
 * @description
 */

public class UserInfo {

    /**
     * uinfo : {"avatar":"group1/M00/00/07/eBtfY1hafHmAO_V9AAAo2cGFwKk.ccbb06","careertype":1,"city":"北京","company":"腾讯","desc":"","id":10002,"name":"小赵","position":"技术专家","province":"北京","phone":13613241231,"needtag":"1-android 2-ios","servicetag":"1-android 2-ios","sex":1}
     */

    private UinfoBean uinfo;

    public UinfoBean getUinfo() {
        return uinfo;
    }

    public void setUinfo(UinfoBean uinfo) {
        this.uinfo = uinfo;
    }

    public static class UinfoBean {
        /**
         * avatar : group1/M00/00/07/eBtfY1hafHmAO_V9AAAo2cGFwKk.ccbb06
         * careertype : 1
         * city : 北京
         * company : 腾讯
         * desc :
         * id : 10002
         * name : 小赵
         * position : 技术专家
         * province : 北京
         * phone : 13613241231
         * needtag : 1-android 2-ios
         * servicetag : 1-android 2-ios
         * sex : 1
         */

        private String avatar;
        private int careertype;
        private String city;
        private String company;
        private String desc;
        private int id;
        private String name;
        private String position;
        private String province;
        private long phone;
        private String needtag;
        private String servicetag;
        private int sex;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCareertype() {
            return careertype;
        }

        public void setCareertype(int careertype) {
            this.careertype = careertype;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        public String getNeedtag() {
            return needtag;
        }

        public void setNeedtag(String needtag) {
            this.needtag = needtag;
        }

        public String getServicetag() {
            return servicetag;
        }

        public void setServicetag(String servicetag) {
            this.servicetag = servicetag;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }
}
