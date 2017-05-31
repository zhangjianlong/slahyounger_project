package com.slash.youth.v2.feature.userinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.core.op.lib.di.HasComponent;
import com.slash.youth.R;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActUserinfoBinding;
import com.slash.youth.v2.di.components.DaggerUserInfoComponent;
import com.slash.youth.v2.di.components.UserInfoComponent;
import com.slash.youth.v2.di.modules.UserInfoModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

@RootView(R.layout.act_userinfo)
public final class UserInfoActivity extends BackActivity<UserInfoViewModel, ActUserinfoBinding> implements HasComponent<UserInfoComponent> {

    UserInfoComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }


    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerUserInfoComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userInfoModule(new UserInfoModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        long uid = getIntent().getBundleExtra("data").getLong("Uid");
        if (LoginManager.currentLoginUserId == uid) {
            binding.toolbar.inflateMenu(R.menu.menu_userinfo_edit);
        } else {
            binding.toolbar.inflateMenu(R.menu.menu_userinfo_more);
        }

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        //业务逻辑
                        viewModel.gotoEidt();
                        break;
                    case R.id.more:
                        //业务逻辑
                        viewModel.doMore();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public UserInfoComponent getComponent() {
        return component;
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar;
    }
}
