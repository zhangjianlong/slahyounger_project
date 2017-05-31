package com.slash.youth.v2.feature.dialog.splash;

import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgSplashBinding;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.ui.view.WarpLinearLayout;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_splash)
public final class SplashDialog extends BaseDialog<SplashViewModel, DlgSplashBinding> {

    @Inject
    public SplashDialog(RxAppCompatActivity activity, SplashViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.CENTER)
                .setMargin(100, 0, 100, 0)
                .setCancelable(false), viewModel);
    }

    @AfterViews
    void afterViews() {
    }


}
