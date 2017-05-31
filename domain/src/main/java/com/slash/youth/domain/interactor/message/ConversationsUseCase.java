package com.slash.youth.domain.interactor.message;


import com.slash.youth.domain.bean.BannerConfigBean;
import com.slash.youth.domain.bean.ConversationBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.repository.MainRepository;
import com.slash.youth.domain.repository.MessageRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class ConversationsUseCase extends UseCase<BaseList<ConversationBean>> {
    MessageRepository repository;

    @Inject
    public ConversationsUseCase(MessageRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<BaseList<ConversationBean>> buildUseCaseObservable() {
        return repository.getConversationList(params[0]);
    }
}
