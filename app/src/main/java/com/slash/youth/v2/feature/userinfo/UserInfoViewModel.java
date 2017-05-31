package com.slash.youth.v2.feature.userinfo;


import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.DoubleUtil;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.weight.swipe.SwipeRefreshLayout;
import com.slash.youth.R;
import com.slash.youth.databinding.ActUserinfoBinding;
import com.slash.youth.domain.ChatCmdBusinesssCardBean;
import com.slash.youth.domain.bean.OtherInfo;
import com.slash.youth.domain.bean.UserVisibleBean;
import com.slash.youth.domain.interactor.main.OtherInfoUseCase;
import com.slash.youth.domain.interactor.main.UserAddFriendUseCase;
import com.slash.youth.domain.interactor.main.UserAgreeFriendUseCase;
import com.slash.youth.domain.interactor.main.UserRemoveFriendUseCase;
import com.slash.youth.domain.interactor.main.UserStatusUseCase;
import com.slash.youth.domain.interactor.main.UserVisibleUseCase;
import com.slash.youth.engine.ContactsManager;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.engine.UserInfoEngine;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.ui.activity.ChatActivity;
import com.slash.youth.ui.activity.MyFriendActivtiy;
import com.slash.youth.ui.activity.ReportTAActivity;
import com.slash.youth.ui.activity.UserinfoEditorActivity;
import com.slash.youth.ui.viewmodel.ActivityUserInfoModel;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.Constants;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.utils.LogKit;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.feature.dialog.report.ReportDialog;
import com.slash.youth.v2.feature.dialog.share.ShareDialog;
import com.slash.youth.v2.feature.edit.PersonalEditActivity;
import com.slash.youth.v2.feature.userinfo.tab.UserInfoTabFragment;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static io.rong.imlib.statistics.UserData.name;

@PerActivity
public class UserInfoViewModel extends BAViewModel<ActUserinfoBinding> {

    long uid;


    private int friendStatus = -1;
    public final ObservableField<Integer> titleVisible = new ObservableField<>(View.VISIBLE);
    public final ObservableField<Integer> authVisible = new ObservableField<>(View.GONE);
    public final ObservableField<Integer> otherVisible = new ObservableField<>(View.GONE);
    public final ObservableField<Drawable> sexIcon = new ObservableField<>();

    public final ObservableField<Boolean> isRefreshing = new ObservableField<>(false);

    public final ReplyCommand onRefreshCommand = new ReplyCommand<>(() -> {
        refresh();
    });

    public final ReplyCommand chatClick = new ReplyCommand<>(() -> {
        final Intent intentChatActivity = new Intent(CommonUtils.getContext(), ChatActivity.class);
        intentChatActivity.putExtra("targetId", uid);
        activity.startActivity(intentChatActivity);
    });


    OtherInfoUseCase useCase;
    ReportDialog reportDialog;
    ShareDialog shareDialog;
    UserStatusUseCase userStatusUseCase;
    UserVisibleUseCase userVisibleUseCase;
    UserAddFriendUseCase addFriendUseCase;
    UserRemoveFriendUseCase removeFriendUseCase;
    UserAgreeFriendUseCase agreeFriendUseCase;

    UserVisibleBean userVisibleBean;


    public ObservableField<String> uri = new ObservableField<>();
    public ObservableField<String> relationCount = new ObservableField<>();
    public ObservableField<String> relationProgress = new ObservableField<>();
    public ObservableField<String> taskCount = new ObservableField<>();
    public ObservableField<String> taskProgress = new ObservableField<>();
    public ObservableField<String> serviceCount = new ObservableField<>();
    public ObservableField<String> serviceProgress = new ObservableField<>();
    public ObservableField<String> level = new ObservableField<>();
    public ObservableField<String> company = new ObservableField<>();
    public ObservableField<String> removeFriend = new ObservableField<>("");
    public ObservableField<OtherInfo.UinfoBean> data = new ObservableField<>();

    public final ReplyCommand share = new ReplyCommand<>(() -> {
        if (!shareDialog.isShowing()) {
            shareDialog.show();
        }
    });

    public final ReplyCommand addClick = new ReplyCommand<>(() -> {
        switch (friendStatus) {
            case 0://现在是陌生人，点击之后是加好友
                addFriend();
                break;
            case 1://现在是我已申请状态，点击之后没有效果
                removeFriend.set(ContactsManager.ADD_FRIEND_APPLICATION);
                ToastUtils.shortToast("您已申请加好友");
                break;
            case 2://人家向我发出好友申请,我显示同意，点击之后变成解除好友
                agreeFriend();
                break;
            case 3://现在解除好友，点击一下,变成加好友
                removeFriend();
                break;
        }
    });

