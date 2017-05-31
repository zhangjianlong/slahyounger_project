package com.slash.youth.data.repository;

import com.slash.youth.data.ApiClient;
import com.slash.youth.data.api.transformer.ErrorTransformer;
import com.slash.youth.data.util.RetrofitUtil;
import com.slash.youth.domain.bean.ConversationBean;
import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.bean.base.ChangePhoneBean;
import com.slash.youth.domain.repository.MessageRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by acer on 2017/3/19.
 */
@Singleton
public class MessageRepositoryImp implements MessageRepository {

    ApiClient apiClient;

    @Inject
    public MessageRepositoryImp(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Observable<BaseList<ConversationBean>> getConversationList(String def) {
        return apiClient.getConversationList(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> delConversation(String def) {
        return apiClient.delConversation(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> friendStatus(String def) {
        return apiClient.friendStatus(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<ChangePhoneBean> changePhoneStatus(String def) {
        return apiClient.changePhoneStatus(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }
}
