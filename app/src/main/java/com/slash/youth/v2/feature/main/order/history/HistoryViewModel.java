package com.slash.youth.v2.feature.main.order.history;


import android.support.v4.app.FragmentManager;

import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.FrgHistoryBinding;
import com.slash.youth.domain.bean.HistoryBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.v2.base.list.BaseListViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

@PerActivity
public class HistoryViewModel extends BaseListViewModel<HistoryBean,HistoryItemViewModel> {

    @Inject
    public HistoryViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }

    @Override
    public UseCase<BaseList<HistoryBean>> useCase() {
        return null;
    }

    @Override
    public void addData(HistoryBean historyBean) {

    }

    @Override
    public void doComplate() {

    }

    @Override
    public int setItem(ItemView itemView, int position, HistoryItemViewModel item) {
        return 0;
    }
}