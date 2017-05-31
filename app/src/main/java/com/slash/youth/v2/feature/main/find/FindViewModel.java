package com.slash.youth.v2.feature.main.find;


import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.PreferenceUtil;
import com.core.op.lib.weight.EmptyLayout;
import com.core.op.lib.weight.RecycleScrollView;
import com.core.op.lib.weight.loading.Indicator;
import com.core.op.lib.weight.swipe.SwipeRefreshLayout;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.FrgFindBinding;
import com.slash.youth.domain.bean.FindDemand;
import com.slash.youth.domain.bean.FindServices;
import com.slash.youth.domain.interactor.main.BannerUseCase;
import com.slash.youth.domain.interactor.main.FindDemandUseCase;
import com.slash.youth.domain.interactor.main.FindServiceUseCase;
import com.slash.youth.domain.interactor.main.TagUseCase;
import com.slash.youth.ui.activity.FirstPagerMoreActivity;
import com.slash.youth.ui.activity.PublishActivity;
import com.slash.youth.ui.activity.SearchActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.v2.base.list.ListViewModel;
import com.slash.youth.v2.feature.dialog.find.PubDialog;
import com.slash.youth.v2.feature.main.MainActivity;
import com.slash.youth.v2.feature.pub.PubActivity;
import com.slash.youth.v2.feature.task.PubDetailActivity;
import com.slash.youth.v2.util.ShareKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

import static com.slash.youth.v2.util.MessageKey.SHOW_MAIN_PUG;


@PerActivity
public class FindViewModel extends ListViewModel<FindItemViewModel, FrgFindBinding> {

    private boolean isFirstSuccess = false;

    private BannerUseCase bannerUseCase;

    private TagUseCase tagUseCase;

    FindServiceUseCase findServiceUseCase;
    FindDemandUseCase findDemandUseCase;

    PubDialog dialog;
    public final ObservableField<Integer> titleVisible = new ObservableField<>(View.VISIBLE);

    public final ItemView bItemView = ItemView.of(BR.viewModel, R.layout.item_main_find_banner_item);
    public final ObservableList<FindBannerItemViewModel> bViewModels = new ObservableArrayList<>();

    public final ItemView tItemView = ItemView.of(BR.viewModel, R.layout.item_main_find_tags_item);
    public final List<FindTagItemViewModel> tItemViewModel = new ArrayList<>();

    public final ItemView lItemView = ItemView.of(BR.viewModel, R.layout.item_main_find);
    public final List<FindItemViewModel> lItemViewModels = new ArrayList<>();

    public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.item_main_find);
    public final List<FindItemViewModel> itemViewModels = new ArrayList<>();

    public boolean isDemand = false;//如果存true，表示展示需求列表，false为展示服务列表,默认为false

    public final ReplyCommand click = new ReplyCommand(() -> {
    });

    public final ReplyCommand search = new ReplyCommand(() -> {
//        Intent intentSearchActivity = new Intent(CommonUtils.getContext(), SearchActivity.class);
//        activity.startActivity(intentSearchActivity);
        com.slash.youth.v2.feature.search.SearchActivity.instance(activity);
    });

    public final ReplyCommand pub = new ReplyCommand(() -> {
//        Intent intentPublishActivity = new Intent(CommonUtils.getContext(), PublishActivity.class);
//        activity.startActivity(intentPublishActivity);
//        Messenger.getDefault().sendNoMsg(SHOW_MAIN_PUG);
        PubActivity.instance(activity);
//        PubDetailActivity.instance(activity);
    });

    public final ReplyCommand moreDemand = new ReplyCommand(() -> {
        more(isDemand);
    });

    public final ReplyCommand moreService = new ReplyCommand(() -> {
        more(isDemand);
    });

    @Inject
    public FindViewModel(RxAppCompatActivity activity,
                         PubDialog dialog,
                         BannerUseCase bannerUseCase,
                         TagUseCase tagUseCase,
                         FindServiceUseCase findServiceUseCase,
                         FindDemandUseCase findDemandUseCase) {
        super(activity);
        this.dialog = dialog;
        this.bannerUseCase = bannerUseCase;
        this.tagUseCase = tagUseCase;
        this.findDemandUseCase = findDemandUseCase;
        this.findServiceUseCase = findServiceUseCase;
    }

    @Override
    public void afterViews() {
        super.afterViews();
        PreferenceUtil.write(CommonUtils.getContext(), ShareKey.ISDEMAND, isDemand);
        errorVisible.set(View.VISIBLE);
//        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.likeRecyclerView.setNestedScrollingEnabled(false);
        binding.scScroll.smoothScrollTo(0, 0);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.app_theme_colorPrimary);
        binding.swipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        binding.rlTitleBar.setAlpha(0);
        binding.scScroll.setOnScrollChangedListener(new RecycleScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(NestedScrollView scrollView, int l, int t, int oldl, int oldt) {
                float headHeight = 600 - binding.rlTitleBar.getMeasuredHeight();
                int alpha = (int) (((float) t / headHeight) * 255);
                if (alpha >= 155)
                    alpha = 220;
                if (alpha <= 0)
                    alpha = 0;
                binding.rlTitleBar.setAlpha(alpha / 255f);
            }
        });

