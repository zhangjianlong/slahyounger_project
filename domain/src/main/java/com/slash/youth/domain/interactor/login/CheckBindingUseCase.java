package com.slash.youth.domain.interactor.login;

import com.slash.youth.domain.bean.ResCodeBean;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.repository.LoginRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by lenovo on 2017/3/30.
 */

public class CheckBindingUseCase extends UseCase<ResCodeBean> {
    LoginRepository repository;


    @Inject
    public CheckBindingUseCase(LoginRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }


    @Override
    protected Observable<ResCodeBean> buildUseCaseObservable() {
        return repository.checkBindng(params[0]);
    }
}
