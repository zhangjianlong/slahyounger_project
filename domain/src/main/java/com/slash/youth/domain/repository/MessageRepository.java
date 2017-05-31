package com.slash.youth.domain.repository;

import com.slash.youth.domain.bean.ConversationBean;
import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.bean.base.ChangePhoneBean;

import rx.Observable;

/**
 * Created by acer on 2017/3/19.
 */

public interface MessageRepository {

    Observable<BaseList<ConversationBean>> getConversationList(String def);

    Observable<StatusBean> delConversation(String def);

    Observable<StatusBean> friendStatus(String def);

    Observable<ChangePhoneBean> changePhoneStatus(String def);
}
