package com.slash.youth.v2.feature.main;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.core.op.bindingadapter.bottomnavigation.NavigationRes;
import com.core.op.bindingadapter.bottomnavigation.ViewBindingAdapter;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.R;
import com.slash.youth.databinding.ActMainBinding;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.ui.activity.PublishDemandBaseInfoActivity;
import com.slash.youth.ui.activity.PublishServiceBaseInfoActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.feature.main.find.FindFragment;
import com.slash.youth.v2.feature.main.mine.MineFragment;
import com.slash.youth.v2.feature.main.order.OrderFragment;
import com.slash.youth.v2.feature.main.task.TaskFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static android.R.attr.data;
import static com.slash.youth.v2.feature.main.mine.MineViewModel.START_ANIMATION;
import static com.slash.youth.v2.util.MessageKey.SHOW_MAIN_PUG;
import static com.slash.youth.v2.util.MessageKey.SHOW_NAVIGATION;
import static com.slash.youth.v2.util.MessageKey.TASK_CHANGE;
import static com.umeng.socialize.Config.dialog;

@PerActivity
public class MainViewModel extends BAViewModel<ActMainBinding> {
    public static final String CHANG_POSITION = "CHANG_POSITION";

    public FragmentManager fragmentManager;

    public final ObservableField<Integer> selectPosition = new ObservableField<>(0);

    public final ObservableField<Integer> pageLimit = new ObservableField<>(3);

    public final ObservableField<Integer> pubVisible = new ObservableField<>(View.GONE);

    public final List<Fragment> fragments = new ArrayList<>();

    private boolean isPub = false;
    Handler handler = new Handler();
    public final NavigationRes navigation = NavigationRes.of(R.array.tab_colors, R.menu.bottom_navigation_main).setAccent(R.color.app_theme_colorPrimary)
            .setDefaultBackground(R.color.app_theme_background);

    public ReplyCommand cancel = new ReplyCommand(() -> {
        startOutAnim();
    });

    public ReplyCommand pubClick = new ReplyCommand(() -> {
    });

    public ReplyCommand pubDemandClick = new ReplyCommand(() -> {
        Intent intentPublishDemandBaseInfoActivity = new Intent(CommonUtils.getContext(), PublishDemandBaseInfoActivity.class);
        activity.startActivity(intentPublishDemandBaseInfoActivity);
        startOutAnim();
    });

    public ReplyCommand pubServerClick = new ReplyCommand(() -> {
        Intent intentPublishServiceBaseInfoActivity = new Intent(CommonUtils.getContext(), PublishServiceBaseInfoActivity.class);
        activity.startActivity(intentPublishServiceBaseInfoActivity);
        startOutAnim();
    });

    public final ReplyCommand<ViewBindingAdapter.NavigationDataWrapper> selectedCommand = new ReplyCommand<>(p -> {
        selectPosition.set(p.position);
        if (p.position == 2) {
            Messenger.getDefault().sendNoMsg(START_ANIMATION);
        }
    });

    @Inject
    public MainViewModel(RxAppCompatActivity activity) {
        super(activity);
        fragmentManager = activity.getSupportFragmentManager();
        fragments.add(new FindFragment());
        fragments.add(new OrderFragment());
        fragments.add(new MineFragment());
    }

    @Override
    public void afterViews() {
        Messenger.getDefault().register(this, CHANG_POSITION, () -> {
            selectPosition.set(0);
        });
        Messenger.getDefault().register(this, SHOW_NAVIGATION, () -> {
            if (binding.bottomNavigation.isHidden())
                binding.bottomNavigation.restoreBottomNavigation(true);
        });

        Messenger.getDefault().register(this, TASK_CHANGE, Integer.class, d -> {
            binding.bottomNavigation.setNotification("" + (d == 0 ? "" : d),
                    1);
        });

        Messenger.getDefault().register(this, SHOW_MAIN_PUG, () -> {
            isPub = true;
            pubVisible.set(View.VISIBLE);
            startInAnim();
        });

    }

    private void startInAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.slDemand, "translationY", 0.0f, 600.0f);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(300).start();

        Observable.timer(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.slServer, "translationY", 0.0f, 600.0f);
                        animator.setInterpolator(new OvershootInterpolator());
                        animator.setDuration(500).start();
                    }
                });
    }

    private void startOutAnim() {
        isPub = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.slDemand, "translationY", 600.0f, 0.0f);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(300).start();

        Observable.timer(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.slServer, "translationY", 600.0f, 0.0f);
                        animator.setInterpolator(new OvershootInterpolator());
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                pubVisible.set(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        animator.setDuration(500).start();
                    }
                });
    }

    public boolean onBackPressed() {
        if (isPub) {
            startOutAnim();
            return false;
        }
        return true;
    }
}