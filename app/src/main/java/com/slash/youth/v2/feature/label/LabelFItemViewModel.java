package com.slash.youth.v2.feature.label;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.domain.bean.LabelBean;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import static android.R.attr.checked;
import static com.slash.youth.v2.util.MessageKey.LABEL_FLOW_DELETE;

/**
 * Created by acer on 2017/4/6.
 */

public class LabelFItemViewModel extends BViewModel {

    public LabelBean data;

    public int index;

    public final ReplyCommand click = new ReplyCommand(()->{
        Messenger.getDefault().send(index, MessageKey.LABEL_FLOW_DELETE);
    });

    public LabelFItemViewModel(RxAppCompatActivity activity, LabelBean data, int index) {
        super(activity);
        this.data = data;
        this.index = index;
    }
}
