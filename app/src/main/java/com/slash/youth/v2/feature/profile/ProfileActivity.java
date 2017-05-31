package com.slash.youth.v2.feature.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.slash.youth.R;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActProfileBinding;
import com.slash.youth.v2.di.components.DaggerProfileComponent;
import com.slash.youth.v2.di.components.ProfileComponent;
import com.slash.youth.v2.di.modules.ProfileModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_profile)
public final class ProfileActivity extends BackActivity<ProfileViewModel, ActProfileBinding> {

    ProfileComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ProfileActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerProfileComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .profileModule(new ProfileModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        binding.toolbar.toolbar.inflateMenu(R.menu.menu_userinfo_save);
        binding.toolbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                viewModel.savaTemplate();
                return true;
            }
        });
    }


    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar.toolbar;
    }
}
