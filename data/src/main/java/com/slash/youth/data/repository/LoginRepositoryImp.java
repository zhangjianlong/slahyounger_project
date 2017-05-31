package com.slash.youth.data.repository;


import com.slash.youth.data.ApiClient;
import com.slash.youth.data.api.subscriber.BaseSubscriber;
import com.slash.youth.data.api.transformer.ErrorTransformer;
import com.slash.youth.data.util.RetrofitUtil;
import com.slash.youth.domain.bean.CustomerService;
import com.slash.youth.domain.bean.LoginResult;
import com.slash.youth.domain.bean.PhoneLoginResultBean;
import com.slash.youth.domain.bean.ResCodeBean;
import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.repository.LoginRepository;
import com.slash.youth.domain.repository.MainRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/10/11
 */
@Singleton
public class LoginRepositoryImp implements LoginRepository {

    ApiClient apiClient;


    @Inject
    public LoginRepositoryImp(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Observable<CustomerService> getCustomService(String def) {
        return apiClient.getCustomService(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<PhoneLoginResultBean> login(String def) {
        return apiClient.login(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<ResCodeBean> getVerifyCode(String def) {
        return apiClient.getVerifyCode(RetrofitUtil.toRequestBody(def));
    }

    @Override
    public Observable<PhoneLoginResultBean> phoneLogin(String def) {
        return apiClient.phoneLogin(RetrofitUtil.toRequestBody(def));
    }

    @Override
    public Observable<ResCodeBean> checkBindng(String def) {
        return apiClient.checkBinding(RetrofitUtil.toRequestBody(def));
    }
}
