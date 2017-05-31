package com.slash.youth.http.protocol;

import com.google.gson.Gson;
import com.slash.youth.domain.CustomerServiceBean;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.utils.SpUtils;

import org.xutils.http.RequestParams;

/**
 * 请求客服
 * <p/>
 * Created by zhouyifeng on 2017/1/24.
 */
public class AskCustomerServiceProtocol extends BaseProtocol<CustomerServiceBean> {
    @Override
    public String getUrlString() {
        return GlobalConstants.HttpUrl.GET_CUSTOMER_SERVICE;
    }

    @Override
    public void addRequestParams(RequestParams params) {
        params.addBodyParameter("uid", SpUtils.getLong("uid", 0l) + "");//参数必须加一个，不然服务端会报错
    }

    @Override
    public CustomerServiceBean parseData(String result) {
        MsgManager.setCustomerServiceUidToSp(customerServiceBean.data.uid);
        return customerServiceBean;
    }

    CustomerServiceBean customerServiceBean;

    @Override
    public boolean checkJsonResult(String result) {
        Gson gson = new Gson();
        customerServiceBean = gson.fromJson(result, CustomerServiceBean.class);
        if (customerServiceBean.rescode == 0) {
            return true;
        } else {
            return false;
        }
    }
}
