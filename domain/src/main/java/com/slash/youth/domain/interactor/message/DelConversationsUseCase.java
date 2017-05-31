package com.slash.youth.domain.interactor.message;


import com.slash.youth.domain.bean.ConversationBean;
import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.bean.base.BaseList;
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
public class DelConversationsUseCase extends UseCase<StatusBean> {
    MessageRepository repository;

    @Inject
    public DelConversationsUseCase(MessageRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<StatusBean> buildUseCaseObservable() {
        return repository.delConversation(params[0]);
    }
}
