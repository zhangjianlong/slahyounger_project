package com.slash.youth.v2.feature.local;

import android.content.Intent;
import android.os.Bundle;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.slash.youth.domain.bean.ProvinceBean;
import com.slash.youth.v2.feature.localsecond.LocalsecondActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by acer on 2017/4/6.
 */

public class LocalItemViewModel extends BViewModel {

    public ProvinceBean provinceBean;

    public ReplyCommand click = new ReplyCommand(() -> {
        Bundle bundle = new Bundle();
        bundle.putSerializable("province", provinceBean);
        LocalsecondActivity.instance(activity, bundle);

//        LocalsecondActivity.instance(activity);
    });

    public LocalItemViewModel(RxAppCompatActivity activity, ProvinceBean provinceBean) {
        super(activity);
        this.provinceBean = provinceBean;
    }
}
