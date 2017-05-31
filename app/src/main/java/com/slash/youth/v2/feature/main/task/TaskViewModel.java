package com.slash.youth.v2.feature.main.task;


import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.jakewharton.rxbinding.view.RxView;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgTaskBinding;
import com.slash.youth.v2.feature.dialog.task.pub.PubTaskDialog;
import com.slash.youth.v2.feature.dialog.task.select.SelectTaskDialog;
import com.slash.youth.v2.feature.main.task.list.TaskListFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observer;

import static android.R.attr.button;
import static com.slash.youth.v2.feature.main.MainViewModel.CHANG_POSITION;
import static com.slash.youth.v2.feature.main.task.list.TaskListViewModel.TASK_ONWAY;
import static com.slash.youth.v2.feature.main.task.list.TaskListViewModel.TASK_STUTUS;
import static com.slash.youth.v2.util.MessageKey.TASK_CHANGE;

@PerActivity
public class TaskViewModel extends BFViewModel<FrgTaskBinding> {

    public final static String SHOW_NODATA = "SHOW_NODATA";

    private SelectTaskDialog selectTaskDialog;

    public ObservableField<String> taskStatus = new ObservableField<>("进行中");

    private PubTaskDialog pubTaskDialog;

    public final ObservableField<Integer> noDataVisible = new ObservableField<>(View.GONE);
    public final ObservableField<Integer> taskMsgVisible = new ObservableField<>(View.INVISIBLE);

    public final ReplyCommand taskClick = new ReplyCommand(() -> {

    });

    public final ReplyCommand pubClick = new ReplyCommand(() -> {
        if (pubTaskDialog != null && !pubTaskDialog.isShowing()) {
            pubTaskDialog.show();
        }
    });

    public final ReplyCommand lookClick = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(CHANG_POSITION);
    });

    @Inject
    public TaskViewModel(RxAppCompatActivity activity,
                         PubTaskDialog pubTaskDialog,
                         SelectTaskDialog selectTaskDialog) {
        super(activity);
        this.pubTaskDialog = pubTaskDialog;
        this.selectTaskDialog = selectTaskDialog;
    }

    @Override
    public void afterViews() {
        addFragment(R.id.fl_container, TaskListFragment.instance());
        Messenger.getDefault().register(activity, SHOW_NODATA, Integer.class, data -> {
            if (data == 0)
                noDataVisible.set(View.VISIBLE);
            else
                noDataVisible.set(View.GONE);
        });

        Messenger.getDefault().register(this, TASK_STUTUS, String.class, status -> {
            if (status.equals(TASK_ONWAY)) {
                taskStatus.set("进行中");
            } else {
                taskStatus.set("历史");
            }
        });

        Messenger.getDefault().register(this, TASK_CHANGE, Integer.class, d -> {
            if (d > 0) {
                taskMsgVisible.set(View.VISIBLE);
            } else {
                taskMsgVisible.set(View.INVISIBLE);
            }
        });

        RxView.clicks(binding.llChange)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Object o) {
                        if (selectTaskDialog != null && !selectTaskDialog.isShowing()) {
                            selectTaskDialog.show();
                        }
                    }
                });
    }
}