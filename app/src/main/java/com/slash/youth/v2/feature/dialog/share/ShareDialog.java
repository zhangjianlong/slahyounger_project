package com.slash.youth.v2.feature.dialog.share;

import android.content.Context;
import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgShareBinding;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_share)
public final class ShareDialog extends BaseDialog<ShareViewModel, DlgShareBinding> {

    @Inject
    public ShareDialog(RxAppCompatActivity activity, ShareViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.BOTTOM), viewModel);
    }

    @AfterViews
    void afterViews() {
    }
}
