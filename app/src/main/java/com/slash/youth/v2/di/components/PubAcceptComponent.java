package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.PubAcceptModule;
import com.slash.youth.v2.feature.pubaccept.PubAcceptActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, PubAcceptModule.class})
public interface PubAcceptComponent extends ActivityComponent {
    void inject(PubAcceptActivity activity);
}