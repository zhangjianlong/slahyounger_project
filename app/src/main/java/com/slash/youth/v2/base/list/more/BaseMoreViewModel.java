package com.slash.youth.v2.base.list.more;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.weight.EmptyLayout;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgBasemoreBinding;
import com.slash.youth.domain.bean.base.BaseList;
import com.slash.youth.domain.interactor.UseCase;
import com.slash.youth.v2.base.list.BaseListItemViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * 加载更多viewModel
 *
 * @param <V>
 * @param <T>
 */
public abstract class BaseMoreViewModel<V, T extends BaseListItemViewModel> extends BFViewModel<FrgBasemoreBinding> {

    public int pageSize = 0;

    public int limit = 10;

    public boolean isLoadMore = true;

    public boolean isComplate = false;

    private int typeCount = 0;

    public final List<T> itemViewModels = new ArrayList<>();

    public final ItemViewSelector<T> itemView = itemView();


    public final ObservableField<Integer> errorVisible = new ObservableField(View.GONE);

    public final ObservableField<Integer> errorType = new ObservableField(EmptyLayout.HIDE_LAYOUT);

    public final ReplyCommand errorClick = new ReplyCommand<>(() -> {
    });

    public final ReplyCommand<Integer> onLoadMoreCommand = new ReplyCommand<>((p) -> {
        if (isLoadMore)
            loadMore();
    });


    public BaseMoreViewModel(RxAppCompatActivity activity) {
        super(activity);
    }


    public void loadMore() {
        if (!isComplate) {
            pageSize++;
            Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(d -> {
                loadData(true);
            });
        }
    }

    public void refresh() {
        pageSize = 0;
        isComplate = false;
        loadData(false);
    }

    public abstract UseCase<BaseList<V>> useCase();

    public abstract void addData(V v);

    public abstract void doComplate();

    public abstract int setItem(ItemView itemView, int position, T item);

    protected void doListData(BaseList<V> data, boolean isLoadMore) {
    }

    protected void doData(V data, boolean isLoadMore) {
    }

    public Map<String, String> prams() {
        Map<String, String> map = new HashMap<>();
        if (isLoadMore) {
            map.put("offset", (pageSize * limit) + "");
            map.put("limit", limit + "");
        }
        return map;
    }

    protected void loadData(boolean loadMore) {
        if (prams() != null) {
            useCase().setParams(JsonUtil.mapToJson(prams()));
        }
        useCase().execute().compose(activity.bindToLifecycle())
                .doOnNext(data -> {
                    if (data == null || data.getList() == null || data.getList().size() == 0) {
                        errorVisible.set(View.VISIBLE);
                        errorType.set(EmptyLayout.NODATA);
                    }
                    if (data.getList() != null && data.getList().size() < limit) {
                        isComplate = true;
                    }
                    if (!loadMore) {
                        itemViewModels.clear();
                    } else {
                        itemViewModels.remove(itemViewModels.size() - 1);
                    }
                    doListData(data, loadMore);//list data 返回
                })
                .flatMap(d ->
                        Observable.from(d.getList())
                )
                .doOnNext(d -> {
                    doData(d, isLoadMore);//单个data 返回
                })
                .subscribe(d -> {
                    addData(d);
                }, error -> {
                }, () -> {
                    doComplate();
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                });

    }

    public ItemViewSelector<T> itemView() {
        return new BaseItemViewSelector<T>() {
            @Override
            public void select(ItemView itemView, int position, T item) {
                if (isLoadMore && !isComplate && position == itemViewModels.size() - 1) {
                    itemView.set(BR.viewModel, R.layout.item_loadmore);
                } else {
                    typeCount = setItem(itemView, position, item);
                }
            }

            @Override
            public int viewTypeCount() {
                return typeCount + (isLoadMore ? 1 : 0);
            }
        };
    }

}