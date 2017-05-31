package com.slash.youth.v2.feature.local;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.R;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by acer on 2017/4/6.
 */

public class LocalHotItemViewModel extends BViewModel {

    public String name;
    String province;
    public ReplyCommand click = new ReplyCommand(() -> {
        Messenger.getDefault().send(province + "-" + name, MessageKey.PUB_CITY_SELECTED);
        activity.finish();
    });

    public LocalHotItemViewModel(RxAppCompatActivity activity, String name, String province) {
        super(activity);
        this.name = name;
        this.province = province;
    }
}
