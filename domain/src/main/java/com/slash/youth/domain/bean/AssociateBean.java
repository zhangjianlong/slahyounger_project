package com.slash.youth.domain.bean;

import java.util.List;

/**
 * @author lenovo
 * @createDate 2017/4/12
 * @description
 */

public class AssociateBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * tag : 张一山
         */

        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
