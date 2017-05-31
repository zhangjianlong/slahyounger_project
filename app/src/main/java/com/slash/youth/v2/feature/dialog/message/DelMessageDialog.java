package com.slash.youth.v2.feature.dialog.message;

import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgDelmessageBinding;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_delmessage)
public final class DelMessageDialog extends BaseDialog<DelMessageViewModel, DlgDelmessageBinding> {

    @Inject
    public DelMessageDialog(RxAppCompatActivity activity, DelMessageViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.CENTER)
                .setMargin(100, 0, 100, 0), viewModel);
    }

    @AfterViews
    void afterViews() {
    }
}
