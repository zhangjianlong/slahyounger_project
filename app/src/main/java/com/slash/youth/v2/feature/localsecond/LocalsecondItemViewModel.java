package com.slash.youth.v2.feature.localsecond;

import android.os.Bundle;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.domain.bean.ProvinceBean;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by acer on 2017/4/6.
 */

public class LocalsecondItemViewModel extends BViewModel {

    public String name;

    public String province;

    public ReplyCommand click = new ReplyCommand(() -> {
        Messenger.getDefault().send(province + "-" + name, MessageKey.PUB_CITY_SELECTED);
        activity.finish();
    });

    public LocalsecondItemViewModel(RxAppCompatActivity activity, String name, String province) {
        super(activity);
        this.name = name;
        this.province = province;
    }
}
