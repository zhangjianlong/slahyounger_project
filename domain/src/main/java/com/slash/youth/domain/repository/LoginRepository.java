package com.slash.youth.domain.repository;

import com.slash.youth.domain.bean.CustomerService;
import com.slash.youth.domain.bean.PhoneLoginResultBean;
import com.slash.youth.domain.bean.ResCodeBean;
import rx.Observable;

/**
 * Created by acer on 2017/3/4.
 */

public interface LoginRepository {

    Observable<CustomerService> getCustomService(String def);

    Observable<PhoneLoginResultBean> login(String def);

    Observable<ResCodeBean> getVerifyCode(String def);

    Observable<PhoneLoginResultBean> phoneLogin(String def);

    Observable<ResCodeBean> checkBindng(String def);
}
