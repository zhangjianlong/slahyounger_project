package com.slash.youth.v2.feature.message.list;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgMlistBinding;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.v2.di.components.MessageComponent;

@RootView(R.layout.frg_mlist)
public final class MListFragment extends BaseFragment<MListViewModel, FrgMlistBinding> {

    public static MListFragment instance() {
        return new MListFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MessageComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
