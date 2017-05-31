package com.slash.youth.v2.feature.dialog.manage;


import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.databinding.DlgDelmanagerBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import static com.slash.youth.v2.util.MessageKey.DEL_MANAGER;

public class DelManagerViewModel extends BDViewModel<DlgDelmanagerBinding> {

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
    public DelManagerViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}