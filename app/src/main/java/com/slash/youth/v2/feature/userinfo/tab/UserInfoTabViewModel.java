package com.slash.youth.v2.feature.userinfo.tab;


import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.JsonUtil;
import com.slash.youth.R;
import com.slash.youth.domain.interactor.main.UserEvaluateCountUseCase;
import com.slash.youth.domain.interactor.main.UserTaskCountUseCase;
import com.slash.youth.v2.base.tab.TabViewModel;
import com.slash.youth.v2.feature.userinfo.tab.about.AboutFragment;
import com.slash.youth.v2.feature.userinfo.tab.evaluate.EvaluateFragment;
import com.slash.youth.v2.feature.userinfo.tab.task.UserTaskFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class UserInfoTabViewModel extends TabViewModel<BViewModel> {

    private long uid;

    UserEvaluateCountUseCase userEvaluateCountUseCase;

    UserTaskCountUseCase userTaskCountUseCase;

    @Inject
    public UserInfoTabViewModel(RxAppCompatActivity activity,
                                UserEvaluateCountUseCase userEvaluateCountUseCase,
                                UserTaskCountUseCase userTaskCountUseCase) {
        super(activity);
        this.userTaskCountUseCase = userTaskCountUseCase;
        this.userEvaluateCountUseCase = userEvaluateCountUseCase;
        uid = activity.getIntent().getBundleExtra("data").getLong("Uid");
        pageLimit.set(3);
        titles.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.userinfo_tabs)));
        fragments.add(AboutFragment.instance());
        fragments.add(EvaluateFragment.instance());
        fragments.add(UserTaskFragment.instance());
    }

    @Override
    public void afterViews() {
        super.afterViews();
        loadTaskCount();
        loadEvaluateCount();
    }

    public void loadTaskCount() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        map.put("anonymity", "1");
        userTaskCountUseCase.setParams(JsonUtil.mapToJson(map));
        userTaskCountUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {

                    String d = String.format(activity.getString(R.string.app_userinfo_task_title), data.getCount());
                    Spannable spannable = new SpannableString(d);
                    spannable.setSpan(new BackgroundColorSpan(Color.GRAY), 4, d.length(), 0);
                    spannable.setSpan(new AbsoluteSizeSpan(8), 4, d.length(), 0);
                    binding.tabLayout.getTabAt(2).setText(spannable);
                }, error -> {

                });
    }


    public void loadEvaluateCount() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        userEvaluateCountUseCase.setParams(JsonUtil.mapToJson(map));

        userEvaluateCountUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    String d = String.format(activity.getString(R.string.app_userinfo_evaluate_title), data.getCount());
                    Spannable spannable = new SpannableString(d);
                    spannable.setSpan(new BackgroundColorSpan(Color.GRAY), 2, d.length(), 0);
                    spannable.setSpan(new AbsoluteSizeSpan(8), 2, d.length(), 0);
                    binding.tabLayout.getTabAt(1).setText(spannable);
                }, error -> {

                });
    }
}