package com.slash.youth.v2.feature.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.slash.youth.R;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActPaymentBinding;
import com.slash.youth.v2.di.components.DaggerPaymentComponent;
import com.slash.youth.v2.di.components.PaymentComponent;
import com.slash.youth.v2.di.modules.PaymentModule;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_payment)
public final class PaymentActivity extends BackActivity<PaymentViewModel, ActPaymentBinding> {

    PaymentComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PaymentActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerPaymentComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .paymentModule(new PaymentModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar.toolbar;
    }
}
