package com.slash.youth.data;

import com.slash.youth.data.api.BaseResponse;
import com.slash.youth.domain.bean.AssociateBean;
import com.slash.youth.domain.bean.BannerConfigBean;
import com.slash.youth.domain.bean.ConversationBean;
import com.slash.youth.domain.bean.CountBean;
import com.slash.youth.domain.bean.CustomerService;
import com.slash.youth.domain.bean.FindDemand;
import com.slash.youth.domain.bean.FindServices;
import com.slash.youth.domain.bean.HomeTagInfoBean;
import com.slash.youth.domain.bean.LabelBean;
import com.slash.youth.domain.bean.MineInfo;
import com.slash.youth.domain.bean.MineManagerList;
import com.slash.youth.domain.bean.OtherInfo;
import com.slash.youth.domain.bean.PersonRelation;
import com.slash.youth.domain.bean.PhoneLoginResultBean;
import com.slash.youth.domain.bean.ResCodeBean;
import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.bean.TaskList;
import com.slash.youth.domain.bean.TimeStatusBean;
import com.slash.youth.domain.bean.UploadBean;
import com.slash.youth.domain.bean.UserEvaluateBean;
import com.slash.youth.domain.bean.UserInfo;
import com.slash.youth.domain.bean.UserTaskBean;
import com.slash.youth.domain.bean.UserVisibleBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.bean.base.ChangePhoneBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/8/8
 */

public interface ApiClient {

    @POST(UriMethod.GET_CUSTOMER_SERVICE)
    Observable<BaseResponse<CustomerService>> getCustomService(@Body RequestBody requestBody);

    @POST(UriMethod.TOKEN_LOGIN)
    Observable<BaseResponse<PhoneLoginResultBean>> login(@Body RequestBody requestBody);

    @POST(UriMethod.GET_BANNER_CONFIG)
    Observable<BannerConfigBean> getBanners(@Body RequestBody requestBody);

    @POST(UriMethod.HOME_TAG_CONFIG)
    Observable<HomeTagInfoBean> getTags(@Body RequestBody requestBody);

    @POST(UriMethod.GET_RECOMMEND_SERVICE2)
    Observable<BaseResponse<FindServices>> getFindServices(@Body RequestBody requestBody);

    @POST(UriMethod.GET_RECOMMEND_DEMAND2)
    Observable<BaseResponse<FindDemand>> getFindDemand(@Body RequestBody requestBody);

    @POST(UriMethod.GET_MY_TASK_LIST)
    Observable<BaseResponse<BaseList<TaskList.TaskBean>>> getTaskList(@Body RequestBody requestBody);


    @POST(UriMethod.MY_INFO)
    Observable<BaseResponse<MineInfo>> getMineInfo();


    @POST(UriMethod.MY_USERINFO)
    Observable<BaseResponse<OtherInfo>> getOtherInfo(@Body RequestBody requestBody);


    @POST(UriMethod.MANAGE_PUBLISH_LIST)
    Observable<BaseResponse<BaseList<MineManagerList.ListBean>>> getMineManagerList(@Body RequestBody requestBody);

    @POST(UriMethod.SKILL_MANAGE_DELETE)
    Observable<BaseResponse<StatusBean>> delManager(@Body RequestBody requestBody);


    @POST(UriMethod.UP_AND_DOWN_TASK)
    Observable<BaseResponse<StatusBean>> pubManager(@Body RequestBody requestBody);

    @POST(UriMethod.PERSON_RELATION_FIRST_PAGER)
    Observable<BaseResponse<PersonRelation>> getPersonRelation(@Body RequestBody requestBody);

    @POST(UriMethod.GET_CONVERSATION_LIST)
    Observable<BaseResponse<BaseList<ConversationBean>>> getConversationList(@Body RequestBody requestBody);

    @POST(UriMethod.DEL_CONVERSATION_LIST)
    Observable<BaseResponse<StatusBean>> delConversation(@Body RequestBody requestBody);

    @POST(UriMethod.GET_ADD_FRIEND_STATUS)
    Observable<BaseResponse<StatusBean>> friendStatus(@Body RequestBody requestBody);

    @POST(UriMethod.GET_IS_CHANGE_CONTACT)
    Observable<BaseResponse<ChangePhoneBean>> changePhoneStatus(@Body RequestBody requestBody);

    @POST(UriMethod.SEND_PHONE_VERIFICATION_CODE)
    Observable<ResCodeBean> getVerifyCode(@Body RequestBody requestBody);

    @POST(UriMethod.PHONE_NUMBER_LOGIN)
    Observable<PhoneLoginResultBean> phoneLogin(@Body RequestBody requestBody);

