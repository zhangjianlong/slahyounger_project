package com.slash.youth.v2.feature.dialog.common;

import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgCommonBinding;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_common)
public final class CommonDialog extends BaseDialog<CommonViewModel, DlgCommonBinding> {

    @Inject
    public CommonDialog(RxAppCompatActivity activity, CommonViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.CENTER)
                .setMargin(100, 0, 100, 0)
                .setCancelable(false), viewModel);
    }

    @AfterViews
    void afterViews() {
    }

    public void initValue(String title, String content) {
        viewModel.initValue(title, content);
    }

    public void initValue(String content) {
        viewModel.initValue(content);
    }

    public void hintCalBtn() {
        viewModel.hintCalBtn();
    }
}
