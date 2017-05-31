package com.slash.youth.v2.di.components;

import com.core.op.lib.di.PerActivity;
import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.OrdercontentModule;
import com.slash.youth.v2.di.modules.TaskcontentModule;
import com.slash.youth.v2.feature.fragment.ordercontent.OrdercontentFragment;
import com.slash.youth.v2.feature.fragment.taskcontent.TaskcontentFragment;

import dagger.Component;

/**
 * Created by op on 2017/4/15.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, TaskcontentModule.class})
public interface TaskcontentComponent extends ActivityComponent  {

    void inject(TaskcontentFragment fragment);
}
