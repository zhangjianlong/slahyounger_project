package com.slash.youth.v2.feature.bindingaccount;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.slash.youth.R;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActBindingBinding;
import com.slash.youth.v2.di.components.BindingComponent;
import com.slash.youth.v2.di.components.DaggerBindingComponent;
import com.slash.youth.v2.di.modules.BindingModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_binding)
public final class BindingActivity extends BackActivity<BindingViewModel, ActBindingBinding> {

    BindingComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, BindingActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerBindingComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .bindingModule(new BindingModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar.toolbar;
    }
}
