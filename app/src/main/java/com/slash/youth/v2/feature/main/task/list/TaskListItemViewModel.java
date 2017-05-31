package com.slash.youth.v2.feature.main.task.list;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.PreferenceUtil;
import com.slash.youth.R;
import com.slash.youth.domain.bean.TaskList;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.ui.activity.DemandChooseServiceActivity;
import com.slash.youth.ui.activity.HomeActivity2;
import com.slash.youth.ui.activity.MyBidDemandActivity;
import com.slash.youth.ui.activity.MyBidServiceActivity;
import com.slash.youth.ui.activity.MyPublishDemandActivity;
import com.slash.youth.ui.activity.MyPublishServiceActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.v2.base.list.BaseListItemViewModel;
import com.slash.youth.v2.util.ShareKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;

import static com.slash.youth.v2.util.MessageKey.TASK_CHANGE;
import static com.slash.youth.v2.util.MessageKey.TASK_POINT_REFRESH;
import static com.slash.youth.v2.util.MessageKey.TASK_REFRESH;

/**
 * Created by acer on 2017/3/9.
 */

public class TaskListItemViewModel extends BaseListItemViewModel {

    public final ObservableField<Integer> vipVisible = new ObservableField<>(View.VISIBLE);

    public TaskList.TaskBean taskBean;

    public ObservableField<String> uri = new ObservableField<>();
    public String date;

    public String quote;

    public String instalmentration = "";

    public String instalment;
    public String dname;
    public String status;
    public String bidnum;
    public Drawable statusBg;

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<Drawable> drawable = new ObservableField<>();

    public ObservableField<Integer> instalmentVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> instalmentrationVisible = new ObservableField<>(View.GONE);

    public ObservableField<Integer> taskMsgVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> bidnumVisible = new ObservableField<>(View.GONE);

    String[] optionalPriceUnit = new String[]{"次", "个", "幅", "份", "单", "小时", "分钟", "天", "其他"};

    public String type;

    public final ReplyCommand click = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MESSAGE_MY_MISSON_CLICK_MISSON);

        TaskList.TaskBean myTaskBean = taskBean;

        Bundle taskInfo = new Bundle();
        taskInfo.putLong("tid", myTaskBean.tid);
        taskInfo.putInt("type", myTaskBean.type);
        taskInfo.putInt("roleid", myTaskBean.roleid);

