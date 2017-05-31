package com.slash.youth.v2.di.components;

import dagger.Component;


import com.core.op.lib.weight.progress.Indeterminate;
import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.MainModule;
import com.slash.youth.v2.feature.main.MainActivity;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.v2.feature.main.find.FindFragment;
import com.slash.youth.v2.feature.main.mine.MineFragment;
import com.slash.youth.v2.feature.main.order.OrderFragment;
import com.slash.youth.v2.feature.main.order.history.HistoryFragment;
import com.slash.youth.v2.feature.main.order.indent.IndentFragment;
import com.slash.youth.v2.feature.main.order.mission.MissionFragment;
import com.slash.youth.v2.feature.main.task.TaskFragment;
import com.slash.youth.v2.feature.main.task.list.TaskListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent extends ActivityComponent {

    void inject(MainActivity activity);

    void inject(FindFragment fragment);

    void inject(TaskFragment fragment);

    void inject(TaskListFragment fragment);

    void inject(MineFragment fragment);

    void inject(OrderFragment fragment);

    void inject(HistoryFragment fragment);

    void inject(IndentFragment fragment);

    void inject(MissionFragment fragment);


}