package com.slash.youth.domain.interactor.main;


import com.slash.youth.domain.bean.MineInfo;
import com.slash.youth.domain.bean.TaskList;
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
public class MineInfoUseCase extends UseCase<MineInfo> {
    MainRepository repository;

    @Inject
    public MineInfoUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<MineInfo> buildUseCaseObservable() {
        return repository.getMineInfo();
    }
}
