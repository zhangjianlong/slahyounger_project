package com.slash.youth.v2.base.tab;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.base.BViewModel;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgTabBinding;
import com.slash.youth.v2.feature.label.LabelFItemViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TabViewModel<E extends BViewModel> extends BFViewModel<FrgTabBinding> {

    public final ObservableField<Integer> selectPosition = new ObservableField<>(0);

    public final ObservableList<Fragment> fragments = new ObservableArrayList<>();

    public final ObservableList<String> titles = new ObservableArrayList<>();

    public final ObservableField<Integer> pageLimit = new ObservableField<>(0);

    public final ObservableField<Boolean> scrollEnable = new ObservableField<>(false);

    public final ItemView itemView = tabItemView();

    public final List<E> items = new ArrayList<>();

    @Inject
    public TabViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    protected ItemView tabItemView(){
        return null;
    }
}