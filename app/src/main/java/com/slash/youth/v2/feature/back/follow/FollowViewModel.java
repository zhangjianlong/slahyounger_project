package com.slash.youth.v2.feature.back.follow;


import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgFollowBinding;
import com.slash.youth.v2.feature.pub.PubItemViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class FollowViewModel extends BFViewModel<FrgFollowBinding> {


    public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.item_follow);
    public final List<FollowItemViewModel> itemViewModels = new ArrayList<>();

    @Inject
    public FollowViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {

    }
}