package com.slash.youth.v2.feature.fragment.taskcontent;

import android.os.Bundle;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgTaskcontentBinding;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.v2.di.components.DaggerTaskcontentComponent;
import com.slash.youth.v2.di.components.TaskcontentComponent;
import com.slash.youth.v2.di.modules.TaskcontentModule;

@RootView(R.layout.frg_taskcontent)
public final class TaskcontentFragment extends BaseFragment<TaskcontentViewModel, FrgTaskcontentBinding> {

    TaskcontentComponent component;

    public static TaskcontentFragment instance() {
        return new TaskcontentFragment();
    }

    public static TaskcontentFragment instance(Bundle bundle) {
        TaskcontentFragment taskcontentFragment = new TaskcontentFragment();
        taskcontentFragment.setArguments(bundle);
        return taskcontentFragment;
    }


    @BeforeViews
    void beforViews() {
        component = DaggerTaskcontentComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .taskcontentModule(new TaskcontentModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        Bundle bundle = getArguments();
    }
}
