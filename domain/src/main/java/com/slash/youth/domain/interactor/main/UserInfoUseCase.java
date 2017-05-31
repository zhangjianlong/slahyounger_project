package com.slash.youth.domain.interactor.main;


import com.slash.youth.domain.bean.OtherInfo;
import com.slash.youth.domain.bean.UserInfo;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.repository.MainRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class UserInfoUseCase extends UseCase<UserInfo> {
    MainRepository repository;

    @Inject
    public UserInfoUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<UserInfo> buildUseCaseObservable() {
        return repository.getUserInfo();
    }
}
