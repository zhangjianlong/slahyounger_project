package com.slash.youth.v2.feature.refund;


import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.R;
import com.slash.youth.databinding.ActRefundBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class RefundViewModel extends BAViewModel<ActRefundBinding> {

    public String title;

    @Inject
    public RefundViewModel(RxAppCompatActivity activity) {
        super(activity);
        title = activity.getString(R.string.app_refund_title);
    }

    @Override
    public void afterViews() {

    }
}