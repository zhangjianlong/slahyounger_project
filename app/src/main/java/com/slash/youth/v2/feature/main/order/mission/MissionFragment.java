package com.slash.youth.v2.feature.main.order.mission;

import com.slash.youth.R;
import com.slash.youth.v2.base.BaseFragment;
import com.slash.youth.databinding.FrgMissionBinding;
import com.slash.youth.v2.base.list.BaseListFragment;
import com.slash.youth.v2.di.components.MainComponent;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

@RootView(R.layout.frg_mission)
public final class MissionFragment extends BaseListFragment<MissionViewModel> {

    public static MissionFragment instance() {
        return new MissionFragment();
    }

    @BeforeViews
    void beforViews() {
        getComponent(MainComponent.class).inject(this);
    }

    @AfterViews
    void afterViews() {
    }
}
