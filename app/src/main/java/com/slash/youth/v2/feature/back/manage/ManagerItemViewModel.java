package com.slash.youth.v2.feature.back.manage;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.view.View;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.JsonUtil;
import com.slash.youth.domain.ManagerMyPublishTaskBean;
import com.slash.youth.domain.bean.MineManagerList;
import com.slash.youth.domain.interactor.main.DelManagerUseCase;
import com.slash.youth.domain.interactor.main.PubManagerUseCase;
import com.slash.youth.engine.FirstPagerManager;
import com.slash.youth.engine.MyManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.ui.activity.DemandDetailActivity;
import com.slash.youth.ui.activity.ServiceDetailActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.utils.SpUtils;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.base.list.BaseListItemViewModel;
import com.slash.youth.v2.feature.dialog.manage.DelManagerDialog;
import com.slash.youth.v2.feature.dialog.manage.DelManagerViewModel;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import static com.slash.youth.ui.activity.CityLocationActivity.map;


/**
 * Created by acer on 2017/3/14.
 */

public class ManagerItemViewModel extends BaseListItemViewModel {

    public MineManagerList.ListBean data;

    DelManagerUseCase useCase;

    PubManagerUseCase pubManagerUseCase;

    int position;


    public ObservableField<String> status = new ObservableField<>();
    public ObservableField<String> quote = new ObservableField<>();
    public ObservableField<String> uri = new ObservableField<>();

    public ObservableField<Integer> statusColor = new ObservableField<>();

    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> pubilcTime = new ObservableField<>();
    public ObservableField<Integer> typeVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> timeVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> delVisible = new ObservableField<>(View.VISIBLE);

    public final ReplyCommand click = new ReplyCommand(() -> {
        int type = data.getType();
        switch (type) {
            case 1:
                long demandId = data.getTid();
                Intent intentDemandDetailActivity = new Intent(CommonUtils.getContext(), DemandDetailActivity.class);
                intentDemandDetailActivity.putExtra("demandId", demandId);
                activity.startActivity(intentDemandDetailActivity);
                break;
            case 2:
                long serviceId = data.getTid();
                Intent intentServiceDetailActivity = new Intent(CommonUtils.getContext(), ServiceDetailActivity.class);
                intentServiceDetailActivity.putExtra("serviceId", serviceId);
                activity.startActivity(intentServiceDetailActivity);
                break;
        }
    });

