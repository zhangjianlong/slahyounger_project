package com.slash.youth.domain.interactor.main;


import com.slash.youth.domain.bean.UserEvaluateBean;
import com.slash.youth.domain.bean.UserTaskBean;
import com.slash.youth.domain.bean.base.BaseList;
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
public class UserEvaluateUseCase extends UseCase<BaseList<UserEvaluateBean>> {
    MainRepository repository;

    @Inject
    public UserEvaluateUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<BaseList<UserEvaluateBean>> buildUseCaseObservable() {
        return repository.getUserEvaluates(params[0]);
    }
}
