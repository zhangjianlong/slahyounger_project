package com.slash.youth.v2.feature.dialog.common;


import android.databinding.ObservableField;
import android.view.View;

import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.R;
import com.slash.youth.databinding.DlgCommonBinding;
import com.slash.youth.utils.CommonUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class CommonViewModel extends BDViewModel<DlgCommonBinding> {
    public final ObservableField<String> title = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_common_dialog_title));
    public final ObservableField<String> hint = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_common_dialog_hint));
    public final ObservableField<Integer> cancelHint = new ObservableField<>(View.VISIBLE);

    public final ReplyCommand confim = new ReplyCommand(() -> {
        dialog.dismiss();
        onDialogLisetener.onConfirm();
    });


    public final ReplyCommand cancel = new ReplyCommand(() -> {
        dialog.dismiss();
        onDialogLisetener.onCancel();
    });


    @Inject
    public CommonViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    public void initValue(String title, String content) {
        this.title.set(title);
        this.hint.set(content);
    }


    public void initValue(String content) {
        this.hint.set(content);
    }

    public void hintCalBtn() {
        cancelHint.set(View.GONE);
    }
}