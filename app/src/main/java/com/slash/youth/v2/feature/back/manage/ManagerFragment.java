package com.slash.youth.v2.feature.back.manage;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.slash.youth.v2.base.list.BaseListFragment;
import com.slash.youth.v2.di.components.SimpleBackComponent;

public final class ManagerFragment extends BaseListFragment<ManagerViewModel> {

    public static ManagerFragment instance() {
        return new ManagerFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(SimpleBackComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