//                ToastUtils.shortToast(myTaskBean.status + "");

        if (myTaskBean.roleid == 1) {//发布者
            if (myTaskBean.type == 1) {//我发的需求
                switch (myTaskBean.status) {
                    case 1:
                    case 4:
                    case 5:
                        Intent intentDemandChooseServiceActivity = new Intent(CommonUtils.getContext(), DemandChooseServiceActivity.class);
                        intentDemandChooseServiceActivity.putExtras(taskInfo);
                        activity.startActivityForResult(intentDemandChooseServiceActivity, HomeActivity2.REQUEST_CODE_TO_TASK_DETAIL);
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        Intent intentMyPublishDemandActivity = new Intent(CommonUtils.getContext(), MyPublishDemandActivity.class);
                        intentMyPublishDemandActivity.putExtras(taskInfo);
                        activity.startActivityForResult(intentMyPublishDemandActivity, HomeActivity2.REQUEST_CODE_TO_TASK_DETAIL);
                        break;
                    default:
                        //其它情况应该跳转到需求详情页
                        //这里有疑问，是跳到需求详情页还是四个圈的页面，暂时先写成四个圈的页面
                        Intent intentMyPublishDemandActivity2 = new Intent(CommonUtils.getContext(), MyPublishDemandActivity.class);
                        intentMyPublishDemandActivity2.putExtras(taskInfo);
                        activity.startActivityForResult(intentMyPublishDemandActivity2, HomeActivity2.REQUEST_CODE_TO_TASK_DETAIL);
                        break;
                }
            } else if (myTaskBean.type == 2) {//我发的服务
                Intent intentMyPublishServiceActivity = new Intent(CommonUtils.getContext(), MyPublishServiceActivity.class);
                intentMyPublishServiceActivity.putExtra("myTaskBean", taskBean);
                activity.startActivityForResult(intentMyPublishServiceActivity, HomeActivity2.REQUEST_CODE_TO_TASK_DETAIL);
            }

        } else if (myTaskBean.roleid == 2) {//抢单者
            if (myTaskBean.type == 1) {//我抢的需求
                switch (myTaskBean.status) {
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        Intent intentMyBidDemandActivity = new Intent(CommonUtils.getContext(), MyBidDemandActivity.class);
                        intentMyBidDemandActivity.putExtras(taskInfo);
                        activity.startActivityForResult(intentMyBidDemandActivity, HomeActivity2.REQUEST_CODE_TO_TASK_DETAIL);
                        break;
                    default:
                        //其它情况应该跳转到需求详情页
                        //这里有疑问，是跳到需求详情页还是四个圈的页面，暂时先写成四个圈的页面
                        Intent intentMyBidDemandActivity2 = new Intent(CommonUtils.getContext(), MyBidDemandActivity.class);
                        intentMyBidDemandActivity2.putExtras(taskInfo);
                        activity.startActivityForResult(intentMyBidDemandActivity2, HomeActivity2.REQUEST_CODE_TO_TASK_DETAIL);
                        break;
                }
            } else if (myTaskBean.type == 2) {//我抢的服务(我预约的服务)
                Intent intentMyBidServiceActivity = new Intent(CommonUtils.getContext(), MyBidServiceActivity.class);
                intentMyBidServiceActivity.putExtra("myTaskBean", myTaskBean);
                activity.startActivityForResult(intentMyBidServiceActivity, HomeActivity2.REQUEST_CODE_TO_TASK_DETAIL);
            }
        }

        //清空任务item对应的消息数量
        PreferenceUtil.write(CommonUtils.getContext(), "TASK_" + taskBean.tid, 0l);
        taskMsgVisible.set(View.GONE);
        Messenger.getDefault().sendNoMsg(TASK_POINT_REFRESH);
    });

    public TaskListItemViewModel(RxAppCompatActivity activity, boolean isLoadComplete) {
        super(activity, isLoadComplete);
    }

    public TaskListItemViewModel(int t) {
        switch (t) {
            case 0:
                type = "进行中";
                break;
            case 3:
                type = "历史";
                break;
        }
    }

    public TaskListItemViewModel(RxAppCompatActivity activity, TaskList.TaskBean data) {
        super(activity);
        this.taskBean = data;

        if (data.anonymity == 0 && ((data.type == 1 && data.status < 6) || (data.type == 2 && (data.status < 5 || data.status == 11)))) {
            if (!TextUtils.isEmpty(data.name)) {
                String firstName = data.name.substring(0, 1);
                String anonymityName = firstName + "xx";
                this.name.set(anonymityName);
            }
            drawable.set(activity.getResources().getDrawable(R.mipmap.anonymity_avater));
        } else {
            name.set(data.name);
            uri.set(GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + data.avatar);
        }

        date = convertStartTimeFormat(taskBean.starttime, taskBean.endtime, taskBean.type, taskBean.timetype);

        //显示报价前应该先判断是否需要显示报价
        if (taskBean.type == 1) {//需求
            if (taskBean.quote <= 0) {
                this.quote = "服务方报价";
            } else {
                this.quote = (int) (taskBean.quote) + "元";
            }
        } else if (taskBean.type == 2) {//服务
            if (taskBean.quoteunit == 9) {
                this.quote = (int) (taskBean.quote) + "元";
            } else if (taskBean.quoteunit < 9 && taskBean.quoteunit > 0) {
                this.quote = (int) (taskBean.quote) + "元/" + optionalPriceUnit[taskBean.quoteunit - 1];
            }
        }

        if (TextUtils.isEmpty(data.instalmentratio)) {
            if (data.instalment == 1) {//分期，发布任务时开启的分期，这时候还没有人抢单，所以没有分期比例信息的
                instalmentVisible.set(View.VISIBLE);
                instalmentrationVisible.set(View.GONE);
                instalment = "分期到账";
            } else {// 0 不分期
                // 未开启分期
                instalmentVisible.set(View.VISIBLE);
                instalment = "一次性到账";
                instalmentrationVisible.set(View.GONE);
            }
        } else {
            //开启分期
            //显示每一期的分期比例
            String[] instalmentratioArray = data.instalmentratio.split(",");
            if (instalmentratioArray.length <= 1) {
                instalmentVisible.set(View.VISIBLE);
                instalment = "一次性到账";
                instalmentrationVisible.set(View.GONE);
            } else {
                instalmentVisible.set(View.VISIBLE);
                instalmentrationVisible.set(View.VISIBLE);

                instalment = "分期到账";
                for (int i = 0; i < instalmentratioArray.length; i++) {
                    String ratio = instalmentratioArray[i];
                    if (TextUtils.isEmpty(ratio)) {
                        continue;
                    }
                    ratio = (int) (Double.parseDouble(ratio) * 100) + "";
                    if (i < instalmentratioArray.length - 1) {
                        instalmentration += ratio + "%/";
                    } else {
                        instalmentration += ratio + "%";
                    }
                }
            }
        }
        if (data.type == 2) {//服务
            this.dname = "需求方:" + data.dname;
        }

        if (data.type == 1) {//需求

            switch (data.status) {
                case 1:
                case 4:
                case 5:
                    status = "待抢单";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_bg);
                    break;
                case 2:
                case 3:
                case 10:
                case 11:
                    status = "已过期";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_huise);
                    break;
                case 6:
                    status = "预支付";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_bg);
                    break;
                case 7:
                case 9:
                    status = "服务中";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_bg);
                    break;
                case 8:
                    status = "已完成";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_huise);
                    break;
            }
        } else if (data.type == 2) {//服务
            switch (data.status) {
                case 1:/*初始化订单*/
                    //预约中 大状态
                    status = "已预约";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_bg);
                    break;
                case 2:/*服务者确认*/
                case 3:/*需求方支付中*/
                    //预支付 大状态
                    status = "待支付";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_bg);
                    break;
                case 5:/*订单进行中*/
                case 6:/*订单完成*/
                case 8:/*申请退款*/
                case 9:/*同意退款*/
                case 10:/*平台申诉处理*/
                    //服务中 大状态
                    status = "服务中";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_bg);
                    break;
                case 7:/*订单确认完成*/
                    //评价中 大状态
                    status = "已完成";//待评价变成已完成
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_huise);
                    break;
                case 11:/*服务方拒绝*/
                    status = "已拒绝";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_huise);
                    break;
                case 4:/*订单已经取消*/
                default:
                    //失效 过期 状态 四个圈全都是灰色
                    status = "已过期";
                    statusBg = activity.getResources().getDrawable(R.mipmap.state_huise);
                    break;
            }
        }

        //隐藏任务item上的小圆点

        //显示小圆点

        long count = PreferenceUtil.readLong(CommonUtils.getContext(), "TASK_" + taskBean.tid, 0);
        if (count > 0) {
            taskMsgVisible.set(View.VISIBLE);
        } else {
            taskMsgVisible.set(View.GONE);
        }
    }

    private String convertStartTimeFormat(long startTimeMill, long endTimeMill, int type, int timetype) {
        if (type == 1) {//需求
            if (startTimeMill <= 0) {
                return "开始时间:随时";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                return "开始时间:" + sdf.format(startTimeMill);
            }
        } else if (type == 2) {//服务
            if (startTimeMill != 0 || endTimeMill != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
                String starttimeStr = sdf.format(startTimeMill);
                String endtimeStr = sdf.format(endTimeMill);
                return starttimeStr + "-" + endtimeStr;
            } else {
                String idleTimeName = "";
                if (timetype == 1) {
                    idleTimeName = "下班后";
                } else if (timetype == 2) {
                    idleTimeName = "周末";
                } else if (timetype == 3) {
                    idleTimeName = "下班后及周末";
                } else if (timetype == 4) {
                    idleTimeName = "随时";
                }
                return idleTimeName;
            }
        }
        return "";
    }
}
