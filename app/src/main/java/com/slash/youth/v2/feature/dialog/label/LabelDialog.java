package com.slash.youth.v2.feature.dialog.label;

import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgLabelBinding;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_label)
public final class LabelDialog extends BaseDialog<LabelViewModel, DlgLabelBinding> {

    @Inject
    public LabelDialog(RxAppCompatActivity activity, LabelViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.CENTER)
                .setMargin(100, 0, 100, 0), viewModel);
    }

    @AfterViews
    void afterViews() {
    }
}
