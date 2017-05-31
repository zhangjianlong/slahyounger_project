package com.slash.youth.domain;

import com.slash.youth.domain.bean.TaskList;

import java.util.ArrayList;

/**
 * Created by zhouyifeng on 2016/10/29.
 */
public class MyTaskList {

    public int rescode;
    public Data data;

    public class Data {
        public ArrayList<TaskList.TaskBean> list;
    }
}
