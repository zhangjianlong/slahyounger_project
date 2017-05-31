package com.slash.youth.v2.feature.label;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.slash.youth.R;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActLabelBinding;
import com.slash.youth.v2.di.components.DaggerLabelComponent;
import com.slash.youth.v2.di.components.LabelComponent;
import com.slash.youth.v2.di.modules.LabelModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.act_label)
public final class LabelActivity extends BackActivity<LabelViewModel, ActLabelBinding> {

    LabelComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, LabelActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerLabelComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .labelModule(new LabelModule())
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
