package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.RefundModule;
import com.slash.youth.v2.feature.refund.RefundActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, RefundModule.class})
public interface RefundComponent extends ActivityComponent {
    void inject(RefundActivity activity);
}