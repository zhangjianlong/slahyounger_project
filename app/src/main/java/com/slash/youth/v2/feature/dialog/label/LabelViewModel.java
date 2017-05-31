package com.slash.youth.v2.feature.dialog.label;


import android.databinding.ObservableField;

import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.databinding.DlgLabelBinding;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class LabelViewModel extends BDViewModel<DlgLabelBinding> {

    public ObservableField<String> content = new ObservableField<>();

    public ReplyCommand confim = new ReplyCommand(()->{
        Messenger.getDefault().send(content.get(), MessageKey.LABEL_NEW);
    });

    public ReplyCommand cancel = new ReplyCommand(()->{
        dialog.dismiss();
    });

    @Inject
    public LabelViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

}