package com.slash.youth.v2.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.core.op.lib.base.BAViewModel;

/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2017/1/5
 */
public abstract class BackActivity<V extends BAViewModel, T extends ViewDataBinding> extends BaseActivity<V, T> {

    @Override
    protected void initAfterView() {

        setToolBar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        super.initAfterView();
    }

    protected abstract Toolbar setToolBar();
}
