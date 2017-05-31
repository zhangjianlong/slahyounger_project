package com.slash.youth.v2.feature.back.service.slist;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.slash.youth.R;
import com.slash.youth.v2.base.list.more.BaseMoreFragment;
import com.slash.youth.v2.di.components.SimpleBackComponent;

public final class SListFragment extends BaseMoreFragment<SListViewModel> {

    public static SListFragment instance() {
        return new SListFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
