package com.slash.youth.v2.feature.pub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.core.op.lib.weight.imgselector.MultiImageSelector;
import com.slash.youth.R;
import com.slash.youth.databinding.ActPubBinding;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.v2.di.components.DaggerPubComponent;
import com.slash.youth.v2.di.components.PubComponent;
import com.slash.youth.v2.di.modules.PubModule;

import java.util.concurrent.TimeUnit;

import rx.Observable;

@RootView(R.layout.act_pub)
public final class PubActivity extends BackActivity<PubViewModel, ActPubBinding> {

    PubComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PubActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerPubComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pubModule(new PubModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        binding.toolbar.toolbar.inflateMenu(R.menu.menu_pub_commit);
        binding.toolbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.commit:
                        viewModel.commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar.toolbar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MultiImageSelector.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                viewModel.uploadImage(data);
            }
        }
    }
}
