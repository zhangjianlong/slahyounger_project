package com.slash.youth.v2.feature.dialog.task.select;


import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.databinding.DlgSelecttaskBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import static com.slash.youth.v2.feature.main.task.list.TaskListViewModel.TASK_HISTORY;
import static com.slash.youth.v2.feature.main.task.list.TaskListViewModel.TASK_ONWAY;
import static com.slash.youth.v2.feature.main.task.list.TaskListViewModel.TASK_STUTUS;
import static com.umeng.socialize.Config.dialog;

@PerActivity
public class SelectTaskViewModel extends BDViewModel<DlgSelecttaskBinding> {

    public final ReplyCommand onWayClick = new ReplyCommand(() -> {
        Messenger.getDefault().send(TASK_ONWAY, TASK_STUTUS);
        dialog.dismiss();
    });

    public final ReplyCommand historyClick = new ReplyCommand(() -> {
        Messenger.getDefault().send(TASK_HISTORY, TASK_STUTUS);
        dialog.dismiss();
    });

    @Inject
    public SelectTaskViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}