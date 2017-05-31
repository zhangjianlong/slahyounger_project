package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.ProfileModule;
import com.slash.youth.v2.feature.profile.ProfileActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ProfileModule.class})
public interface ProfileComponent extends ActivityComponent {
    void inject(ProfileActivity activity);
}