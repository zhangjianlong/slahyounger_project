package com.slash.youth.v2.feature.back.manage;


import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.utils.PreferenceUtil;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.data.Global;
import com.slash.youth.databinding.FrgManagerBinding;
import com.slash.youth.domain.bean.MineManagerList;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.domain.interactor.main.DelManagerUseCase;
import com.slash.youth.domain.interactor.main.MineManagerListUseCase;
import com.slash.youth.domain.interactor.main.PubManagerUseCase;
import com.slash.youth.v2.base.list.BaseListViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

import static com.slash.youth.ui.activity.CityLocationActivity.map;
import static com.slash.youth.v2.util.MessageKey.MINE_MANAGER_DEL;
import static com.slash.youth.v2.util.MessageKey.MINE_MANAGER_REFRESH;

@PerActivity
public class ManagerViewModel extends BaseListViewModel<MineManagerList.ListBean, ManagerItemViewModel> {
    MineManagerListUseCase useCase;
    DelManagerUseCase delManagerUseCase;
    PubManagerUseCase pubManagerUseCase;
    int index = 0;

    @Inject
    public ManagerViewModel(RxAppCompatActivity activity,
                            MineManagerListUseCase useCase,
                            PubManagerUseCase pubManagerUseCase,
                            DelManagerUseCase delManagerUseCase) {
        super(activity);
        this.useCase = useCase;
        this.delManagerUseCase = delManagerUseCase;
        this.pubManagerUseCase = pubManagerUseCase;
    }

    @Override
    public void afterViews() {
        super.afterViews();


        Messenger.getDefault().register(activity, MINE_MANAGER_DEL, Integer.class, position -> {
            itemViewModels.remove(position);
            binding.recyclerView.getAdapter().notifyDataSetChanged();
        });

        Messenger.getDefault().register(activity, MINE_MANAGER_REFRESH, Integer.class, position -> {
            binding.recyclerView.getAdapter().notifyDataSetChanged();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public UseCase<BaseList<MineManagerList.ListBean>> useCase() {
        return useCase;
    }

    @Override
    public void addData(MineManagerList.ListBean listBean) {
        itemViewModels.add(new ManagerItemViewModel(activity, listBean, delManagerUseCase, pubManagerUseCase, index));
        index++;
    }

    @Override
    protected void doListData(BaseList<MineManagerList.ListBean> data, boolean isLoadMore) {
        if (!isLoadMore) {
            index = 0;
        }
    }

    @Override
    public void doComplate() {
        if (!isComplate)
            itemViewModels.add(new ManagerItemViewModel(activity, isComplate));
    }

    @Override
    public int setItem(ItemView itemView, int position, ManagerItemViewModel item) {
        itemView.set(BR.viewModel, R.layout.item_manage);
        return 1;
    }

}