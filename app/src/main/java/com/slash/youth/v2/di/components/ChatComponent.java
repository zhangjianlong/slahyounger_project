package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.ChatModule;
import com.slash.youth.v2.feature.chat.ChatActivity;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.v2.feature.chat.list.ChatListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ChatModule.class})
public interface ChatComponent extends ActivityComponent {
    void inject(ChatActivity activity);

    void inject(ChatListFragment fragment);
}