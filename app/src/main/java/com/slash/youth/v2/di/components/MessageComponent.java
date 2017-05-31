package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.MessageModule;
import com.slash.youth.v2.feature.message.MessageActivity;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.v2.feature.message.list.MListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, MessageModule.class})
public interface MessageComponent extends ActivityComponent {
    void inject(MessageActivity activity);

    void inject(MListFragment fragment);

}