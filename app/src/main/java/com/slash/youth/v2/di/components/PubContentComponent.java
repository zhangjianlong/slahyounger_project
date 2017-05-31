package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.PubContentModule;
import com.slash.youth.v2.feature.pubcontent.PubContentActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, PubContentModule.class})
public interface PubContentComponent extends ActivityComponent {
    void inject(PubContentActivity activity);
}