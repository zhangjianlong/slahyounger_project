package com.slash.youth.domain.interactor.login;


import com.slash.youth.domain.bean.LoginResult;
import com.slash.youth.domain.bean.PhoneLoginResultBean;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.repository.LoginRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class PhoneLoginUseCase extends UseCase<PhoneLoginResultBean> {
    LoginRepository repository;

    @Inject
    public PhoneLoginUseCase(LoginRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }


    @Override
    protected Observable<PhoneLoginResultBean> buildUseCaseObservable() {
        return repository.phoneLogin(params[0]);
    }
}
