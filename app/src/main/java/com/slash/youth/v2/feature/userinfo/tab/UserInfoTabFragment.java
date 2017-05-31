package com.slash.youth.v2.feature.userinfo.tab;

import android.os.Bundle;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.v2.base.tab.TabFragment;
import com.slash.youth.v2.di.components.UserInfoComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

public final class UserInfoTabFragment extends TabFragment<UserInfoTabViewModel> {

    public static UserInfoTabFragment instance() {
        return new UserInfoTabFragment();
    }

    public static UserInfoTabFragment instance(Bundle bundle) {
        UserInfoTabFragment fragment = new UserInfoTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @BeforeViews
    void beforViews() {
        getComponent(UserInfoComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
