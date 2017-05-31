package com.slash.youth.v2.feature.dialog.report;

import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.core.op.lib.utils.DeviceUtil;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgReportBinding;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_report)
public final class ReportDialog extends BaseDialog<ReportViewModel, DlgReportBinding> {

    @Inject
    public ReportDialog(RxAppCompatActivity activity, ReportViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.TOP | Gravity.RIGHT)
                .setMargin(0, DeviceUtil.dip2px(activity, 60f), DeviceUtil.px2dip(activity, 30f), 0)
                .setBackgroud(false)
                .setInAnimation(R.anim.anim_scan_in)
                .setOutAnimation(R.anim.anim_scan_out), viewModel);
    }

    @AfterViews
    void afterViews() {
    }
}
