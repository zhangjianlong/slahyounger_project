package com.slash.youth.domain.interactor.main;


import com.slash.youth.domain.bean.StatusBean;
import com.slash.youth.domain.bean.UploadBean;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.interfaces.OnProgressListener;
import com.slash.youth.domain.repository.MainRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/11/14
 */
public class UserHeadUseCase extends UseCase<UploadBean> {
    MainRepository repository;

    @Inject
    public UserHeadUseCase(MainRepository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Observable<UploadBean> buildUseCaseObservable() {
        return repository.uploadUserHead(params[0], onProgressListener);
    }
}
