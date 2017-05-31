package com.slash.youth.v2.feature.message;


import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.R;
import com.slash.youth.databinding.ActMessageBinding;
import com.slash.youth.v2.feature.message.list.MListFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class MessageViewModel extends BAViewModel<ActMessageBinding> {

    public ReplyCommand click = new ReplyCommand(() -> {
        activity.finish();
    });

    @Inject
    public MessageViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        addFragment(R.id.fl_container, new MListFragment());
    }
}