//        binding.dtChoice.setServiceDemandToggle((isCheckedService) -> {
//            if (isCheckedService) {
//                loadService(false);
//                isDemand = false;
//            } else {
//                isDemand = true;
//                loadRecommand(false);
//            }
//            PreferenceUtil.write(CommonUtils.getContext(), ShareKey.ISDEMAND, isDemand);
//        });

        binding.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setSelectedVpPointer(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.swipeRefreshLayout.setOnDragListener(new SwipeRefreshLayout.OnDragListener() {
            @Override
            public void onStartDrag() {
                startOutAnim();
            }

            @Override
            public void onStopDragNoRefresh() {
                startInAnim();
            }
        });
//        binding.dtChoice.checkedService();
        titleVisible.set(View.GONE);
        initBanner();
        loadData();

    }

    @Override
    public void loadMore() {
    }

    @Override
    public void refresh() {
        Observable.timer(1, TimeUnit.SECONDS).subscribe(data -> {
            if (!isFirstSuccess)
                initBanner();
            loadData();
        });
    }

    private void initBanner() {

        bannerUseCase.execute().compose(activity.bindToLifecycle())
                .flatMap(data -> {
                    bViewModels.clear();
                    return Observable.from(data.getBanner());
                })
                .subscribe(data -> {
                    bViewModels.add(new FindBannerItemViewModel(activity, data));
                }, error -> {
                }, () -> {
                    isFirstSuccess = true;
                    initIndicator();
                });
    }

    private void loadData() {
        isRefreshing.set(true);

        tagUseCase.execute().compose(activity.bindToLifecycle())
                .flatMap(data -> {
                    tItemViewModel.clear();
                    return Observable.from(data.tag);
                })
                .subscribe(data -> {
                    tItemViewModel.add(new FindTagItemViewModel(activity, data));
                }, error -> {
                    startInAnim();
                    isRefreshing.set(false);
                }, () -> {
                    binding.idRecyclerviewHorizontal.getAdapter().notifyDataSetChanged();
                    startInAnim();
                    isRefreshing.set(false);
                });

//        loadService(true);
    }

    private void initIndicator() {
        binding.llIndicator.removeAllViews();
        for (int i = 0; i < bViewModels.size(); i++) {
            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(CommonUtils.dip2px(6), CommonUtils.dip2px(6));
            if (i > 0) {
                llParams.leftMargin = CommonUtils.dip2px(10);
            }
            View vPoint = new View(CommonUtils.getContext());
            vPoint.setLayoutParams(llParams);
            binding.llIndicator.addView(vPoint);
        }
        int currentItem = binding.banner.getViewPager().getCurrentItem();
        setSelectedVpPointer(currentItem);
    }

    private void setSelectedVpPointer(int index) {
        //这里需要做判断，因为可能执行这段代码的时候，bannerList中的数据还没有从网络加载完毕
        if (bViewModels != null && bViewModels.size() > 0) {
            index = index % bViewModels.size();
            int childCount = binding.llIndicator.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View vPoint = binding.llIndicator.getChildAt(i);
                if (i == index) {//选中
                    vPoint.setBackgroundResource(R.drawable.shape_vpindicator_selected);
                } else {//未选中
                    vPoint.setBackgroundResource(R.drawable.shape_vpindicator_unselected);
                }
            }
        }
    }

