package com.slash.youth.v2.feature.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.core.op.lib.di.HasComponent;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.ActMainBinding;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.ui.activity.ChatActivity;
import com.slash.youth.ui.activity.ChooseSkillActivity;
import com.slash.youth.ui.activity.GuidActivity;
import com.slash.youth.ui.activity.LoginActivity;
import com.slash.youth.ui.activity.MessageActivity;
import com.slash.youth.ui.activity.PerfectInfoActivity;
import com.slash.youth.ui.activity.SplashActivity;
import com.slash.youth.ui.dialog.offline.OfflineDialog;
import com.slash.youth.ui.dialog.offline.OfflineViewModel;
import com.slash.youth.ui.receiver.FloatWinService;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.LogKit;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.v2.di.components.DaggerMainComponent;
import com.slash.youth.v2.di.components.MainComponent;
import com.slash.youth.v2.di.modules.MainModule;
import com.slash.youth.v2.util.MessageKey;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static com.slash.youth.engine.MsgManager.OFF_LINE;

@RootView(R.layout.act_main)
public final class MainActivity extends BaseActivity<MainViewModel, ActMainBinding> implements HasComponent<MainComponent> {

    MainComponent component;

    //记录第一次点击的时间
    private long clickTime = 0;
    private OfflineDialog offlineDialog;
    View msgIconLayer;
    ImageView ivMsgIcon;
    boolean isAddMsgIconLayer = false;
    private Intent intent;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerMainComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .mainModule(new MainModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        intent = new Intent(this, FloatWinService.class);
        startService(intent);
        Messenger.getDefault().register(this, OFF_LINE, () -> {
            offline();
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        Messenger.getDefault().sendNoMsg(MessageKey.HIDE_FLOAT_WINDOW);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Messenger.getDefault().sendNoMsg(MessageKey.SHOW_FLOAT_WINDOW);

    }

    @Override
    public MainComponent getComponent() {
        return component;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public void offline() {
        if (offlineDialog == null) {
            offlineDialog = new OfflineDialog(this, new OfflineViewModel(this));
        }
        if (!offlineDialog.isShowing()) {
            offlineDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
        MsgManager.exit();
    }

    @Override
    public void onBackPressed() {
        boolean isPub = viewModel.onBackPressed();
        if (isPub && (System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else if (isPub) {
            super.onBackPressed();
        }
    }
}
