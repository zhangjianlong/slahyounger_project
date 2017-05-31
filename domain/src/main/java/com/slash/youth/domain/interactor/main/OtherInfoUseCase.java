package com.slash.youth.domain.interactor.main;


import com.slash.youth.domain.bean.MineInfo;
import com.slash.youth.domain.bean.OtherInfo;
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
public class OtherInfoUseCase extends UseCase<OtherInfo> {
    MainRepository repository;

    @Inject
    public OtherInfoUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<OtherInfo> buildUseCaseObservable() {
        return repository.getOtherInfo(params[0]);
    }
}
