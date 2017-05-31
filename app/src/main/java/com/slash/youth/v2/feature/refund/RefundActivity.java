package com.slash.youth.v2.feature.refund;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActRefundBinding;
import com.slash.youth.v2.di.components.DaggerRefundComponent;
import com.slash.youth.v2.di.components.RefundComponent;
import com.slash.youth.v2.di.modules.RefundModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_refund)
public final class RefundActivity extends BaseActivity<RefundViewModel, ActRefundBinding> {

    RefundComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, RefundActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerRefundComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .refundModule(new RefundModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
