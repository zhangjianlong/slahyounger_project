package com.slash.youth.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by acer on 2017/4/6.
 */

public class ProvinceBean implements Serializable {

    /**
     * name : 北京
     * city : [{"name":"北京","cityId":"101","provinceId":"1"}]
     * provinceId : 1
     */

    private String name;
    private String provinceId;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean implements Serializable {
        /**
         * name : 北京
         * cityId : 101
         * provinceId : 1
         */

        private String name;
        private String cityId;
        private String provinceId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }
    }
}
