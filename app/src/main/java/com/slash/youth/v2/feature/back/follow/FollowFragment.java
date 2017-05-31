package com.slash.youth.v2.feature.back.follow;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.databinding.FrgFollowBinding;
import com.slash.youth.v2.di.components.SimpleBackComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_follow)
public final class FollowFragment extends BaseFragment<FollowViewModel, FrgFollowBinding> {

    public static FollowFragment instance() {
        return new FollowFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
