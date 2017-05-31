package com.slash.youth.v2.feature.dialog.common;


import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;

import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.DlgCopytextBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import static com.umeng.socialize.utils.DeviceConfig.context;

@PerActivity
public class CopyTextViewModel extends BDViewModel<DlgCopytextBinding> {
    private String strText;

    @Inject
    public CopyTextViewModel(RxAppCompatActivity activity) {
        super(activity);
    }


    public final ReplyCommand copy = new ReplyCommand(() -> {
        ClipboardManager cmb = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(strText);
        dialog.dismiss();
    });

    public final ReplyCommand cal = new ReplyCommand(() -> {
        dialog.dismiss();
    });

    public void setTextView(String textView) {
        this.strText = textView;
    }
}