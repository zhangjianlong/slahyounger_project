package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.MySettingModule;
import com.slash.youth.v2.feature.setting.MySettingActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, MySettingModule.class})
public interface MySettingComponent extends ActivityComponent {
    void inject(MySettingActivity activity);
}