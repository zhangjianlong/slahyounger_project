package com.slash.youth.domain.interactor.main;


import com.slash.youth.domain.bean.CountBean;
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
public class UserEvaluateCountUseCase extends UseCase<CountBean> {
    MainRepository repository;

    @Inject
    public UserEvaluateCountUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<CountBean> buildUseCaseObservable() {
        return repository.getUserEvaluateCount(params[0]);
    }
}
