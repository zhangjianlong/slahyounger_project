package com.slash.youth.v2.feature.dialog.find;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgPubBinding;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@RootView(R.layout.dlg_pub)
public final class PubDialog extends BaseDialog<PubViewModel, DlgPubBinding> {

    @Inject
    public PubDialog(RxAppCompatActivity activity, PubViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.TOP)
                .setBackgroud(false)
                .setInAnimation(R.anim.anim_trans_in)
                .setOutAnimation(R.anim.anim_trans_out), viewModel);
    }

    @AfterViews
    void afterViews() {
    }
}
