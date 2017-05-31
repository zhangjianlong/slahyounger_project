package com.slash.youth.v2.feature.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.slash.youth.R;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActPubdetailBinding;
import com.slash.youth.v2.di.components.DaggerPubDetailComponent;
import com.slash.youth.v2.di.components.PubDetailComponent;
import com.slash.youth.v2.di.modules.PubDetailModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import java.util.UUID;

import javax.inject.Inject;

@RootView(R.layout.act_pubdetail)
public final class PubDetailActivity extends BackActivity<PubDetailViewModel, ActPubdetailBinding> {

    PubDetailComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PubDetailActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerPubDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pubDetailModule(new PubDetailModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
//        if (LoginManager.currentLoginUserId == LoginManager.currentLoginUserId) {
//            binding.toolbar.toolbar.inflateMenu(R.menu.menu_modify);
//        } else {
        binding.toolbar.toolbar.inflateMenu(R.menu.menu_collect);
//        }
        binding.toolbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setIcon(CommonUtils.getContext().getResources().getDrawable(R.mipmap.collection_sel_icon));
                return false;
            }
        });
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar.toolbar;
    }

}
