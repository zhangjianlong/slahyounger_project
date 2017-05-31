package com.slash.youth.v2.feature.dialog.message;


import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.DlgDelmessageBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class DelMessageViewModel extends BDViewModel<DlgDelmessageBinding> {
    public ReplyCommand cancel = new ReplyCommand(() -> {
        dialog.dismiss();
    });

    public ReplyCommand confim = new ReplyCommand(() -> {
        if (onDialogLisetener != null) {
            dialog.dismiss();
            onDialogLisetener.onConfirm();
        }
    });

    @Inject
    public DelMessageViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}