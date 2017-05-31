package com.slash.youth.v2.base.list;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.weight.EmptyLayout;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgBaselistBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class BaseListItemViewModel<T> extends BViewModel<T> {

    public final ObservableField<Boolean> isLoadComplete = new ObservableField<>();

    public BaseListItemViewModel(RxAppCompatActivity activity, boolean isLoadComplete) {
        super(activity);
        this.isLoadComplete.set(isLoadComplete);
    }

    public BaseListItemViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    public BaseListItemViewModel() {
    }
}