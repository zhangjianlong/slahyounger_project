package com.slash.youth.v2.feature.main.order;

import android.databinding.ObservableField;
import android.view.View;

import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.base.BViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by op on 2017/4/13.
 */

public class OrderTabViewModel extends BViewModel {

    public ObservableField<Integer> dotVisible = new ObservableField<>(View.VISIBLE);
    public ObservableField<String> title = new ObservableField<>();

    public OrderTabViewModel(RxAppCompatActivity activity,String title) {
        super(activity);
        this.title.set(title);
    }
}
