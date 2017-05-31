package com.slash.youth.domain.interactor.main;


import com.slash.youth.domain.bean.BannerConfigBean;
import com.slash.youth.domain.bean.PersonRelation;
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
public class PersonRelationUseCase extends UseCase<PersonRelation> {
    MainRepository repository;

    @Inject
    public PersonRelationUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<PersonRelation> buildUseCaseObservable() {
        return repository.getPersonRelation("{\"a\":\"a\"}");
    }
}
