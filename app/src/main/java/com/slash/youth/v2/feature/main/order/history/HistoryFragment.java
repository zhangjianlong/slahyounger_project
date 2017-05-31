package com.slash.youth.v2.feature.main.order.history;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.databinding.FrgHistoryBinding;
import com.slash.youth.v2.base.list.BaseListFragment;
import com.slash.youth.v2.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_history)
public final class HistoryFragment extends BaseListFragment<HistoryViewModel> {

    public static HistoryFragment instance() {
        return new HistoryFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
