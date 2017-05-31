package com.slash.youth.v2.feature.chat.list;

import com.slash.youth.R;
import com.slash.youth.databinding.FrgChatlistBinding;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.v2.di.components.ChatComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_chatlist)
public final class ChatListFragment extends BaseFragment<ChatListViewModel, FrgChatlistBinding> {

    public static ChatListFragment instance() {
        return new ChatListFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(ChatComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