    @Inject
    public UserInfoViewModel(RxAppCompatActivity activity,
                             OtherInfoUseCase useCase,
                             UserStatusUseCase userStatusUseCase,
                             UserAddFriendUseCase addFriendUseCase,
                             UserRemoveFriendUseCase removeFriendUseCase,
                             UserAgreeFriendUseCase agreeFriendUseCase,
                             UserVisibleUseCase userVisibleUseCase,
                             ReportDialog reportDialog,
                             ShareDialog shareDialog) {
        super(activity);
        this.reportDialog = reportDialog;
        this.shareDialog = shareDialog;
        this.userStatusUseCase = userStatusUseCase;
        this.addFriendUseCase = addFriendUseCase;
        this.userVisibleUseCase = userVisibleUseCase;
        this.removeFriendUseCase = removeFriendUseCase;
        this.agreeFriendUseCase = agreeFriendUseCase;
        this.useCase = useCase;
        uid = activity.getIntent().getBundleExtra("data").getLong("Uid");
        if (LoginManager.currentLoginUserId != uid) {
            otherVisible.set(View.VISIBLE);
        }

        reportDialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {
                Intent intentReportTAActivity = new Intent(CommonUtils.getContext(), ReportTAActivity.class);
                intentReportTAActivity.putExtra("uid", uid);
                intentReportTAActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intentReportTAActivity);
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    public void afterViews() {
        //设置样式刷新显示的位置
        binding.swipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.app_theme_colorPrimary);
        addFragment(R.id.fl_container, UserInfoTabFragment.instance());
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (!binding.swipeRefreshLayout.isEnabled() && verticalOffset == 0) {
                    binding.swipeRefreshLayout.setEnabled(true);
                } else if (binding.swipeRefreshLayout.isEnabled() && verticalOffset != 0) {
                    binding.swipeRefreshLayout.setEnabled(false);
                }
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
        if (LoginManager.currentLoginUserId != uid) {
            fiends();
        }

        Messenger.getDefault().register(this, MessageKey.SHARE_FRIEND, () -> {
            ChatCmdBusinesssCardBean chatCmdBusinesssCardBean = new ChatCmdBusinesssCardBean();
            chatCmdBusinesssCardBean.avatar = data.get().getAvatar();
//            chatCmdBusinesssCardBean.industry = data.get().getIndustry();
            chatCmdBusinesssCardBean.name = name;
            chatCmdBusinesssCardBean.profession = data.get().getPosition();
//        chatCmdBusinesssCardBean.uid = uid;
            chatCmdBusinesssCardBean.uid = uid + "";
            Intent intentChooseFriendActivtiy = new Intent(CommonUtils.getContext(), MyFriendActivtiy.class);
            intentChooseFriendActivtiy.putExtra("sendFriend", true);
            intentChooseFriendActivtiy.putExtra("ChatCmdBusinesssCardBean", chatCmdBusinesssCardBean);
            intentChooseFriendActivtiy.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            CommonUtils.getContext().startActivity(intentChooseFriendActivtiy);
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginManager.currentLoginUserId != uid) {
            loadConfig();
        } else {
            refresh();
        }
    }

    private void fiends() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        userStatusUseCase.setParams(JsonUtil.mapToJson(map));
        userStatusUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    switch (data.getStatus()) {
                        case 0:
                            LogKit.d("0表示陌生人");
                            friendStatus = 0;
                            removeFriend.set(ContactsManager.ADD_FRIEND);
                            break;
                        case 1:
                            LogKit.d("表示我主动加了他，他还未回复");
                            friendStatus = 1;
                            removeFriend.set(ContactsManager.ADD_FRIEND_APPLICATION);
                            break;
                        case 2:
                            LogKit.d("表示他主动加了我，我还未同意");
                            friendStatus = 2;
                            removeFriend.set(ContactsManager.AFREEN_FRIEND_APPLICATION);
                            break;
                        case 3:
                            LogKit.d("表示是好友关系");
                            friendStatus = 3;
                            removeFriend.set(ContactsManager.IS_FRIEND);
                            break;
                    }
                }, error -> {

                });
    }

    private void loadConfig() {
        userVisibleUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    userVisibleBean = data;
                }, error -> {
                }, () -> {
                    refresh();
                });
    }

    private void refresh() {
        isRefreshing.set(true);
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        map.put("anonymity", "1");
        useCase.setParams(JsonUtil.mapToJson(map));
        useCase.execute().compose(activity.bindToLifecycle())
                .map(d -> d.getUinfo())
                .subscribe(data -> {
                    UserInfoViewModel.this.data.set(data);
                    uri.set(GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + data.getAvatar());

                    relationCount.set(String.format(activity.getString(R.string.app_userinfo_relation), data.getRelationshipscount()));
//                    relationProgress.set(String.format(activity.getString(R.string.app_userinfo_relationprogress), (int) (data.getFansratio() * 100)));


                    taskCount.set(String.format(activity.getString(R.string.app_userinfo_task), (int) data.getAchievetaskcount()));
                    taskProgress.set(String.format(activity.getString(R.string.app_userinfo_taskprogress), (int) (data.getTotoltaskcount())));


                    serviceCount.set(String.format(activity.getString(R.string.app_userinfo_service), DoubleUtil.changeDecimal(data.getUserservicepoint(), 1) + ""));
                    serviceProgress.set(String.format(activity.getString(R.string.app_userinfo_serviceprogress), DoubleUtil.changeDecimal(data.getAverageservicepoint(), 1) + ""));
//                    authVisible.set(data.getIsauth() == 1 ? View.VISIBLE : View.GONE);
//                    level.set(activity.getResources().getStringArray(R.array.user_grades)[data.getExpert() - 1]);

                    int careertype = data.getCareertype();
                    if (careertype == 1) {//固定职业者
                        String company = data.getCompany();//公司
                        String position = data.getPosition();//技术专家
                        String companyAndPosition = company + "-" + position;
                        if (!company.isEmpty()) {
                            this.company.set(companyAndPosition);
                        } else {
                            this.company.set(activity.getString(R.string.app_userinfo_company_none));
                        }
                    } else if (careertype == 2) {//自由职业者
                        this.company.set("自雇者");
                    }
                    int sex = data.getSex();
                    if (sex == 1) {
                        sexIcon.set(CommonUtils.getContext().getResources().getDrawable(R.mipmap.list_man_icon));
                    } else {
                        sexIcon.set(CommonUtils.getContext().getResources().getDrawable(R.mipmap.list_woman_icon));
                    }


                }, error -> {
                    isRefreshing.set(false);
                    startInAnim();
                }, () -> {
                    isRefreshing.set(false);
                    startInAnim();
                });
    }

    private void addFriend() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        addFriendUseCase.setParams(JsonUtil.mapToJson(map));
        addFriendUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    switch (data.getStatus()) {
                        case 1:
                            removeFriend.set(ContactsManager.ADD_FRIEND_APPLICATION);
                            Messenger.getDefault().sendNoMsg(MessageKey.UPDATE_FRIEND_NUM);
                            friendStatus = 1;
                            break;
                        case 0:
                            ToastUtils.shortToast("加好友失败");
                            break;
                    }
                }, error -> {

                });
    }

    private void agreeFriend() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        agreeFriendUseCase.setParams(JsonUtil.mapToJson(map));
        agreeFriendUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    switch (data.getStatus()) {
                        case 1:
                            removeFriend.set(ContactsManager.IS_FRIEND);
                            friendStatus = 3;
                            break;
                        case 0:
                            ToastUtils.shortToast("同意未成功");
                            break;
                    }
                }, error -> {

                });
    }

    private void removeFriend() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid + "");
        removeFriendUseCase.setParams(JsonUtil.mapToJson(map));
        removeFriendUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    switch (data.getStatus()) {
                        case 1:
                            Messenger.getDefault().sendNoMsg(MessageKey.UPDATE_FRIEND_NUM);
                            removeFriend.set(ContactsManager.ADD_FRIEND);
                            friendStatus = 0;
                            break;
                        case 0:
                            ToastUtils.shortToast("删除好友失败");
                            break;
                    }
                }, error -> {

                });
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
        binding.toolbar.startAnimation(animation);
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
        binding.toolbar.startAnimation(animation);
    }

    public void gotoEidt() {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_EDIT_PROFILE);

        Intent intent = new Intent(CommonUtils.getContext(), PersonalEditActivity.class);
        intent.putExtra("myId", data.get().getId());
        activity.startActivityForResult(intent, UserInfoEngine.MY_USER_EDITOR);
    }

    public void doMore() {
        if (!reportDialog.isShowing()) {
            reportDialog.show();
        }
    }
}