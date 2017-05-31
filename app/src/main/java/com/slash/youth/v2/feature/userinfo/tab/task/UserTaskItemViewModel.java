package com.slash.youth.v2.feature.userinfo.tab.task;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.core.op.lib.command.ReplyCommand;
import com.slash.youth.R;
import com.slash.youth.domain.bean.UserTaskBean;
import com.slash.youth.engine.FirstPagerManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.global.SlashApplication;
import com.slash.youth.ui.activity.DemandDetailActivity;
import com.slash.youth.ui.activity.ServiceDetailActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.DistanceUtils;
import com.slash.youth.v2.base.list.BaseListItemViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by acer on 2017/3/31.
 */

public class UserTaskItemViewModel extends BaseListItemViewModel {

    public UserTaskBean userTaskBean;

    public String name;

    public String title;

    public ObservableField<String> quote = new ObservableField<>();

    public final ObservableField<Integer> timeVisibility = new ObservableField<>(View.GONE);
    public final ObservableField<Integer> instalmentVisibility = new ObservableField<>(View.GONE);
    public final ObservableField<Integer> isauthVisibility = new ObservableField<>(View.GONE);

    public String freeTime;
    public String pattern;
    public String instalment;
    public String place;
    public String distance;
    public ReplyCommand click = new ReplyCommand(() -> {
        if (userTaskBean.getType() != 2) {
            Intent intentDemandDetailActivity = new Intent(CommonUtils.getContext(), DemandDetailActivity.class);
            intentDemandDetailActivity.putExtra("demandId", userTaskBean.getId());
            activity.startActivity(intentDemandDetailActivity);
        } else {
            //服务
            Intent intentServiceDetailActivity = new Intent(CommonUtils.getContext(), ServiceDetailActivity.class);
            intentServiceDetailActivity.putExtra("serviceId", userTaskBean.getId());
            activity.startActivity(intentServiceDetailActivity);
        }
    });

    public ObservableField<String> uri = new ObservableField<>();

    public UserTaskItemViewModel(RxAppCompatActivity activity, boolean isLoadComplete) {
        super(activity, isLoadComplete);
    }

    public UserTaskItemViewModel(RxAppCompatActivity activity, UserTaskBean data) {
        super(activity);
        this.userTaskBean = data;

        uri.set(GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + data.getAvatar());

        isauthVisibility.set((data.getIsauth() == 0) ? View.GONE : View.VISIBLE);
        title = data.getTitle();
        double quote = data.getQuote();
        if (quote > 0) {
            if (data.getType() == 2)
                this.quote.set(FirstPagerManager.QUOTE + quote + "元" + "/" + FirstPagerManager.QUOTEUNITS[data.getQuoteunit() - 1]);
            else
                this.quote.set(FirstPagerManager.QUOTE + quote + "元");
        } else {
            this.quote.set(FirstPagerManager.DEMAND_QUOTE);
        }

        int instalment = data.getInstalment();
        this.instalment = FirstPagerManager.SERVICE_INSTALMENT;
        switch (instalment) {
            case 0:
                instalmentVisibility.set(View.GONE);
                break;
            case 1:
                instalmentVisibility.set(View.VISIBLE);
                break;
        }

        int timetype = data.getTimetype();
        if (timetype != 0) {
            freeTime = FirstPagerManager.TIMETYPES[timetype - 1];
            timeVisibility.set(View.VISIBLE);
        } else {
            timeVisibility.set(View.GONE);
        }

        int pattern = data.getPattern();
        this.place = data.getPlace();
        double lat = data.getLat();
        double lng = data.getLng();
        switch (pattern) {
            case 0:
                this.pattern = FirstPagerManager.PATTERN_UP;
                this.place = "不限城市";
                break;
            case 1:
                this.pattern = FirstPagerManager.PATTERN_DOWN;
                double currentLatitude = SlashApplication.getCurrentLatitude();
                double currentLongitude = SlashApplication.getCurrentLongitude();
                double distance = DistanceUtils.getDistance(lat, lng, currentLatitude, currentLongitude);
                this.distance = "距您" + distance + "km";
                break;
        }
    }
}
