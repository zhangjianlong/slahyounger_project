package com.slash.youth.v2.feature.userinfo.tab.task;


import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.domain.bean.UserTaskBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.interactor.main.UserTaskUseCase;
import com.slash.youth.v2.base.list.more.BaseMoreViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class UserTaskViewModel extends BaseMoreViewModel<UserTaskBean, UserTaskItemViewModel> {

    UserTaskUseCase userTaskUseCase;

    private long uid;

    @Inject
    public UserTaskViewModel(RxAppCompatActivity activity,
                             UserTaskUseCase userTaskUseCase) {
        super(activity);
        this.userTaskUseCase = userTaskUseCase;
        uid = activity.getIntent().getBundleExtra("data").getLong("Uid");
    }

    @Override
    public void afterViews() {
        refresh();
    }

    @Override
    public Map<String, String> prams() {
        Map<String, String> map = super.prams();
        map.put("uid", uid + "");
        map.put("anonymity", "1");
        return map;
    }

    @Override
    public UseCase<BaseList<UserTaskBean>> useCase() {
        return userTaskUseCase;
    }

    @Override
    public void addData(UserTaskBean userTaskBean) {
        itemViewModels.add(new UserTaskItemViewModel(activity, userTaskBean));
    }

    @Override
    public void doComplate() {
        if (!isComplate)
            itemViewModels.add(new UserTaskItemViewModel(activity, isComplate));
    }

    @Override
    public int setItem(ItemView itemView, int position, UserTaskItemViewModel item) {
        itemView.set(BR.viewModel, R.layout.item_userinfo_task);
        return 1;
    }
}