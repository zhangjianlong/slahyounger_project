package com.slash.youth.v2.feature.userinfo.tab.task;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.slash.youth.v2.base.list.more.BaseMoreFragment;
import com.slash.youth.v2.di.components.UserInfoComponent;

public final class UserTaskFragment extends BaseMoreFragment<UserTaskViewModel> {

    public static UserTaskFragment instance() {
        return new UserTaskFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(UserInfoComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
