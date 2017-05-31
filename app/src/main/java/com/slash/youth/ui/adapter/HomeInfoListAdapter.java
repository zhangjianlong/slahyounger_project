package com.slash.youth.ui.adapter;

import com.slash.youth.domain.ConversationListBean;
import com.slash.youth.ui.holder.BaseHolder;
import com.slash.youth.ui.holder.HomeInfoListHolder;

import java.util.ArrayList;

/**
 * Created by zhouyifeng on 2016/10/11.
 */
public class HomeInfoListAdapter extends SlashBaseAdapter<ConversationListBean.ConversationInfo> {
    OnSlidClickListener onSlidClickListener;

    public HomeInfoListAdapter(ArrayList<ConversationListBean.ConversationInfo> listData, OnSlidClickListener onSlidClickListener) {
        super(listData);
        this.onSlidClickListener = onSlidClickListener;
    }

    @Override
    public ArrayList<ConversationListBean.ConversationInfo> onLoadMore() {
        return null;
    }

    @Override
    public BaseHolder getHolder(int position) {
        return new HomeInfoListHolder(onSlidClickListener);
    }

    public interface OnSlidClickListener {
        void onDelListener(long id);

        void onItemClick(ConversationListBean.ConversationInfo conversationInfo);
    }
}
