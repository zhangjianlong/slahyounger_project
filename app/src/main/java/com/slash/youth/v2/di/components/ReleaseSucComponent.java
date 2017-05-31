package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.ReleaseSucModule;
import com.slash.youth.v2.feature.release.ReleaseSucActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ReleaseSucModule.class})
public interface ReleaseSucComponent extends ActivityComponent {
    void inject(ReleaseSucActivity activity);
}