package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.PubModule;
import com.slash.youth.v2.feature.pub.PubActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, PubModule.class})
public interface PubComponent extends ActivityComponent {
    void inject(PubActivity activity);
}