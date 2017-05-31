package com.slash.youth.v2.feature.main.mine;


import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.utils.PreferenceUtil;
import com.slash.youth.data.Global;
import com.slash.youth.databinding.FrgMineBinding;
import com.slash.youth.domain.bean.MineInfo;
import com.slash.youth.domain.interactor.main.MineInfoUseCase;
import com.slash.youth.domain.interactor.main.OtherInfoUseCase;
import com.slash.youth.domain.interactor.main.PersonRelationUseCase;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.engine.UserInfoEngine;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.ui.activity.ApprovalActivity;
import com.slash.youth.ui.activity.MyAccountActivity;
import com.slash.youth.ui.activity.MyCollectionActivity;
import com.slash.youth.ui.activity.MyFriendActivtiy;
import com.slash.youth.ui.activity.MyHelpActivity;
import com.slash.youth.ui.activity.UserinfoEditorActivity;
import com.slash.youth.ui.activity.VisitorsActivity;
import com.slash.youth.ui.activity.WebViewActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.Constants;
import com.slash.youth.utils.CountUtils;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.utils.LogKit;
import com.slash.youth.utils.SpUtils;
import com.slash.youth.v2.feature.back.SimpleBackActivity;
import com.slash.youth.v2.feature.back.SimpleBackPage;
import com.slash.youth.v2.feature.dialog.mine.IdentificateDialog;
import com.slash.youth.v2.feature.edit.PersonalEditActivity;
import com.slash.youth.v2.feature.setting.MySettingActivity;
import com.slash.youth.v2.feature.userinfo.UserInfoActivity;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class MineViewModel extends BFViewModel<FrgMineBinding> {

    public static final String START_ANIMATION = "START_ANIMATION";

    public ObservableField<MineInfo.DataBean> data = new ObservableField<>();
    private String[] grades = {"少侠", "大侠", "宗师", "至尊"};//4000  10000 请等待客服审核

    float expertⅠMaxMarks = 1000;
    float expertⅡMaxMarks = 4000;
    float expertⅢMaxMarks = 10000;
    float expertⅣMaxMarks = 99999;
    float expertMarks;
    float expertMarksProgress;//0到360

    boolean isLoadDataFinished = false;
    private RotateAnimation raExpertMarksMaker;

    IdentificateDialog dialog;

    public final ReplyCommand personInfoClick = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_PERSON_MESSAGE);
        Bundle bundle = new Bundle();
        if (null != data && null != data.get()) {
            bundle.putString("phone", data.get().getPhone());
            bundle.putString("skillTag", data.get().getTag());
            bundle.putLong("Uid", LoginManager.currentLoginUserId);
        }
        UserInfoActivity.instance(activity, bundle);
    });

    public final ReplyCommand identificateClick = new ReplyCommand(() -> {

        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_PERSON_MESSAGE);
        Intent intentUserInfoActivity = new Intent(CommonUtils.getContext(), com.slash.youth.ui.activity.UserInfoActivity.class);
//        Bundle bundle = new Bundle();
        if (null != data && null != data.get()) {
            intentUserInfoActivity.putExtra("phone", data.get().getPhone());
            intentUserInfoActivity.putExtra("skillTag", data.get().getTag());
            intentUserInfoActivity.putExtra("Uid", LoginManager.currentLoginUserId);
        }
//        UserInfoActivity.instance(activity, bundle);
        activity.startActivity(intentUserInfoActivity);
//        Intent intentApprovalActivity = new Intent(CommonUtils.getContext(), ApprovalActivity.class);
//        intentApprovalActivity.putExtra("careertype", data.get().getCareertype());
//        intentApprovalActivity.putExtra("Uid", LoginManager.currentLoginUserId);
//        activity.startActivityForResult(intentApprovalActivity, UserInfoEngine.MY_USER_EDITOR);
    });

    public final ReplyCommand approvalClick = new ReplyCommand(() -> {
        //埋点
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_APPROVE);

        switch (data.get().getCareertype()) {
            case 1:
                if (TextUtils.isEmpty(data.get().getCompany()) || TextUtils.isEmpty(data.get().getName()) || TextUtils.isEmpty(data.get().getPosition())) {
                    if (!dialog.isShowing())
                        dialog.show();
                } else {
                    jumpApprovalActivity();
                }
                break;
            case 2:
                if (TextUtils.isEmpty(data.get().getName())) {
                    if (!dialog.isShowing())
                        dialog.show();
                } else {
                    jumpApprovalActivity();
                }
                break;
            case 0:
                if (!dialog.isShowing())
                    dialog.show();
                break;
        }
    });

    private void jumpApprovalActivity() {
        Intent intentApprovalActivity = new Intent(CommonUtils.getContext(), ApprovalActivity.class);
        intentApprovalActivity.putExtra("careertype", data.get().getCareertype());
        intentApprovalActivity.putExtra("Uid", LoginManager.currentLoginUserId);
        activity.startActivityForResult(intentApprovalActivity, Constants.APPROVAL);
    }

    public final ReplyCommand influenceClick = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_INFLUENCE);
        Intent intentCommonQuestionActivity = new Intent(CommonUtils.getContext(), WebViewActivity.class);
        intentCommonQuestionActivity.putExtra("influence", "influence");
        activity.startActivity(intentCommonQuestionActivity);
    });

    public final ReplyCommand accountClick = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_MY_ACCOUNT);
        Intent intentMyAccountActivity = new Intent(CommonUtils.getContext(), MyAccountActivity.class);
        intentMyAccountActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intentMyAccountActivity);
    });

    public final ReplyCommand friendClick = new ReplyCommand(() -> {
        Intent intentChooseFriendActivtiy = new Intent(CommonUtils.getContext(), MyFriendActivtiy.class);
        activity.startActivity(intentChooseFriendActivtiy);
    });

    public final ReplyCommand visitorClick = new ReplyCommand(() -> {
        Intent intentChooseFriendActivtiy = new Intent(CommonUtils.getContext(), VisitorsActivity.class);
        activity.startActivity(intentChooseFriendActivtiy);
    });

    public final ReplyCommand managerClick = new ReplyCommand(() -> {
//        Intent intentMySkillManageActivity = new Intent(CommonUtils.getContext(), MySkillManageActivity.class);
//        intentMySkillManageActivity.putExtra("Title", Constants.MY_TITLE_MANAGER_MY_PUBLISH);
//        intentMySkillManageActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.startActivity(intentMySkillManageActivity);
        SpUtils.setString("myActivityTitle", Constants.MY_TITLE_MANAGER_MY_PUBLISH);
        SimpleBackActivity.instance(activity, SimpleBackPage.MANAGER);
    });

    public final ReplyCommand collectionClick = new ReplyCommand(() -> {
        Intent intentMyCollectionActivity = new Intent(CommonUtils.getContext(), MyCollectionActivity.class);
        intentMyCollectionActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intentMyCollectionActivity);
        //埋点
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_MY_COLLECT);
    });

    public final ReplyCommand helpClick = new ReplyCommand(() -> {
        Intent intentMyHelpActivity = new Intent(CommonUtils.getContext(), MyHelpActivity.class);
        intentMyHelpActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intentMyHelpActivity);
        //帮助的埋点
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_HELP);
    });

    public final ReplyCommand editorClick = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_EDIT_PROFILE);
        Intent intentUserinfoEditorActivity = new Intent(CommonUtils.getContext(), PersonalEditActivity.class);
        intentUserinfoEditorActivity.putExtra("phone", data.get().getPhone());
        intentUserinfoEditorActivity.putExtra("myId", data.get().getId());
        activity.startActivityForResult(intentUserinfoEditorActivity, UserInfoEngine.MY_USER_EDITOR);
    });

    public final ReplyCommand settingClick = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_SET);
        Intent intentMySettingActivity = new Intent(activity, MySettingActivity.class);
        activity.startActivity(intentMySettingActivity);
        //设置的埋点

    });

    MineInfoUseCase mineInfoUseCase;
    OtherInfoUseCase otherInfoUseCase;
    PersonRelationUseCase personRelationUseCase;

    public ObservableField<String> totalsMoney = new ObservableField<>("0.0元");
    public ObservableField<String> uri = new ObservableField<>();
    public ObservableField<String> over = new ObservableField<>();

    public ObservableField<String> grade = new ObservableField<>();
    public ObservableField<String> mark = new ObservableField<>("0");
    public ObservableField<String> connection = new ObservableField<>("0");
    public ObservableField<String> serverStar = new ObservableField<>("0.0星");
    public ObservableField<Integer> contactsVisible = new ObservableField<>(View.GONE);

    @Inject
    public MineViewModel(RxAppCompatActivity activity,
                         MineInfoUseCase mineInfoUseCase,
                         OtherInfoUseCase otherInfoUseCase,
                         PersonRelationUseCase personRelationUseCase,
                         IdentificateDialog dialog) {
        super(activity);
        this.mineInfoUseCase = mineInfoUseCase;
        this.otherInfoUseCase = otherInfoUseCase;
        this.personRelationUseCase = personRelationUseCase;
        this.dialog = dialog;
        dialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {
                Intent intentUserinfoEditorActivity = new Intent(CommonUtils.getContext(), PersonalEditActivity.class);
                intentUserinfoEditorActivity.putExtra("myId", data.get().getId());
                activity.startActivityForResult(intentUserinfoEditorActivity, UserInfoEngine.MY_USER_EDITOR);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void afterViews() {
        binding.svPagerHomeMy.setHightView(binding.rlHead, binding.rlHeadUp, binding.ivMine);
        Messenger.getDefault().register(this, START_ANIMATION, () -> {
            doMarksAnimation();
        });
        Messenger.getDefault().register(this, MessageKey.UPDATE_FRIEND_NUM, () -> {
            loadData();
        });

        Messenger.getDefault().register(this, MessageKey.HIDE_NEW_CONTACTS, Integer.class, data -> {
            if (data == 0)
                contactsVisible.set(View.GONE);
            else
                contactsVisible.set(View.VISIBLE);
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        mineInfoUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(d -> {
                    if (null == d) {
                        return;
                    }
                    MineInfo.DataBean data = d.getMyinfo();

                    LoginManager.currentLoginUserAvatar = data.getAvatar();
                    LoginManager.currentLoginUserName = data.getName();
                    this.data.set(data);

                    totalsMoney.set(CountUtils.DecimalFormat(data.getAmount()) + "元");
                    uri.set(GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + data.getAvatar());

                    int v = (int) (data.getExpertratio() * 100);

                    over.set(v + "%");

                    List<Integer> expertlevels = data.getExpertlevels();//每个等级对应的分数
                    float score = 0;
                    if (expertlevels.size() != 0) {
                        expertMarks = data.getExpertscore();
                        int expertlevel = data.getExpertlevel();//当前对应的等级
                        if (expertlevel > 0 && expertlevel <= 4) {
                            int expertscore = expertlevels.get(expertlevel - 1);
                            if (expertscore < expertMarks) {
                                if (expertscore == expertⅡMaxMarks) {
                                    grade.set("请等待客户审核");
                                    mark.set("");
                                } else if (expertscore == expertⅢMaxMarks) {
                                    grade.set("请等待客户审核");
                                    mark.set("");
                                }
                                score = expertscore;
                            } else {
                                grade.set("距离" + grades[expertlevel] + "还有 ");
                                mark.set(((int) (expertscore - expertMarks)) + "");
                                score = expertMarks;
                            }
                        }
                    }

                    serverStar.set(data.getUserservicepoint() + "星");
                    setExpertMarks(score);
                    initAnimation();
                    isLoadDataFinished = true;
                }, error -> {

                });

        Map<String, String> map = new HashMap<>();
        map.put("uid", PreferenceUtil.readLong(activity, "slash_sp.config", Global.UID, 0l) + "");
        map.put("anonymity", "1");
        otherInfoUseCase.setParams(JsonUtil.mapToJson(map));
        otherInfoUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(d -> {
                    if (null == d) {
                        return;
                    }
                    connection.set(d.getUinfo().getRelationshipscount() + "");
                }, error -> {

                });

        personRelationUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(d -> {
                    if (SpUtils.getInt("addMeFriendCount" + LoginManager.currentLoginUserId, 0) < d.getInfo().getAddMeFriendCount()) {
                        contactsVisible.set(View.VISIBLE);
                    }
                }, error -> {

                });
    }

    private void setExpertMarks(float score) {
//        expertMarks = 2000;//这个数据实际应该从服务端获取
//             expertMarksProgress = expertMarks / totalExpertMarks * 360;
        if (score >= 0 && score <= expertⅠMaxMarks) {
            expertMarksProgress = score / expertⅠMaxMarks * 90;
        } else if (score <= expertⅡMaxMarks) {
            expertMarksProgress = 90 + (score - expertⅠMaxMarks) / (expertⅡMaxMarks - expertⅠMaxMarks) * 90;
        } else if (score <= expertⅢMaxMarks) {
            expertMarksProgress = 180 + (score - expertⅡMaxMarks) / (expertⅢMaxMarks - expertⅡMaxMarks) * 90;
        } else if (score <= expertⅣMaxMarks) {
            expertMarksProgress = 270 + (score - expertⅢMaxMarks) / (expertⅣMaxMarks - expertⅢMaxMarks) * 90;
        }
    }


    private void initAnimation() {
        raExpertMarksMaker = new RotateAnimation(0, expertMarksProgress, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        raExpertMarksMaker.setDuration(300);
        raExpertMarksMaker.setInterpolator(new LinearInterpolator());
        raExpertMarksMaker.setFillAfter(true);
    }

    private void initScoreView() {
        binding.flHomeMyExpertMarksMaker.startAnimation(raExpertMarksMaker);
        binding.rsvHomeMyExpertMarksProgress.setStartProgressAngle(0);
        binding.rsvHomeMyExpertMarksProgress.setTotalProgressAngle(expertMarksProgress);
        binding.rsvHomeMyExpertMarksProgress.post(new Runnable() {
            @Override
            public void run() {
                binding.rsvHomeMyExpertMarksProgress.initRingProgressDraw();
            }
        });
    }

    public void doMarksAnimation() {
        if (isLoadDataFinished) {
            initScoreView();
            initExpertMarksProgress();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        LogKit.v("HomeMyPager waiting load data...");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (isLoadDataFinished) {
                            CommonUtils.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    initScoreView();
                                    initExpertMarksProgress();
                                }
                            });
                            return;
                        }
                    }
                }
            }).start();
        }
    }

    private void initExpertMarksProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 120; i++) {
                    try {
                        long startMill = System.currentTimeMillis();
                        final float displayMarks = expertMarks * (i + 1) / 120;
                        CommonUtils.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                binding.tvHomeMyExpertMarks.setText((int) displayMarks + "");
                            }
                        });
                        long endMill = System.currentTimeMillis();
                        if (16 - (endMill - startMill) > 0) {
                            Thread.sleep(16 - (endMill - startMill));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
