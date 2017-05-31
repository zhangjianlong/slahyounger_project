package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.BindingModule;
import com.slash.youth.v2.feature.bindingaccount.BindingActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, BindingModule.class})
public interface BindingComponent extends ActivityComponent {
    void inject(BindingActivity activity);
}