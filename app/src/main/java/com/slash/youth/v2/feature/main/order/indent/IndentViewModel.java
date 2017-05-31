package com.slash.youth.v2.feature.main.order.indent;


import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.domain.bean.HistoryBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.v2.base.list.BaseListViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class IndentViewModel extends BaseListViewModel<HistoryBean,IndentItemViewModel> {

    @Inject
    public IndentViewModel(RxAppCompatActivity activity) {
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
    public int setItem(ItemView itemView, int position, IndentItemViewModel item) {
        itemView.set(BR.viewModel,R.layout.item_order_history);
        return 1;
    }
}