package com.slash.youth.http.protocol;

import com.google.gson.Gson;
import com.slash.youth.domain.CommonResultBean;
import com.slash.youth.global.GlobalConstants;

import org.xutils.http.RequestParams;

/**
 * 八、[需求]-服务方拒绝
 * Created by zhouyifeng on 2016/10/8.
 */
public class ServicePartyRejectProtocol extends BaseProtocol<CommonResultBean> {

    private String id;// 需求ID

    public ServicePartyRejectProtocol(String id) {
        this.id = id;
    }

    @Override
    public String getUrlString() {
        return GlobalConstants.HttpUrl.SERVICE_PARTY_REJECT;
    }

    @Override
    public void addRequestParams(RequestParams params) {
        params.addBodyParameter("id", id);
    }


    @Override
    public CommonResultBean parseData(String result) {
        return commonResultBean;
    }

    CommonResultBean commonResultBean;

    @Override
    public boolean checkJsonResult(String result) {
        Gson gson = new Gson();
        commonResultBean = gson.fromJson(result, CommonResultBean.class);
        if (commonResultBean.rescode == 0) {
            if (commonResultBean.data.status == 1) {
                return true;
            }
        }
        return false;
    }
}
