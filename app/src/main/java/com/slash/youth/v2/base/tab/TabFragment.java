package com.slash.youth.v2.base.tab;

import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgTabBinding;
import com.slash.youth.v2.base.BaseFragment;

@RootView(R.layout.frg_tab)
public abstract class TabFragment<V extends BFViewModel> extends BaseFragment<V, FrgTabBinding> {

}
