package com.slash.youth.v2.feature.main.order.indent;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.databinding.FrgIndentBinding;
import com.slash.youth.v2.base.list.BaseListFragment;
import com.slash.youth.v2.base.list.BaseListViewModel;
import com.slash.youth.v2.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_indent)
public final class IndentFragment extends BaseListFragment<IndentViewModel> {

    public static IndentFragment instance() {
        return new IndentFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
