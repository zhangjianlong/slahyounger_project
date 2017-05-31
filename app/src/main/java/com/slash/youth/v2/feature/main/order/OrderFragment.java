package com.slash.youth.v2.feature.main.order;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.databinding.FrgOrderBinding;
import com.slash.youth.v2.base.tab.TabFragment;
import com.slash.youth.v2.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;
@RootView(R.layout.frg_order)
public final class OrderFragment extends BaseFragment<OrderViewModel,FrgOrderBinding> {

    public static OrderFragment instance() {
        return new OrderFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
