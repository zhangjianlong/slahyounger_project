package com.slash.youth.domain.bean;

/**
 * Created by acer on 2017/3/10.
 */

public class OtherInfo {

    /**
     * uinfo : {"achievetaskcount":0,"avatar":"","averageservicepoint":0,"careertype":1,"city":"","company":"","desc":"","id":0,"name":"","needtag":"","position":"","province":"","relationshipscount":0,"relationshipsratio":0,"servicetag":"","sex":1,"totoltaskcount":0,"userservicepoint":-1}
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
         * achievetaskcount : 0
         * avatar :
         * averageservicepoint : 0
         * careertype : 1
         * city :
         * company :
         * desc :
         * id : 0
         * name :
         * needtag :
         * position :
         * province :
         * relationshipscount : 0
         * relationshipsratio : 0
         * servicetag :
         * sex : 1
         * totoltaskcount : 0
         * userservicepoint : -1
         */

        private int achievetaskcount;
        private String avatar;
        private int averageservicepoint;
        private int careertype;
        private String city;
        private String company;
        private String desc;
        private int id;
        private String name;
        private String needtag;
        private String position;
        private String province;
        private int relationshipscount;
        private int relationshipsratio;
        private String servicetag;
        private int sex;
        private int totoltaskcount;
        private int userservicepoint;

        public int getAchievetaskcount() {
            return achievetaskcount;
        }

        public void setAchievetaskcount(int achievetaskcount) {
            this.achievetaskcount = achievetaskcount;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getAverageservicepoint() {
            return averageservicepoint;
        }

        public void setAverageservicepoint(int averageservicepoint) {
            this.averageservicepoint = averageservicepoint;
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

        public String getNeedtag() {
            return needtag;
        }

        public void setNeedtag(String needtag) {
            this.needtag = needtag;
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

        public int getRelationshipscount() {
            return relationshipscount;
        }

        public void setRelationshipscount(int relationshipscount) {
            this.relationshipscount = relationshipscount;
        }

        public int getRelationshipsratio() {
            return relationshipsratio;
        }

        public void setRelationshipsratio(int relationshipsratio) {
            this.relationshipsratio = relationshipsratio;
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

        public int getTotoltaskcount() {
            return totoltaskcount;
        }

        public void setTotoltaskcount(int totoltaskcount) {
            this.totoltaskcount = totoltaskcount;
        }

        public int getUserservicepoint() {
            return userservicepoint;
        }

        public void setUserservicepoint(int userservicepoint) {
            this.userservicepoint = userservicepoint;
        }
    }
}
