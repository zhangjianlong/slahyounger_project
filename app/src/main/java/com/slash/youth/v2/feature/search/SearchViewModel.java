package com.slash.youth.v2.feature.search;


import android.content.Context;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.utils.SPUtils;
import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.utils.PreferenceUtil;
import com.core.op.lib.utils.StrUtil;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.data.util.SpUtil;
import com.slash.youth.databinding.ActSearchBinding;
import com.slash.youth.domain.bean.AssociateBean;
import com.slash.youth.domain.interactor.main.GetAssociateUseCase;
import com.slash.youth.domain.interactor.main.SearchDataUseCase;
import com.slash.youth.utils.PatternUtils;
import com.slash.youth.v2.util.MessageKey;
import com.slash.youth.v2.util.ShareKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

import static com.slash.youth.R.id.view;
import static com.slash.youth.R.string.search;
import static com.slash.youth.ui.activity.CityLocationActivity.map;

@PerActivity
public class SearchViewModel extends BAViewModel<ActSearchBinding> {
    private final int HISTORY_TYPE = 1;
    private final int ASSOCIATE_TYPE = 2;
    private final int SERACH_TYPE = 3;
    private int CURRENT_TYPE = HISTORY_TYPE;

    public final List<SeachItemViewModel> itemViewModels = new ArrayList<>();
    public final ItemViewSelector<SeachItemViewModel> itemView = itemView();
    private List<String> historyData = new ArrayList<>();
    private GetAssociateUseCase getAssociateUseCase;
    private SearchDataUseCase searchDataUseCase;
    private boolean blockSearch;

    @Inject
    public SearchViewModel(RxAppCompatActivity activity, GetAssociateUseCase getAssociateUseCase, SearchDataUseCase searchDataUseCase) {
        super(activity);
        this.getAssociateUseCase = getAssociateUseCase;
        this.searchDataUseCase = searchDataUseCase;
    }

    @Override
    public void afterViews() {
        initData();
        loadData();
        initSerachView();
        Messenger.getDefault().register(this, MessageKey.SEARCH_CLICK, String.class, data -> {
            blockSearch = true;
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.serachview.getWindowToken(), 0);
            searchData(data);
            initData();
            saveData(data);
        });
        Messenger.getDefault().register(this, MessageKey.DEL_CLICK, () -> {
            cleanHistory();

        });

    }

    private void searchData(String data) {
        binding.serachview.setQuery(data, false);
        Map<String, String> map = new HashMap<>();
        map.put("tag", data);
        CURRENT_TYPE = SERACH_TYPE;
        itemViewModels.clear();
        binding.recyclerView.getAdapter().notifyDataSetChanged();
//        searchDataUseCase.setParams(JsonUtil.mapToJson(map));
//        searchDataUseCase.execute().compose(activity.bindToLifecycle()).subscribe();

    }

    private void initSerachView() {
        binding.serachview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (blockSearch) {
                    blockSearch = false;
                    return false;

                }
                if (StrUtil.isEmpty(newText)) {
                    CURRENT_TYPE = HISTORY_TYPE;
                    loadData();
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                    return false;
                }
                switch (newText.length()) {
                    case 1:
                        boolean match = PatternUtils.match(PatternUtils.chineseRegex, newText);
                        if (!match) {
                            break;
                        }
                    default:
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("tag", newText);
                        map.put("offset", "0");
                        map.put("limit", "5");
                        getAssociateUseCase.setParams(JsonUtil.mapToJson(map));
                        getAssociateUseCase.execute().compose(activity.bindToLifecycle())
                                .flatMap(d -> {
                                    if (!(d.getList() != null && d.getList().size() > 0)) {
                                        return null;
                                    }
                                    itemViewModels.clear();
                                    CURRENT_TYPE = ASSOCIATE_TYPE;
                                    return Observable.from(d.getList());
                                })
                                .subscribe(data -> {
                                    itemViewModels.add(new SeachItemViewModel(data.getTag()));
                                }, error -> {
                                }, () -> {
                                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                                });
                }
                return false;
            }
        });
    }

    private void initData() {
        historyData.clear();
        historyData.addAll((Collection<? extends String>) SpUtil.readObject(ShareKey.HOSTORYDATA));

    }

    private void saveData(String searchKey) {
        if (historyData.size() >= 5) {
            historyData.remove(0);
        }
        if (!historyData.contains(searchKey)) {
            historyData.add(searchKey);
            SpUtil.saveObject((Serializable) historyData, ShareKey.HOSTORYDATA);
        }

    }

    private void cleanHistory() {
        historyData.clear();
        SpUtil.saveObject((Serializable) historyData, ShareKey.HOSTORYDATA);
        itemViewModels.clear();
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void loadData() {
        itemViewModels.clear();
        itemViewModels.add(new SeachItemViewModel("历史搜索"));
        for (String temp : historyData) {
            itemViewModels.add(new SeachItemViewModel(temp));
        }
        itemViewModels.add(new SeachItemViewModel());
    }


    private ItemViewSelector<SeachItemViewModel> itemView() {
        return new BaseItemViewSelector<SeachItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, SeachItemViewModel item) {
                switch (CURRENT_TYPE) {
                    case HISTORY_TYPE:
                        if (position == 0) {
                            itemView.set(BR.viewModel, R.layout.item_search_history_head);
                            return;
                        }
                        if (1 <= position && position <= historyData.size()) {
                            itemView.set(BR.viewModel, R.layout.item_search_history);
                        } else {
                            itemView.set(BR.viewModel, R.layout.item_search_del_history);
                        }
                        break;
                    case ASSOCIATE_TYPE:
                        itemView.set(BR.viewModel, R.layout.item_search_history);
                        break;
                    case SERACH_TYPE:
                        break;
                }
            }
        };
    }
}