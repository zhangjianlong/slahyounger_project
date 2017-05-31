package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.LocalsecondModule;
import com.slash.youth.v2.feature.localsecond.LocalsecondActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, LocalsecondModule.class})
public interface LocalsecondComponent extends ActivityComponent {
    void inject(LocalsecondActivity activity);
}