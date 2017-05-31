package com.core.op.bindingadapter.swipemenu;

import android.databinding.BindingAdapter;

import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.weight.SwipeMenuLayout;
import com.core.op.lib.weight.swipe.SwipeRefreshLayout;


/**
 * Created by kelin on 16-4-26.
 */
public class ViewBindingAdapter {
    @BindingAdapter({"enable"})
    public static void swipeMenuLayout(SwipeMenuLayout swipeMenuLayout, final Boolean swipeEnable) {
        swipeMenuLayout.setSwipeEnable(swipeEnable);
    }

}
