package com.slash.youth.domain.interactor.task;


import com.slash.youth.domain.bean.LabelBean;
import com.slash.youth.domain.bean.ResCodeBean;
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
public class DeleteLableUseCase extends UseCase {
    TaskRepository repository;

    @Inject
    public DeleteLableUseCase(TaskRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.deleteLable(params[0]);
    }
}