    @POST(UriMethod.NEW_DEMAND_ANDSERVICE_LIST)
    Observable<BaseResponse<BaseList<UserTaskBean>>> getUserTasks(@Body RequestBody requestBody);

    @POST(UriMethod.NEW_DEMAND_ANDSERVICE_COUNT)
    Observable<BaseResponse<CountBean>> getUserTaskCount(@Body RequestBody requestBody);

    @POST(UriMethod.SHARE_TO)
    Observable<BaseResponse<BaseList<UserEvaluateBean>>> getUserEvaluates(@Body RequestBody requestBody);

    @POST(UriMethod.SHARE_TO_COUNT)
    Observable<BaseResponse<CountBean>> getUserEvaluateCount(@Body RequestBody requestBody);

    @POST(UriMethod.CHECK_FRIEND_STATUS)
    Observable<BaseResponse<StatusBean>> getFriendStatus(@Body RequestBody requestBody);

    @POST(UriMethod.ADD_FRIEND_RELATION)
    Observable<BaseResponse<StatusBean>> addFriend(@Body RequestBody requestBody);

    @POST(UriMethod.AGREE_FRIEND_RELATION)
    Observable<BaseResponse<StatusBean>> agreeFriend(@Body RequestBody requestBody);

    @POST(UriMethod.DELETE_FRIEND_RELATION)
    Observable<BaseResponse<StatusBean>> removeFriend(@Body RequestBody requestBody);

    @POST(UriMethod.CHECK_ACCOUNT_BINDING)
    Observable<ResCodeBean> checkBinding(@Body RequestBody requestBody);

    @Multipart
    @POST(UriMethod.IMG_UPLOAD)
    Observable<BaseResponse<UploadBean>> uploadUserHead(@Part MultipartBody.Part file);

    @POST(UriMethod.USER_VISIBLE)
    Observable<BaseResponse<UserVisibleBean>> getUserVisible();

    @POST(UriMethod.USER_COMPANY_DISPLAY)
    Observable<BaseResponse<StatusBean>> userCompanyDisplay(@Body RequestBody requestBody);

    @POST(UriMethod.USER_EVALUTION_DISPLAY)
    Observable<BaseResponse<StatusBean>> userEvalutionDisplay(@Body RequestBody requestBody);

    @POST(UriMethod.USER_SERVICEPOWER_DISPLAY)
    Observable<BaseResponse<StatusBean>> userServicepowerDisplay(@Body RequestBody requestBody);

    @POST(UriMethod.SAVE_NAME)
    Observable<BaseResponse<StatusBean>> saveName(@Body RequestBody requestBody);

    @POST(UriMethod.SAVE_SEX)
    Observable<BaseResponse<StatusBean>> saveSex(@Body RequestBody requestBody);

    @POST(UriMethod.SAVE_INFO)
    Observable<BaseResponse<StatusBean>> saveInfo(@Body RequestBody requestBody);

    @POST(UriMethod.SAVE_COMPANY)
    Observable<BaseResponse<StatusBean>> saveCompany(@Body RequestBody requestBody);

    @POST(UriMethod.SET_TIME_GET)
    Observable<BaseResponse<TimeStatusBean>> checkTimeStatus();

    @POST(UriMethod.SET_TIME_SET)
    Observable<BaseResponse<StatusBean>> setTimeStatus(@Body RequestBody requestBody);

    @POST(UriMethod.LOGNI_GET_TAG)
    Observable<List<LabelBean>> getLables(@Body RequestBody requestBody);

    @POST(UriMethod.SKILLLABEL_CREATE)
    Observable<BaseResponse> createLable(@Body RequestBody requestBody);

    @POST(UriMethod.SKILLLABEL_DELETE)
    Observable<BaseResponse> deleteLable(@Body RequestBody requestBody);

    @POST(UriMethod.SKILLLABEL_GET)
    Observable<List<LabelBean>> getCustomLables(@Body RequestBody requestBody);

    @POST(UriMethod.SAVE_USER_HEAD)
    Observable<BaseResponse<StatusBean>> saveHead(@Body RequestBody requestBody);

    @POST(UriMethod.SAVE_USER_TAG)
    Observable<BaseResponse<StatusBean>> savaTag(@Body RequestBody requestBody);

    @POST(UriMethod.SAVE_USER_LOCATION)
    Observable<BaseResponse<StatusBean>> savaLocation(@Body RequestBody requestBody);

    @POST(UriMethod.GET_USERINFO)
    Observable<BaseResponse<UserInfo>> getUserInfo();


    @POST(UriMethod.SEARCH_ASSOCIATIVE)
    Observable<BaseResponse<AssociateBean>> getAssociate(@Body RequestBody requestBody);


    @POST(UriMethod.SEARCH_DATA)
    Observable<BaseResponse<StatusBean>> searchData(@Body RequestBody requestBody);

}

