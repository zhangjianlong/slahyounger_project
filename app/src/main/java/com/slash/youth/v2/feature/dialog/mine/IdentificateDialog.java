package com.slash.youth.v2.feature.dialog.mine;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgIdentificateBinding;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_identificate)
public final class IdentificateDialog extends BaseDialog<IdentificateViewModel, DlgIdentificateBinding> {

    @Inject
    public IdentificateDialog(RxAppCompatActivity activity, IdentificateViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.CENTER)
                .setMargin(100, 0, 100, 0), viewModel);
    }

    @AfterViews
    void afterViews() {
    }
}
