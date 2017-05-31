package com.slash.youth.http.protocol;

import com.google.gson.Gson;
import com.slash.youth.domain.CommonResultBean;
import com.slash.youth.global.GlobalConstants;

import org.xutils.http.RequestParams;

/**
 * Created by zhouyifeng on 2016/12/23.
 */
public class ChangePhoneProtocol extends BaseProtocol<CommonResultBean> {

    private String agree;
    private String relationTitle;
    private String uid;


    public ChangePhoneProtocol(String uid, String agree, String relationTitle) {
        this.uid = uid;
        this.agree = agree;
        this.relationTitle = relationTitle;
    }

    @Override
    public String getUrlString() {
        return GlobalConstants.HttpUrl.AGREE_CHANGE_CONTACT_WEB;
    }

    @Override
    public void addRequestParams(RequestParams params) {
        params.addBodyParameter("relationTitle", relationTitle);
        params.addBodyParameter("agree", agree);
        params.addBodyParameter("uid", uid);
    }

    @Override
    public CommonResultBean parseData(String result) {
        Gson gson = new Gson();
        CommonResultBean commonResultBean = gson.fromJson(result, CommonResultBean.class);
        return commonResultBean;
    }

    @Override
    public boolean checkJsonResult(String result) {
        //这个接口不能在这里做判断
        return true;
    }
}
