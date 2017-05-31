package com.slash.youth.v2.di.components;

import com.core.op.lib.di.PerActivity;
import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.SimpleBackModule;
import com.slash.youth.v2.feature.back.SimpleBackActivity;
import com.slash.youth.v2.feature.back.follow.FollowFragment;
import com.slash.youth.v2.feature.back.manage.ManagerFragment;
import com.slash.youth.v2.feature.back.service.ServiceFragment;
import com.slash.youth.v2.feature.back.service.slist.SListFragment;
import com.slash.youth.v2.feature.back.servicedetail.ServiceDetailFragment;
import com.slash.youth.v2.feature.fragment.ordercontent.OrdercontentFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, SimpleBackModule.class})
public interface SimpleBackComponent extends ActivityComponent {
    void inject(SimpleBackActivity activity);

    void inject(ManagerFragment fragment);

    void inject(ServiceFragment fragment);

    void inject(SListFragment fragment);

    void inject(ServiceDetailFragment fragment);

    void inject(FollowFragment fragment);
}