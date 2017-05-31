package com.slash.youth.v2.feature.chat;


import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.R;
import com.slash.youth.databinding.ActChatBinding;
import com.slash.youth.v2.feature.chat.list.ChatListFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class ChatViewModel extends BAViewModel<ActChatBinding> {

    public ReplyCommand changePhone = new ReplyCommand(() -> {

    });

    public ReplyCommand addFriend = new ReplyCommand(() -> {

    });

    public String title;

    @Inject
    public ChatViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        addFragment(R.id.fl_container, ChatListFragment.instance());
    }
}