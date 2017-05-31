package com.slash.youth.data.repository;


import com.slash.youth.data.ApiClient;
import com.slash.youth.data.api.transformer.ErrorTransformer;
import com.slash.youth.data.util.RetrofitUtil;
import com.slash.youth.domain.bean.LabelBean;
import com.slash.youth.domain.bean.ResCodeBean;
import com.slash.youth.domain.repository.TaskRepository;

import java.util.List;

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
public class TaskRepositoryImp implements TaskRepository {

    ApiClient apiClient;


    @Inject
    public TaskRepositoryImp(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Observable<List<LabelBean>> getLables(String def) {
        return apiClient.getLables(RetrofitUtil.toRequestBody(def));
    }

    @Override
    public Observable<List<LabelBean>> getCustomLables(String def) {
        return apiClient.getCustomLables(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer());
    }

    @Override
    public Observable deleteLable(String def) {
        return apiClient.deleteLable(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer());
    }

    @Override
    public Observable createLable(String def) {
        return apiClient.createLable(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer());
    }
}
