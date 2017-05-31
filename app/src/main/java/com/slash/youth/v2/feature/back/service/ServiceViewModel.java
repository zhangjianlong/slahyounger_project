package com.slash.youth.v2.feature.back.service;


import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.FrgServiceBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class ServiceViewModel extends BFViewModel<FrgServiceBinding> {

    @Inject
    public ServiceViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }
}