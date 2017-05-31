package com.slash.youth.v2.feature.dialog.binding;

import android.app.Activity;
import android.view.Gravity;

import com.core.op.lib.base.BDialog;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.databinding.DialogBindingBinding;
import com.slash.youth.v2.base.BaseDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;


/**
 * Created by acer on 2017/3/2.
 */
@RootView(R.layout.dialog_binding)
public class BindingDialog extends BaseDialog<BindingViewModel, DialogBindingBinding> {

    @Inject
    public BindingDialog(RxAppCompatActivity activity, BindingViewModel viewModel) {
        super(BDialog.newDialog(activity)
                .setGravity(Gravity.CENTER)
                .setMargin(100, 0, 100, 0), viewModel);
    }
}
