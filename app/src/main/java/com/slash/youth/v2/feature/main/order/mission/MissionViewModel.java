package com.slash.youth.v2.feature.main.order.mission;


import android.support.v4.app.FragmentManager;

import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgMissionBinding;
import com.slash.youth.domain.bean.MissionBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.v2.base.list.BaseListViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

@PerActivity
public class MissionViewModel extends BaseListViewModel<MissionBean,MissionItemViewModel> {

    @Inject
    public MissionViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }

    @Override
    public UseCase<BaseList<MissionBean>> useCase() {
        return null;
    }

    @Override
    public void addData(MissionBean missionBean) {

    }

    @Override
    public void doComplate() {

    }

    @Override
    public int setItem(ItemView itemView, int position, MissionItemViewModel item) {
        itemView.set(BR.viewModel, R.layout.item_order_mission);
        return 1;
    }
}