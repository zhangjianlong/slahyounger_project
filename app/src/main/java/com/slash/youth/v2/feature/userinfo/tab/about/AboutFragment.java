package com.slash.youth.v2.feature.userinfo.tab.about;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.databinding.FrgAboutBinding;
import com.slash.youth.v2.di.components.UserInfoComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_about)
public final class AboutFragment extends BaseFragment<AboutViewModel, FrgAboutBinding> {

    public static AboutFragment instance() {
        return new AboutFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(UserInfoComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
