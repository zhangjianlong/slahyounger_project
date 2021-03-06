package com.slash.youth.v2.feature.userinfo.tab.evaluate;


import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.domain.bean.UserEvaluateBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.interactor.main.UserEvaluateUseCase;
import com.slash.youth.v2.base.list.more.BaseMoreViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class EvaluateViewModel extends BaseMoreViewModel<UserEvaluateBean, EvaluateItemViewModel> {
    UserEvaluateUseCase userEvaluateUseCase;

    private long uid;

    @Inject
    public EvaluateViewModel(RxAppCompatActivity activity,
                             UserEvaluateUseCase userEvaluateUseCase) {
        super(activity);
        this.userEvaluateUseCase = userEvaluateUseCase;
        uid = activity.getIntent().getBundleExtra("data").getLong("Uid");
    }

    @Override
    public void afterViews() {
        binding.recyclerView.setBackgroundColor(activity.getResources().getColor(R.color.white));
        refresh();
    }

    @Override
    public Map<String, String> prams() {
        Map<String, String> map = super.prams();
        map.put("uid", uid + "");
        return map;
    }

    @Override
    public UseCase<BaseList<UserEvaluateBean>> useCase() {
        return userEvaluateUseCase;
    }

    @Override
    public void addData(UserEvaluateBean userEvaluateBean) {
        itemViewModels.add(new EvaluateItemViewModel(activity, userEvaluateBean));
    }

    @Override
    public void doComplate() {
        if (!isComplate) {
            itemViewModels.add(new EvaluateItemViewModel(activity, isComplate));
        }
    }

    @Override
    public int setItem(ItemView itemView, int position, EvaluateItemViewModel item) {
        itemView.set(BR.viewModel, R.layout.item_userinfo_evaluate);
        return 1;
    }
}