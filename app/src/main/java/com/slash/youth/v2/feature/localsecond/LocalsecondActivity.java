package com.slash.youth.v2.feature.localsecond;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.slash.youth.R;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActLocalsecondBinding;
import com.slash.youth.v2.di.components.DaggerLocalsecondComponent;
import com.slash.youth.v2.di.components.LocalsecondComponent;
import com.slash.youth.v2.di.modules.LocalsecondModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_localsecond)
public final class LocalsecondActivity extends BackActivity<LocalsecondViewModel, ActLocalsecondBinding> {

    LocalsecondComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, LocalsecondActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerLocalsecondComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .localsecondModule(new LocalsecondModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toobar.toolbar;
    }
}
