package com.slash.youth.v2.feature.message;

import android.content.Context;
import android.content.Intent;

import com.core.op.lib.di.HasComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.ActMessageBinding;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.v2.di.components.DaggerMessageComponent;
import com.slash.youth.v2.di.components.MessageComponent;
import com.slash.youth.v2.di.modules.MessageModule;

@RootView(R.layout.act_message)
public final class MessageActivity extends BaseActivity<MessageViewModel, ActMessageBinding> implements HasComponent<MessageComponent> {

    MessageComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, MessageActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerMessageComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .messageModule(new MessageModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
    }

    @Override
    public MessageComponent getComponent() {
        return component;
    }
}
