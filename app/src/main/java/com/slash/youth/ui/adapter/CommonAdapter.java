package com.slash.youth.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.slash.youth.utils.ViewHolder;

import java.util.List;

/**
 * Created by zss on 2015-10-10
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData;
    protected int mLayoutId;

    public CommonAdapter(Context context, List<T> data, int layoutId){
        mContext = context;
        mData = data;
        mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(mContext,convertView,mLayoutId,parent,position);
        convert(holder,position);
        return holder.getConvertView();
    }

    /**
     * get holder convert
     */
    public abstract void convert(ViewHolder holder,int position);
}
