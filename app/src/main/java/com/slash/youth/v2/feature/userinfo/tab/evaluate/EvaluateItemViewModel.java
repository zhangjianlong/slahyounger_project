package com.slash.youth.v2.feature.userinfo.tab.evaluate;

import android.databinding.ObservableField;
import android.view.View;

import com.core.op.lib.command.ReplyCommand;
import com.slash.youth.R;
import com.slash.youth.domain.bean.UserEvaluateBean;
import com.slash.youth.domain.bean.UserTaskBean;
import com.slash.youth.engine.FirstPagerManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.global.SlashApplication;
import com.slash.youth.utils.DistanceUtils;
import com.slash.youth.v2.base.list.BaseListItemViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import static u.aly.av.ac;

/**
 * Created by acer on 2017/3/31.
 */

public class EvaluateItemViewModel extends BaseListItemViewModel {

    public UserEvaluateBean data;

    public String uri;

    public String title;

    public EvaluateItemViewModel(RxAppCompatActivity activity, boolean isLoadComplete) {
        super(activity, isLoadComplete);
    }

    public EvaluateItemViewModel(RxAppCompatActivity activity, UserEvaluateBean data) {
        super(activity);
        this.data = data;
        uri = GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + data.getAvatar();
        title = String.format(activity.getString(R.string.app_userinfo_evaluate_item_title), data.getTitle());
    }
}
