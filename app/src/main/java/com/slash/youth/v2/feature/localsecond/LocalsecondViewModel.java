package com.slash.youth.v2.feature.localsecond;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.ActLocalsecondBinding;
import com.slash.youth.domain.bean.ProvinceBean;
import com.slash.youth.v2.feature.local.LocalItemViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

@PerActivity
public class LocalsecondViewModel extends BAViewModel<ActLocalsecondBinding> {

    ProvinceBean provinceBean;

    public String title;

    public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.item_localsecond);
    public final List<LocalsecondItemViewModel> itemViewModels = new ArrayList<>();


    @Inject
    public LocalsecondViewModel(RxAppCompatActivity activity) {
        super(activity);
        this.provinceBean = (ProvinceBean) activity.getIntent().getBundleExtra("data").getSerializable("province");
        this.title = provinceBean.getName();
    }

    @Override
    public void afterViews() {
        loadData();
    }

    private void loadData() {
        Observable.from(provinceBean.getCity())
                .subscribe(data -> {
                    itemViewModels.add(new LocalsecondItemViewModel(activity, data.getName(), provinceBean.getName()));
                },error->{

                },()->{
//                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });
    }
}