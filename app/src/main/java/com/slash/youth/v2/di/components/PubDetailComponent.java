package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.PubDetailModule;
import com.slash.youth.v2.feature.task.PubDetailActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, PubDetailModule.class})
public interface PubDetailComponent extends ActivityComponent {
    void inject(PubDetailActivity activity);
}