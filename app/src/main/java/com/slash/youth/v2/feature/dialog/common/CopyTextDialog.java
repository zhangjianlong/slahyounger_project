package com.slash.youth.v2.feature.dialog.common;

import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgCopytextBinding;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_copytext)
public final class CopyTextDialog extends BaseDialog<CopyTextViewModel, DlgCopytextBinding> {

    @Inject
    public CopyTextDialog(RxAppCompatActivity activity, CopyTextViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.BOTTOM), viewModel);
    }

    @AfterViews
    void afterViews() {
    }


    public void setCopyText(String str) {
        viewModel.setTextView(str);
    }
}
