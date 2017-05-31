package com.slash.youth.domain.interactor.main;


import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.repository.MainRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author zjl
 * @createDate 2017/4/10
 * @description
 */

public class UserServiceVisibleUseCase extends UseCase<StatusBean> {
    MainRepository repository;

    @Inject
    public UserServiceVisibleUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<StatusBean> buildUseCaseObservable() {
        return repository.setUserServiceVisible(params[0]);
    }
}

 

