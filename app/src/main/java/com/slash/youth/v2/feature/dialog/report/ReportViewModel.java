package com.slash.youth.v2.feature.dialog.report;


import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.databinding.DlgReportBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import static com.slash.youth.v2.feature.main.task.list.TaskListViewModel.TASK_HISTORY;
import static com.slash.youth.v2.feature.main.task.list.TaskListViewModel.TASK_ONWAY;
import static com.slash.youth.v2.feature.main.task.list.TaskListViewModel.TASK_STUTUS;

@PerActivity
public class ReportViewModel extends BDViewModel<DlgReportBinding> {

    public final ReplyCommand click = new ReplyCommand(() -> {
        if (onDialogLisetener != null) {
            onDialogLisetener.onConfirm();
        }
        dialog.dismiss();
    });

    @Inject
    public ReportViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}