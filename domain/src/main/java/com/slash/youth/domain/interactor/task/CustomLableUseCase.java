package com.slash.youth.domain.interactor.task;


import com.slash.youth.domain.bean.LabelBean;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.repository.TaskRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class CustomLableUseCase extends UseCase<List<LabelBean>> {
    TaskRepository repository;

    @Inject
    public CustomLableUseCase(TaskRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<List<LabelBean>> buildUseCaseObservable() {
        return repository.getCustomLables("a");
    }
}
