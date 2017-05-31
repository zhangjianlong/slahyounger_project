package com.slash.youth.v2.feature.dialog.manage;

import android.content.Context;
import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.slash.youth.R;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.databinding.DlgDelmanagerBinding;
import com.slash.youth.v2.base.BaseDialog;

import javax.inject.Inject;

@RootView(R.layout.dlg_delmanager)
public final class DelManagerDialog extends BaseDialog<DelManagerViewModel, DlgDelmanagerBinding> {

    @Inject
    public DelManagerDialog(Context context, DelManagerViewModel viewModel) {
        super(BDialog.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setMargin(100, 0, 100, 0), viewModel);
    }

    @AfterViews
    void afterViews() {
    }
}
