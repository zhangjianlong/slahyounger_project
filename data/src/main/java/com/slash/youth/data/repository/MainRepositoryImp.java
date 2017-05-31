package com.slash.youth.data.repository;


import com.slash.youth.data.ApiClient;
import com.slash.youth.data.api.transformer.ErrorTransformer;
import com.slash.youth.data.util.RetrofitUtil;
import com.slash.youth.data.util.UpDownLoadUtil;
import com.slash.youth.domain.bean.AssociateBean;
import com.slash.youth.domain.bean.BannerConfigBean;
import com.slash.youth.domain.bean.CountBean;
import com.slash.youth.domain.bean.FindDemand;
import com.slash.youth.domain.bean.FindServices;
import com.slash.youth.domain.bean.HomeTagInfoBean;
import com.slash.youth.domain.bean.MineInfo;
import com.slash.youth.domain.bean.MineManagerList;
import com.slash.youth.domain.bean.OtherInfo;
import com.slash.youth.domain.bean.PersonRelation;
import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.bean.TaskList;
import com.slash.youth.domain.bean.TimeStatusBean;
import com.slash.youth.domain.bean.UploadBean;
import com.slash.youth.domain.bean.UserEvaluateBean;
import com.slash.youth.domain.bean.UserInfo;
import com.slash.youth.domain.bean.UserTaskBean;
import com.slash.youth.domain.bean.UserVisibleBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interfaces.OnProgressListener;
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
public class MainRepositoryImp implements MainRepository {

    ApiClient apiClient;


    @Inject
    public MainRepositoryImp(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Observable<BannerConfigBean> getBanners(String def) {
        return apiClient.getBanners(RetrofitUtil.toRequestBody(def));
    }

    @Override
    public Observable<HomeTagInfoBean> getTags(String def) {
        return apiClient.getTags(RetrofitUtil.toRequestBody(def));
    }

    @Override
    public Observable<FindServices> getFindServices(String limit) {
        return apiClient.getFindServices(RetrofitUtil.toRequestBody(limit)).compose(new ErrorTransformer());
    }

    @Override
    public Observable<FindDemand> getFindDemand(String limit) {
        return apiClient.getFindDemand(RetrofitUtil.toRequestBody(limit)).compose(new ErrorTransformer());
    }

    @Override
    public Observable<BaseList<TaskList.TaskBean>> getTaskList(String def) {
        return apiClient.getTaskList(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer());
    }

    @Override
    public Observable<MineInfo> getMineInfo() {
        return apiClient.getMineInfo().compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<OtherInfo> getOtherInfo(String def) {
        return apiClient.getOtherInfo(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<BaseList<MineManagerList.ListBean>> getMineManagerList(String def) {
        return apiClient.getMineManagerList(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> delManager(String def) {
        return apiClient.delManager(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> pubManager(String def) {
        return apiClient.pubManager(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<PersonRelation> getPersonRelation(String def) {
        return apiClient.getPersonRelation(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<BaseList<UserTaskBean>> getUserTasks(String def) {
        return apiClient.getUserTasks(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<CountBean> getUserTaskCount(String def) {
        return apiClient.getUserTaskCount(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<BaseList<UserEvaluateBean>> getUserEvaluates(String def) {
        return apiClient.getUserEvaluates(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<CountBean> getUserEvaluateCount(String def) {
        return apiClient.getUserEvaluateCount(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> getFriendsStatus(String def) {
        return apiClient.getFriendStatus(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }


    @Override
    public Observable<StatusBean> addFriend(String def) {
        return apiClient.addFriend(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> agreeFriend(String def) {
        return apiClient.agreeFriend(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> removeFriend(String def) {
        return apiClient.removeFriend(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<UserVisibleBean> getUserVisible() {
        return apiClient.getUserVisible().compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> setUserCompanyVisible(String def) {
        return apiClient.userCompanyDisplay(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> setUserEvalutionVisible(String def) {
        return apiClient.userEvalutionDisplay(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> setUserServiceVisible(String def) {
        return apiClient.userServicepowerDisplay(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> saveName(String def) {
        return apiClient.saveName(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> saveSex(String def) {
        return apiClient.saveSex(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }


    @Override
    public Observable<StatusBean> saveInfo(String def) {
        return apiClient.saveInfo(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> saveCompany(String def) {
        return apiClient.saveCompany(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<TimeStatusBean> checkTimeStatus() {
        return apiClient.checkTimeStatus().compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> setTimeStatus(String def) {
        return apiClient.setTimeStatus(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> saveHead(String def) {
        return apiClient.saveHead(RetrofitUtil.toRequestBody(def)).compose(new ErrorTransformer<>());

    }

    @Override
    public Observable<StatusBean> saveTag(String param) {
        return apiClient.savaTag(RetrofitUtil.toRequestBody(param)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> saveLocation(String param) {
        return apiClient.savaLocation(RetrofitUtil.toRequestBody(param)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<UserInfo> getUserInfo() {
        return apiClient.getUserInfo().compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<AssociateBean> getAssociate(String param) {
        return apiClient.getAssociate(RetrofitUtil.toRequestBody(param)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<StatusBean> searchData(String param) {
        return apiClient.searchData(RetrofitUtil.toRequestBody(param)).compose(new ErrorTransformer<>());
    }

    @Override
    public Observable<UploadBean> uploadUserHead(String path, OnProgressListener onProgressListener) {
        return apiClient.uploadUserHead(UpDownLoadUtil.uploadImageFile("filename", path, onProgressListener))
                .compose(new ErrorTransformer<>());
    }
}
