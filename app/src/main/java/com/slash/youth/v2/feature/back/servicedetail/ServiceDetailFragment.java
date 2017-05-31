package com.slash.youth.v2.feature.back.servicedetail;

import com.slash.youth.R;
import com.slash.youth.databinding.FrgServicedetailBinding;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.v2.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_servicedetail)
public final class ServiceDetailFragment extends BaseFragment<ServiceDetailViewModel, FrgServicedetailBinding> {

    public static ServiceDetailFragment instance() {
        return new ServiceDetailFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
