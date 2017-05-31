package com.slash.youth.v2.feature.local;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.ActLocalBinding;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.di.components.DaggerLocalComponent;
import com.slash.youth.v2.di.components.LocalComponent;
import com.slash.youth.v2.di.modules.LocalModule;

@RootView(R.layout.act_local)
public final class LocalActivity extends BackActivity<LocalViewModel, ActLocalBinding> {

    LocalComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, LocalActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerLocalComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .localModule(new LocalModule())
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
