package com.slash.youth.v2.feature.back.service;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.databinding.FrgServiceBinding;
import com.slash.youth.v2.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_service)
public final class ServiceFragment extends BaseFragment<ServiceViewModel, FrgServiceBinding> {

    public static ServiceFragment instance() {
        return new ServiceFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
