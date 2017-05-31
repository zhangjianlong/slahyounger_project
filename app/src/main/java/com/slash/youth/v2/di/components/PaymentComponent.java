package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.PaymentModule;
import com.slash.youth.v2.feature.payment.PaymentActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, PaymentModule.class})
public interface PaymentComponent extends ActivityComponent {
    void inject(PaymentActivity activity);
}