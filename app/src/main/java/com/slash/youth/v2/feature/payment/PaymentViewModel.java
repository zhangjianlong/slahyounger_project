package com.slash.youth.v2.feature.payment;


import android.databinding.ObservableField;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.CompatUtil;
import com.slash.youth.R;
import com.slash.youth.databinding.ActPaymentBinding;
import com.slash.youth.utils.CommonUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class PaymentViewModel extends BAViewModel<ActPaymentBinding> {
    public ObservableField<String> title = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_payment_title));

    @Inject
    public PaymentViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }
}