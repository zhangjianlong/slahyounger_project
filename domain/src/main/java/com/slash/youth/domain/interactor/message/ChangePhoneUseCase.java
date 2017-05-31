package com.slash.youth.domain.interactor.message;


import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.bean.base.ChangePhoneBean;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.repository.MessageRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class ChangePhoneUseCase extends UseCase<ChangePhoneBean> {
    MessageRepository repository;

    @Inject
    public ChangePhoneUseCase(MessageRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<ChangePhoneBean> buildUseCaseObservable() {
        return repository.changePhoneStatus(params[0]);
    }
}
