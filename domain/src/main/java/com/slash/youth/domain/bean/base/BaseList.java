package com.slash.youth.domain.bean.base;

import java.util.ArrayList;

/**
 * Created by acer on 2017/3/18.
 */

public class BaseList<T> {

    private ArrayList<T> list;

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

}
