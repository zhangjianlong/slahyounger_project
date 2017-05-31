package com.slash.youth.v2.feature.protocol;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.slash.youth.R;
import com.slash.youth.databinding.ActBindingBinding;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActProtocolBinding;
import com.slash.youth.v2.di.components.DaggerProtocolComponent;
import com.slash.youth.v2.di.components.ProtocolComponent;
import com.slash.youth.v2.di.modules.ProtocolModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.v2.feature.bindingaccount.BindingViewModel;

import javax.inject.Inject;

@RootView(R.layout.act_protocol)
public final class ProtocolActivity extends BackActivity<ProtocolViewModel, ActProtocolBinding> {

    ProtocolComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, ProtocolActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerProtocolComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .protocolModule(new ProtocolModule())
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
