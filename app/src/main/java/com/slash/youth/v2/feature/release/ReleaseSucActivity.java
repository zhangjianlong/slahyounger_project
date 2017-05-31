package com.slash.youth.v2.feature.release;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.slash.youth.R;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActReleasesucBinding;
import com.slash.youth.v2.di.components.DaggerReleaseSucComponent;
import com.slash.youth.v2.di.components.ReleaseSucComponent;
import com.slash.youth.v2.di.modules.ReleaseSucModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_releasesuc)
public final class ReleaseSucActivity extends BackActivity<ReleaseSucViewModel, ActReleasesucBinding> {

    ReleaseSucComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ReleaseSucActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerReleaseSucComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .releaseSucModule(new ReleaseSucModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        binding.toolbar.toolbar.inflateMenu(R.menu.menu_finsh);
        binding.toolbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                viewModel.finsh();
                return true;
            }
        });
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar.toolbar;
    }
}
