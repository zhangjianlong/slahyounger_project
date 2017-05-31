package com.slash.youth.domain;

import java.util.ArrayList;

/**
 * @author lenovo
 * @createDate 2017/4/11
 * @description
 */

public class TagBean {
    private ArrayList<String> tag;
    private int type;

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
