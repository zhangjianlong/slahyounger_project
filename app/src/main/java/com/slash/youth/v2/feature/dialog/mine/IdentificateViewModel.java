package com.slash.youth.v2.feature.dialog.mine;


import android.content.Intent;
import android.view.View;

import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.DlgIdentificateBinding;
import com.slash.youth.engine.UserInfoEngine;
import com.slash.youth.ui.activity.UserinfoEditorActivity;
import com.slash.youth.utils.CommonUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class IdentificateViewModel extends BDViewModel<DlgIdentificateBinding> {

    public ReplyCommand confim = new ReplyCommand(() -> {
        if (onDialogLisetener != null) {
            onDialogLisetener.onConfirm();
        }
    });

    public ReplyCommand cancel = new ReplyCommand(() -> {
        dialog.dismiss();
    });

    @Inject
    public IdentificateViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}