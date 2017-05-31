package com.slash.youth.v2.feature.protocol;


import android.databinding.ObservableField;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.R;
import com.slash.youth.databinding.ActProtocolBinding;
import com.slash.youth.utils.CommonUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import static cn.finalteam.galleryfinal.utils.ILogger.getSettings;

@PerActivity
public class ProtocolViewModel extends BAViewModel<ActProtocolBinding> {
    public final ObservableField<String> title = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_procol_title));

    @Inject
    public ProtocolViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        binding.webviewSlashProtocol.getSettings().setJavaScriptEnabled(true);
        binding.webviewSlashProtocol.loadUrl("http://web.slashyounger.com/#!/agreement?nav=1");
    }
}