//    private void loadRecommand(boolean isRefresh) {
//        findDemandUseCase.cleanParams();
//        findDemandUseCase.setParams("{\"limit\":\"10\"}");
//        findDemandUseCase.execute().compose(activity.bindToLifecycle())
//                .flatMap(data -> {
//                    lItemViewModels.clear();
//                    itemViewModels.clear();
//                    if (data.getRadlist().size() == 0 && data.getReclist().size() == 0) {
//                        errorVisible.set(View.VISIBLE);
//                        errorType.set(EmptyLayout.NODATA);
//                    } else {
//                        errorVisible.set(View.GONE);
//                    }
//                    if (data.getReclist() != null && data.getReclist().size() != 0) {
//                        for (FindDemand.ListBean listBean : data.getReclist()) {
//                            lItemViewModels.add(new FindItemViewModel(activity, listBean));
//                        }
//                    }
//                    binding.likeRecyclerView.getAdapter().notifyDataSetChanged();
//                    return Observable.from(data.getRadlist());
//                })
//                .subscribe(data -> {
//                    itemViewModels.add(new FindItemViewModel(activity, data));
//                }, error -> {
//                    if (isRefresh) {
//                        startInAnim();
//                        isRefreshing.set(false);
//                    }
//                }, () -> {
//                    binding.recyclerView.getAdapter().notifyDataSetChanged();
//
//                    if (isRefresh) {
//                        isRefreshing.set(false);
//                        startInAnim();
//                    }
//                });
//    }
//
//    private void loadService(boolean isRefresh) {
//        findServiceUseCase.cleanParams();
//        findServiceUseCase.setParams("{\"limit\":\"10\"}");
//        findServiceUseCase.execute().compose(activity.bindToLifecycle())
//                .flatMap(data -> {
//                    lItemViewModels.clear();
//                    itemViewModels.clear();
//                    if (data.getRadlist().size() == 0 && data.getReclist().size() == 0) {
//                        errorVisible.set(View.VISIBLE);
//                        errorType.set(EmptyLayout.NODATA);
//                    } else {
//                        errorVisible.set(View.GONE);
//                    }
//                    if (data.getReclist() != null && data.getReclist().size() != 0) {
//                        for (FindServices.ListBean listBean : data.getReclist()) {
//                            lItemViewModels.add(new FindItemViewModel(activity, listBean));
//                        }
//                    }
//                    binding.likeRecyclerView.getAdapter().notifyDataSetChanged();
//                    return Observable.from(data.getRadlist());
//                })
//                .subscribe(data -> {
//                    itemViewModels.add(new FindItemViewModel(activity, data));
//                }, error -> {
//                    if (isRefresh) {
//                        startInAnim();
//                        isRefreshing.set(false);
//                    }
//                }, () -> {
//                    binding.recyclerView.getAdapter().notifyDataSetChanged();
//                    if (isRefresh) {
//                        startInAnim();
//                        isRefreshing.set(false);
//                    }
//                });
//    }

    private void more(boolean isDemand) {
        Intent intentFirstPagerMoreActivity = new Intent(CommonUtils.getContext(), FirstPagerMoreActivity.class);
        intentFirstPagerMoreActivity.putExtra("isDemand", isDemand);
        intentFirstPagerMoreActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CommonUtils.getContext().startActivity(intentFirstPagerMoreActivity);
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.IDLE_TIME_CLICK_BOTTOM_MORE);
    }

    @Override
    public ItemViewSelector<FindItemViewModel> itemView() {
        return new BaseItemViewSelector<FindItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, FindItemViewModel item) {
                itemView.set(BR.viewModel, R.layout.item_main_find);
            }
        };
    }

    protected void startOutAnim() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim_alpha_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                titleVisible.set(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        binding.rlTitle.startAnimation(animation);
    }

    protected void startInAnim() {
        titleVisible.set(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim_alpha_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        binding.rlTitle.startAnimation(animation);
    }
}