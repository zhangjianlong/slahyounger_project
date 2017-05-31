package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.PersonalEditModule;
import com.slash.youth.v2.feature.edit.PersonalEditActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, PersonalEditModule.class})
public interface PersonalEditComponent extends ActivityComponent {
    void inject(PersonalEditActivity activity);
}