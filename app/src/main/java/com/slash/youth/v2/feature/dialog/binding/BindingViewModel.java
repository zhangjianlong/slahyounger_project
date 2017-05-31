package com.slash.youth.v2.feature.dialog.binding;

import android.view.View;

import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.DialogBindingBinding;
import com.slash.youth.ui.dialog.base.BDialogViewModel;
import com.slash.youth.ui.dialog.base.OnDialogLisetener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * Created by acer on 2017/3/1.
 */

@PerActivity
public class BindingViewModel extends BDViewModel<DialogBindingBinding> {
    @Inject
    public BindingViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    public void cancel(View view) {
        dialog.dismiss();
        if (onDialogLisetener != null) {
            onDialogLisetener.onCancel();
        }
    }

    public void consult(View view) {
        dialog.dismiss();
        if (onDialogLisetener != null) {
            onDialogLisetener.onConfirm();
        }
    }
}