    public final ReplyCommand delClick = new ReplyCommand(() -> {

        DelManagerDialog delManagerDialog = new DelManagerDialog(activity, new DelManagerViewModel(activity));
        delManagerDialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {
                Map<String, String> map = new HashMap<>();
                map.put("id", data.getId() + "");
                map.put("type", data.getType() + "");
                useCase.setParams(JsonUtil.mapToJson(map));
                useCase.execute().compose(activity.bindToLifecycle())
                        .subscribe(d -> {
                            if (d.getStatus() == 1) {
                                Messenger.getDefault().send(position, MessageKey.MINE_MANAGER_DEL);
                            } else if (d.getStatus() == 0) {
                                AppToast.show(activity, "删除失败");
                            } else {
                                AppToast.show(activity, "在架上无法删除");
                            }
                        }, error -> {

                        });
            }

            @Override
            public void onCancel() {
            }
        });
        delManagerDialog.show();
    });

    public ReplyCommand pubClick = new ReplyCommand(() -> {

        String text = status.get();
        int action = 0;
        if (text.equals(MyManager.UP)) {
            //下架埋点
            MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_MY_RELEASE_UNSHELVE);
            action = 1;
        } else if (text.equals(MyManager.DOWN)) {
            //上架埋点
            MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_MY_RELEASE_TASK_RESHELF);
            action = 0;
        }

        if (action != -1) {
            Map<String, String> map = new HashMap<>();
            map.put("tid", data.getTid() + "");
            map.put("type", data.getType() + "");
            map.put("action", action + "");
            pubManagerUseCase.setParams(JsonUtil.mapToJson(map));
            pubManagerUseCase.execute().compose(activity.bindToLifecycle())
                    .subscribe(d -> {
                        switch (d.getStatus()) {
                            case 1:
                                if (text.equals(MyManager.UP)) {
                                    ToastUtils.shortToast("上架成功");
                                    status.set(MyManager.DOWN);
                                    statusColor.set(Color.parseColor("#999999"));
                                } else {
                                    ToastUtils.shortToast("下架成功");
                                    status.set(MyManager.UP);
                                    statusColor.set(Color.parseColor("#31C6E4"));
                                }
                                break;
                            case 2:
                                //未实名认证
                                ToastUtils.shortToast("未认证");
                            case 5:
                                //状态错误
                                ToastUtils.shortToast("状态错误");
                                break;
                            case 4:
                                //服务端错误
                                ToastUtils.shortToast("服务端错误");
                                break;
                            case 6:
                                //未绑定手机号
                                ToastUtils.shortToast("未绑定手机号");
                                break;
                            case 7:
                                //未实名认证
                                ToastUtils.shortToast("请进行实名认证");
                                break;
                            default:
                                ToastUtils.shortToast("未知错误");
                                break;
                        }
                        Messenger.getDefault().send(position, MessageKey.MINE_MANAGER_REFRESH);
                    }, error -> {

                    });
        }
    });

    public ManagerItemViewModel(RxAppCompatActivity activity, boolean isLoadComplete) {
        super(activity, isLoadComplete);
    }

    public ManagerItemViewModel(RxAppCompatActivity activity,
                                MineManagerList.ListBean listBean,
                                DelManagerUseCase useCase,
                                PubManagerUseCase pubManagerUseCase,
                                int position) {
        this.activity = activity;
        this.data = listBean;
        this.useCase = useCase;
        this.pubManagerUseCase = pubManagerUseCase;
        this.position = position;
        setView(SpUtils.getString("myActivityTitle", ""), data);
        uri.set(GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + data.getAvatar());

    }

    //设置界面
    private void setView(String myActivityTitle, MineManagerList.ListBean data) {
        switch (myActivityTitle) {
            case MyManager.SKILL_MANAGER:
                status.set(MyManager.PUBLISH);
                break;
            case MyManager.MAANAGER_MY_PUBLISH_TASK:
                int s = data.getStatus();
                switch (s) {
                    case 0://不在架上,那我就需要把它上架
                        status.set(MyManager.UP);
                        statusColor.set(Color.parseColor("#31C6E4"));
                        break;
                    case 1://在架上，那我显示下架
                        delVisible.set(View.GONE);
                        status.set(MyManager.DOWN);
                        statusColor.set(Color.parseColor("#999999"));
                        break;
                }
                break;
        }

        int t = data.getType();

        double quote = data.getQuote();
        int instalment = data.getInstalment();
        switch (t) {
            case 1://需求

                if (instalment == 0) {
                    type.set(FirstPagerManager.DEMAND_INSTALMENT);
                }
                timeVisible.set(View.GONE);

                switch (myActivityTitle) {
                    case MyManager.SKILL_MANAGER:
                        type.set(FirstPagerManager.DEMAND_INSTALMENT);
                        switch (instalment) {
                            case 1:
                                typeVisible.set(View.GONE);
                                break;
                            case 0:
                                typeVisible.set(View.VISIBLE);
                                break;
                        }
                        break;
                    case MyManager.MAANAGER_MY_PUBLISH_TASK:
                        typeVisible.set(View.VISIBLE);
                        switch (instalment) {
                            case 1:
                                type.set(FirstPagerManager.SERVICE_INSTALMENT);
                                break;
                            case 0:
                                type.set(FirstPagerManager.DEMAND_INSTALMENT);
                                break;
                        }
                        break;
                }

                if (data.getQuote() <= 0) {
                    this.quote.set(FirstPagerManager.DEMAND_QUOTE);
                } else {
                    this.quote.set(MyManager.QOUNT + (int) data.getQuote() + "元");
                }

                break;
            case 2://服务
                if (instalment == 1) {
                    type.set(FirstPagerManager.SERVICE_INSTALMENT);
                }

                timeVisible.set(View.VISIBLE);

                switch (myActivityTitle) {
                    case MyManager.SKILL_MANAGER:
                        type.set(FirstPagerManager.SERVICE_INSTALMENT);
                        switch (instalment) {
                            case 1:
                                typeVisible.set(View.VISIBLE);
                                break;
                            case 0:
                                typeVisible.set(View.GONE);
                                break;
                        }
                        break;
                    case MyManager.MAANAGER_MY_PUBLISH_TASK:
                        typeVisible.set(View.VISIBLE);
                        switch (instalment) {
                            case 1:
                                type.set(FirstPagerManager.SERVICE_INSTALMENT);
                                break;
                            case 0:
                                type.set(FirstPagerManager.DEMAND_INSTALMENT);
                                break;
                        }
                        break;
                }

                int quoteUnit = data.getQuoteUnit();
                if (quoteUnit == 9) {
                    this.quote.set(MyManager.QOUNT + (int) quote + "元");
                } else if (quoteUnit > 0 && quoteUnit < 9) {
                    this.quote.set(MyManager.QOUNT + (int) quote + "元/" + MyManager.unitArr[quoteUnit - 1]);
                } else {//这种情况应该不存在
                    this.quote.set(MyManager.QOUNT + (int) quote + "元");
                }

                int timetype = data.getTimetype();
                if (timetype == 0) {
                    // itemManagePublishHolderBinding.tvManageMyPublishTime.setText( startData + "-" + endData);
                } else {
                    pubilcTime.set(FirstPagerManager.TIMETYPES[timetype - 1]);
                }
                break;
        }

        delVisible.set(View.GONE);
    }
}
