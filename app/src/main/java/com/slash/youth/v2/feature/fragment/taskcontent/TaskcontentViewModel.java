package com.slash.youth.v2.feature.fragment.taskcontent;


import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.FrgTaskcontentBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class TaskcontentViewModel extends BFViewModel<FrgTaskcontentBinding> {

    @Inject
    public TaskcontentViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }
}