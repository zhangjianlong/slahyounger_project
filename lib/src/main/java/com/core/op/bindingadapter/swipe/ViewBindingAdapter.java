package com.core.op.bindingadapter.swipe;

import android.databinding.BindingAdapter;

import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.weight.swipe.SwipeRefreshLayout;


/**
 * Created by kelin on 16-4-26.
 */
public class ViewBindingAdapter {
    @BindingAdapter({"onSwipeRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ReplyCommand onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
    }

    @BindingAdapter(value = {"swipeRefresh"}, requireAll = false)
    public static void setRefresh(final SwipeRefreshLayout swipeRefreshLayout, final Boolean refresh) {
        swipeRefreshLayout.setRefreshing(refresh);
    }

}
