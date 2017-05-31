package com.slash.youth.v2.feature.dialog.task.pub;


import android.content.Intent;

import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.DlgPubtaskBinding;
import com.slash.youth.ui.activity.PublishDemandBaseInfoActivity;
import com.slash.youth.ui.activity.PublishServiceBaseInfoActivity;
import com.slash.youth.utils.CommonUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class PubTaskViewModel extends BDViewModel<DlgPubtaskBinding> {

    public final ReplyCommand close = new ReplyCommand(() -> {
        dialog.dismiss();
    });

    public final ReplyCommand demandClick = new ReplyCommand(() -> {
        Intent intentPublishDemandBaseInfoActivity = new Intent(CommonUtils.getContext(), PublishDemandBaseInfoActivity.class);
        activity.startActivity(intentPublishDemandBaseInfoActivity);
        dialog.dismiss();
    });

    public final ReplyCommand serviceClick = new ReplyCommand(() -> {
        Intent intentPublishServiceBaseInfoActivity = new Intent(CommonUtils.getContext(), PublishServiceBaseInfoActivity.class);
        activity.startActivity(intentPublishServiceBaseInfoActivity);
        dialog.dismiss();
    });


    @Inject
    public PubTaskViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}