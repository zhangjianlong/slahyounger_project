package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.UserInfoModule;
import com.slash.youth.v2.feature.userinfo.UserInfoActivity;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.v2.feature.userinfo.tab.UserInfoTabFragment;
import com.slash.youth.v2.feature.userinfo.tab.about.AboutFragment;
import com.slash.youth.v2.feature.userinfo.tab.evaluate.EvaluateFragment;
import com.slash.youth.v2.feature.userinfo.tab.task.UserTaskFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, UserInfoModule.class})
public interface UserInfoComponent extends ActivityComponent {
    void inject(UserInfoActivity activity);

    void inject(UserInfoTabFragment fragment);

    void inject(AboutFragment fragment);

    void inject(UserTaskFragment fragment);

    void inject(EvaluateFragment fragment);
}