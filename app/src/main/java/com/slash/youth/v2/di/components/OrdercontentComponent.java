package com.slash.youth.v2.di.components;

import com.core.op.lib.di.PerActivity;
import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.MySettingModule;
import com.slash.youth.v2.di.modules.OrdercontentModule;
import com.slash.youth.v2.feature.fragment.ordercontent.OrdercontentFragment;

import dagger.Component;

/**
 * Created by op on 2017/4/15.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, OrdercontentModule.class})
public interface OrdercontentComponent extends ActivityComponent  {

    void inject(OrdercontentFragment fragment);
}
