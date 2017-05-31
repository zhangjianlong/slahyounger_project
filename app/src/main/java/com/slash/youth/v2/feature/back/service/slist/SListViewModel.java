package com.slash.youth.v2.feature.back.service.slist;


import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.domain.bean.ServiceBean;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.v2.base.list.more.BaseMoreViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class SListViewModel extends BaseMoreViewModel<ServiceBean, SListItemViewModel> {

    @Inject
    public SListViewModel(RxAppCompatActivity activity) {
        super(activity);
    }


    @Override
    public void afterViews() {

    }

    @Override
    public UseCase<BaseList<ServiceBean>> useCase() {
        return null;
    }

    @Override
    public void addData(ServiceBean serviceBean) {

    }

    @Override
    public void doComplate() {

    }

    @Override
    public int setItem(ItemView itemView, int position, SListItemViewModel item) {
        itemView.set(BR.viewModel,R.layout.item_service);
        return 1;
    }
}