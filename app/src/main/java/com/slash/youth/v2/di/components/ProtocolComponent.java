package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.ProtocolModule;
import com.slash.youth.v2.feature.protocol.ProtocolActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ProtocolModule.class})
public interface ProtocolComponent extends ActivityComponent {
    void inject(ProtocolActivity activity);
}