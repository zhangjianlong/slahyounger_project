package com.core.op.bindingadapter.flowlayout;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.core.op.bindingadapter.common.BindingCollectionAdapter;
import com.core.op.bindingadapter.common.BindingRecyclerViewAdapter;
import com.core.op.bindingadapter.common.BindingUtils;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewArg;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.bindingadapter.common.factories.BindingRecyclerViewAdapterFactory;
import com.core.op.lib.weight.FlowLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import static org.greenrobot.eventbus.util.ErrorDialogManager.factory;


public class ViewBindingAdapter {
    // FlowLayout
    @BindingAdapter(value = {"itemView", "items"}, requireAll = false)
    public static <T> void setAdapter(FlowLayout flowLayout, ItemViewArg<T> arg, List<T> items) {
        if (arg == null) {
            throw new IllegalArgumentException("itemView must not be null");
        }
        FlowLayout.FlowAdapter<T> adapter = (FlowLayout.FlowAdapter) flowLayout.getAdapter();
        if (adapter == null) {
            adapter = new FlowLayout.FlowAdapter<T>(arg);
            adapter.setItems(items);
            flowLayout.setAdapter(adapter);
            flowLayout.notifyChange();
        } else {
            adapter.setItems(items);
            flowLayout.notifyChange();
        }
    }
}