package com.slash.youth.v2.feature.dialog.find;


import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.DlgPubBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class PubViewModel extends BDViewModel<DlgPubBinding> {


    public ReplyCommand cancel = new ReplyCommand(() -> {
        dialog.dismiss();
    });

    public ReplyCommand pubDemandClick = new ReplyCommand(() -> {

    });

    public ReplyCommand pubServerClick = new ReplyCommand(() -> {

    });

    @Inject
    public PubViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}