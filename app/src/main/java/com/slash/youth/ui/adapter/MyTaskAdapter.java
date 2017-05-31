package com.slash.youth.ui.adapter;

import com.slash.youth.domain.MyTaskBean;
import com.slash.youth.domain.bean.TaskList;
import com.slash.youth.ui.holder.BaseHolder;
import com.slash.youth.ui.holder.MyTaskHolder;

import java.util.ArrayList;

/**
 * Created by zhouyifeng on 2016/10/26.
 */
public class MyTaskAdapter extends SlashBaseAdapter<TaskList.TaskBean> {
    public MyTaskAdapter(ArrayList<TaskList.TaskBean> listData) {
        super(listData);
    }

    @Override
    public ArrayList<TaskList.TaskBean> onLoadMore() {
        return null;
    }

    @Override
    public BaseHolder getHolder(int position) {
        return new MyTaskHolder();
    }
}
