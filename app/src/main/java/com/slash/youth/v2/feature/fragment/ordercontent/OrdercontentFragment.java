package com.slash.youth.v2.feature.fragment.ordercontent;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgOrdercontentBinding;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.v2.di.components.DaggerOrdercontentComponent;
import com.slash.youth.v2.di.components.OrdercontentComponent;

@RootView(R.layout.frg_ordercontent)
public final class OrdercontentFragment extends BaseFragment<OrdercontentViewModel, FrgOrdercontentBinding> {

    public static OrdercontentFragment instance() {
        return new OrdercontentFragment();
    }

    OrdercontentComponent component;

    @BeforeViews
    void beforViews() {
        component = DaggerOrdercontentComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
