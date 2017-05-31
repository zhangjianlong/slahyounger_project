package com.slash.youth.v2.feature.release;


import android.databinding.ObservableField;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.R;
import com.slash.youth.databinding.ActReleasesucBinding;
import com.slash.youth.utils.CommonUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class ReleaseSucViewModel extends BAViewModel<ActReleasesucBinding> {
    public final ObservableField<String> title = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_release_title));

    @Inject
    public ReleaseSucViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }

    public ReplyCommand complete = new ReplyCommand(() -> {

    });

    public ReplyCommand review = new ReplyCommand(() -> {

    });

    public void finsh() {
        if (!activity.isFinishing()) {
            activity.finish();
        }
    }
}