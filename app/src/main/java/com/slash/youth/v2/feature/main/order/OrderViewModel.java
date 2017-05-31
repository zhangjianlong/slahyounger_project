package com.slash.youth.v2.feature.main.order;


import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.viewpager.ViewPagerFragmentAdatper;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.rxjava.Transformers;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgOrderBinding;
import com.slash.youth.v2.feature.main.order.history.HistoryFragment;
import com.slash.youth.v2.feature.main.order.indent.IndentFragment;
import com.slash.youth.v2.feature.main.order.mission.MissionFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

@PerActivity
public class OrderViewModel extends BFViewModel<FrgOrderBinding> {
    public final ObservableField<Integer> selectPosition = new ObservableField<>(0);

    public final ObservableList<Fragment> fragments = new ObservableArrayList<>();

    public final ObservableList<String> titles = new ObservableArrayList<>();

    public final ObservableField<Integer> pageLimit = new ObservableField<>(0);

    public final ObservableField<Boolean> scrollEnable = new ObservableField<>(false);

    public final ItemView itemView = tabItemView();

    public final List<OrderTabViewModel> items = new ArrayList<>();
    @Inject
    public OrderViewModel(RxAppCompatActivity activity) {
        super(activity);
        pageLimit.set(3);
        titles.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.order_tabs)));
        fragments.add(MissionFragment.instance());
        fragments.add(IndentFragment.instance());
        fragments.add(HistoryFragment.instance());

        items.add(new OrderTabViewModel(activity,"任务"));
        items.add(new OrderTabViewModel(activity,"订单"));
        items.add(new OrderTabViewModel(activity,"历史"));
    }

    @Override
    public void afterViews() {
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        scrollEnable.set(true);

        ViewPagerFragmentAdatper adatper;
        adatper = new ViewPagerFragmentAdatper(fragmentManager, fragments, titles);
        binding.viewPager.setAdapter(adatper);
        Observable.from(items)
                .compose(Transformers.mapWithIndex())
                .subscribe(data->{
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(activity),itemView.layoutRes(),binding.tabLayout,false);
                    viewDataBinding.setVariable(itemView.bindingVariable(),data.value());
                    binding.tabLayout.getTabAt((int)data.index()).setCustomView(viewDataBinding.getRoot());
                });
    }

    protected ItemView tabItemView() {
        return ItemView.of(BR.viewModel, R.layout.item_order_tab);
    }
}