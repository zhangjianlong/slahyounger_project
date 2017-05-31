package com.slash.youth.v2.feature.setting;


import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.weight.progress.Progress;
import com.slash.youth.R;
import com.slash.youth.databinding.ActMysettingBinding;
import com.slash.youth.databinding.ActivityMySettingBinding;
import com.slash.youth.domain.CommonResultBean;
import com.slash.youth.domain.GetBindBean;
import com.slash.youth.domain.LoginBindBean;
import com.slash.youth.domain.RecodeBean;
import com.slash.youth.domain.SetBean;
import com.slash.youth.domain.SetMsgBean;
import com.slash.youth.domain.SetTimeBean;
import com.slash.youth.domain.interactor.main.CheckTimeUseCase;
import com.slash.youth.domain.interactor.main.SetTimeUseCase;
import com.slash.youth.domain.interactor.main.UserCompanyVisibleUseCase;
import com.slash.youth.domain.interactor.main.UserEvaluateCountUseCase;
import com.slash.youth.domain.interactor.main.UserEvaluateUseCase;
import com.slash.youth.domain.interactor.main.UserEvalutionVisibleUseCase;
import com.slash.youth.domain.interactor.main.UserServiceVisibleUseCase;
import com.slash.youth.domain.interactor.main.UserVisibleUseCase;
import com.slash.youth.engine.AccountManager;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.engine.MyManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.global.SlashApplication;
import com.slash.youth.http.protocol.BaseProtocol;
import com.slash.youth.http.protocol.GetBindProtocol;
import com.slash.youth.http.protocol.LoginBindProtocol;
import com.slash.youth.http.protocol.LoginUnBindProtocol;
import com.slash.youth.http.protocol.MySettingProtocol;
import com.slash.youth.http.protocol.SetMsgProtocol;
import com.slash.youth.http.protocol.SetTimeProtocol;
import com.slash.youth.ui.activity.FindPassWordActivity;
import com.slash.youth.ui.activity.RevisePasswordActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.Constants;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.utils.DistanceUtils;
import com.slash.youth.utils.LogKit;
import com.slash.youth.utils.StringUtils;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.feature.dialog.binding.BindingDialog;
import com.slash.youth.v2.feature.dialog.common.CommonDialog;
import com.slash.youth.v2.feature.login.LoginActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.slash.youth.ui.viewmodel.MySettingModel.SY_RES_BINDED_THIRDPART;
import static com.slash.youth.ui.viewmodel.MySettingModel.SY_RES_FAIL;
import static com.slash.youth.ui.viewmodel.MySettingModel.SY_RES_SUCCESS;

@PerActivity
public class MySettingViewModel extends BAViewModel<ActMysettingBinding> {
    private int platformType;
    private Progress progress;
    private int type;
    private int status;
    private CommonDialog commonDialog;
    private static final int SET_PSD = 1;
    private static final int CHANGE_PSD = 0;
    private boolean isWinxinBing = false;
    private boolean isQQBing = false;
    private BindingDialog bindingDialog;
    private UserVisibleUseCase userVisibleUseCase;
    private UserCompanyVisibleUseCase companyVisibleUseCase;
    private UserServiceVisibleUseCase serviceVisibleUseCase;
    private UserEvalutionVisibleUseCase evalutionVisibleUseCase;
    private CheckTimeUseCase checkTimeUseCase;
    private SetTimeUseCase setTimeUseCase;

    public final ObservableField<String> setPsd = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_setting_find_psd));
    public final ObservableField<Integer> changePsdShow = new ObservableField<>(View.GONE);
    public final ObservableField<Boolean> timeCheck = new ObservableField<>(false);
    public final ObservableField<String> weixinBinding = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_setting_bind));
    public final ObservableField<String> qqBinding = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_setting_bind));
    public final ObservableField<String> title = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_setting_title));
    public final ObservableField<Boolean> evaluate = new ObservableField<>(false);
    public final ObservableField<Boolean> showCompany = new ObservableField<>(false);
    public final ObservableField<Boolean> service = new ObservableField<>(false);
    public CompoundButton.OnCheckedChangeListener setTimeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int timeStatus = 0;
            String startTime = "22:00";
            String endTime = "08:00";
            if (isChecked) {
                timeStatus = 1;
            } else {
                timeStatus = 0;
            }
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("status", timeStatus + "");
            paramsMap.put("starttime", startTime);
            paramsMap.put("endtime", endTime);
            setTimeUseCase.setParams(JsonUtil.mapToJson(paramsMap));
            setTimeUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {

            }, error -> {

            });


        }
    };
    public CompoundButton.OnCheckedChangeListener setCompanyListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setCompany(isChecked);
        }
    };

    public CompoundButton.OnCheckedChangeListener setServiceListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setService(isChecked);
        }
    };

    public CompoundButton.OnCheckedChangeListener setEvaluateListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setEvalution(isChecked);
        }
    };


    @Inject
    public MySettingViewModel(RxAppCompatActivity activity, CommonDialog commonDialog, BindingDialog bindingDialog, UserVisibleUseCase userVisibleUseCase, UserEvalutionVisibleUseCase evalutionVisibleUseCase
            , UserCompanyVisibleUseCase companyVisibleUseCase, UserServiceVisibleUseCase serviceVisibleUseCase, CheckTimeUseCase checkTimeUseCase, SetTimeUseCase setTimeUseCase) {
        super(activity);
        this.commonDialog = commonDialog;
        this.bindingDialog = bindingDialog;
        this.userVisibleUseCase = userVisibleUseCase;
        this.serviceVisibleUseCase = serviceVisibleUseCase;
        this.evalutionVisibleUseCase = evalutionVisibleUseCase;
        this.companyVisibleUseCase = companyVisibleUseCase;
        this.checkTimeUseCase = checkTimeUseCase;
        this.setTimeUseCase = setTimeUseCase;
        progress = new Progress(activity);

    }

    public final ReplyCommand revisePassWord = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_SET_MODIFICATE_TRADE_PASSWORD);
        checkPsdStatus(CHANGE_PSD);
    });

    public final ReplyCommand setPassWord = new ReplyCommand(() -> {
        switch (type) {
            case 1:
                //找回交易密码
                MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_SET_FIND_TRADE_PASSWORD);
                checkPsdStatus(SET_PSD);
                break;
            case 2:
                MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_SET_SET_TRADE_PASSWORD);
                jumpIntoActivity(type);//设置交易密码
                break;
            case 3:
                //弹窗
                MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_SET_SET_TRADE_PASSWORD);
                showCommonDialog(CommonUtils.getContext().getString(R.string.app_setting_psd_remind));
                break;
        }
    });

    private void setCompany(boolean isChecked) {
        int status = 0;
        if (isChecked) {
            status = 1;
        }
        Map<String, String> map = new HashMap<>();
        map.put("status", status + "");
        companyVisibleUseCase.setParams(JsonUtil.mapToJson(map));
        companyVisibleUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
        }, error -> {

        });
    }

    private void setEvalution(boolean isChecked) {
        int status = 0;
        if (isChecked) {
            status = 1;
        }
        Map<String, String> map = new HashMap<>();
        map.put("status", status + "");
        evalutionVisibleUseCase.setParams(JsonUtil.mapToJson(map));
        evalutionVisibleUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
        }, error -> {

        });
    }

    private void setService(boolean isChecked) {
        int status = 0;
        if (isChecked) {
            status = 1;
        }
        Map<String, String> map = new HashMap<>();
        map.put("status", status + "");
        serviceVisibleUseCase.setParams(JsonUtil.mapToJson(map));
        serviceVisibleUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
        }, error -> {

        });
    }


    private void showCommonDialog(String content) {
        commonDialog.initValue(content);
        commonDialog.hintCalBtn();
        commonDialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {

            }

            @Override
            public void onCancel() {

            }
        });
        if (!commonDialog.isShowing()) {
            commonDialog.show();
        }

    }

    //解绑第三方账号
    public void loginUnBind(Byte loginPlatform) {
        LoginUnBindProtocol loginUnBindProtocol = new LoginUnBindProtocol(loginPlatform);
        loginUnBindProtocol.getDataFromServer(new BaseProtocol.IResultExecutor<LoginBindBean>() {
            @Override
            public void execute(LoginBindBean dataBean) {
                dismissProess();
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                }
                int rescode = dataBean.getRescode();
                if (rescode == 0) {
                    switch (platformType) {
                        case GlobalConstants.LoginPlatformType.WECHAT://微信
                            weixinBinding.set(CommonUtils.getContext().getString(R.string.app_setting_bind));
                            isWinxinBing = false;
                            break;
                        case GlobalConstants.LoginPlatformType.QQ://qq
                            qqBinding.set(CommonUtils.getContext().getString(R.string.app_setting_bind));
                            isQQBing = false;
                            break;
                    }
                } else {
                    ToastUtils.shortCenterToast("解绑失败");
                }
            }

            @Override
            public void executeResultError(String result) {
                LogKit.d("result:" + result);
            }
        });
    }

    private void showProess() {
        if (progress != null && !progress.isShowing()) {
            progress.show();
        }
    }

    private void dismissProess() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    //绑定第三方账号
    public void loginBind(String token, String uid, Byte loginPlatform, String wechatOpenid) {
        final LoginBindProtocol loginBindProtocol = new LoginBindProtocol(token, uid, loginPlatform, wechatOpenid);
        loginBindProtocol.getDataFromServer(new BaseProtocol.IResultExecutor<LoginBindBean>() {
            @Override
            public void execute(LoginBindBean dataBean) {
                dismissProess();
                int rescode = dataBean.getRescode();
                switch (platformType) {
                    case GlobalConstants.LoginPlatformType.WECHAT://微信
                        if (rescode == SY_RES_SUCCESS) {
                            isWinxinBing = true;
                        }
                        break;
                    case GlobalConstants.LoginPlatformType.QQ://qq
                        if (rescode == SY_RES_SUCCESS) {
                            isQQBing = true;
                        }
                        break;
                }
                switch (rescode) {
                    case SY_RES_SUCCESS:
                        initView(isWinxinBing, isQQBing);
                        break;
                    case SY_RES_BINDED_THIRDPART:
                        ToastUtils.shortCenterToast(String.format("该三方账号已绑定%S，请解绑后再绑定新账号", StringUtils.phoneFormat(dataBean.getData().getPhone())));
                        break;
                    case SY_RES_FAIL:
                    default:
                        ToastUtils.shortCenterToast("绑定失败");
                        break;
                }
            }

            @Override
            public void executeResultError(String result) {
                ToastUtils.shortCenterToast("绑定失败");
            }
        });
    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            showProess();
            UMShareAPI mShareAPI = UMShareAPI.get(activity);
            switch (platform) {
                case QQ:
                    String QQ_access_token = data.get("access_token");
                    isQQBing = true;
                    String QQ_uid = data.get("openid");
                    loginBind(QQ_access_token, QQ_uid, GlobalConstants.LoginPlatformType.QQ, null);
                    break;
                case WEIXIN:
                    String WEIXIN_access_token = data.get("access_token");
                    String unionid = data.get("unionid");
                    String openid = data.get("openid");
                    loginBind(WEIXIN_access_token, unionid, GlobalConstants.LoginPlatformType.WECHAT, openid);
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.shortToast("绑定失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.shortToast("绑定失败");
        }
    };


    public final ReplyCommand qq = new ReplyCommand(() -> {
        platformType = GlobalConstants.LoginPlatformType.QQ;
        if (isQQBing) {
            MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_THIRD_PARTY_ACCOUNT_QQ_UNBINDING);
            showBindingDialog();
        } else {
            MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_THIRD_PARTY_ACCOUNT_QQ_BINDING);
            UMShareAPI mShareAPI = UMShareAPI.get(activity);
            if (mShareAPI.isInstall(activity, SHARE_MEDIA.QQ)) {
                mShareAPI.doOauthVerify(activity, SHARE_MEDIA.QQ, umAuthListener);
            } else {
                ToastUtils.shortToast("请先安装QQ客户端");
            }
        }

    });

    public final ReplyCommand weixin = new ReplyCommand(() -> {
        platformType = GlobalConstants.LoginPlatformType.WECHAT;
        if (isWinxinBing) {
            MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_THIRD_PARTY_ACCOUNT_WECHAT_UNBINDING);
            showBindingDialog();
        } else {
            MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_THIRD_PARTY_ACCOUNT_WECHAT_BINDING);
            UMShareAPI mShareAPI = UMShareAPI.get(activity);
            mShareAPI.doOauthVerify(activity, SHARE_MEDIA.WEIXIN, umAuthListener);
        }

    });

    public final ReplyCommand finishApp = new ReplyCommand(() -> {
        commonDialog.initValue(CommonUtils.getContext().getString(R.string.app_setting_logout));
        commonDialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {
                showProess();
                logout(LoginManager.token);

            }

            @Override
            public void onCancel() {

            }
        });
        if (commonDialog != null && !commonDialog.isShowing()) {
            commonDialog.show();
        }
    }
    );

    private void showBindingDialog() {
        bindingDialog.setOnDialogLisetener(new com.core.op.lib.base.OnDialogLisetener() {
            @Override
            public void onConfirm() {
                showProess();
                switch (platformType) {
                    case GlobalConstants.LoginPlatformType.WECHAT:
                        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_THIRD_PARTY_ACCOUNT_WECHAT_UNBINDING_CONFIRM_UNBUNDING);
                        loginUnBind(GlobalConstants.LoginPlatformType.WECHAT);
                        break;
                    case GlobalConstants.LoginPlatformType.QQ:
                        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_THIRD_PARTY_ACCOUNT_QQ_UNBINDING_CONFIRM_UNBUNDING);
                        loginUnBind(GlobalConstants.LoginPlatformType.QQ);
                        break;
                }
            }

            @Override
            public void onCancel() {

            }


        });

        if (!bindingDialog.isShowing()) {
            bindingDialog.show();
        }
    }


    @Override
    public void afterViews() {
        initData();
    }

    private void initData() {
        userVisibleUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            if (data.getData() == null) {
                return;
            }
            if (data.getData().getCompany() == 1) {
                showCompany.set(true);
            }
            if (data.getData().getEvalution() == 1) {
                evaluate.set(true);
            }
            if (data.getData().getServicepower() == 1) {
                service.set(true);
            }
        }, e -> {
            System.out.println(e + "user error");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        testPassWord();
        //第一次网络获取状态，展示时间
        getTimeState();
        getMsgState();
        bindPlatform(LoginManager.token);
    }

    //绑定平台
    public void bindPlatform(String token) {
        GetBindProtocol getBindProtocol = new GetBindProtocol(token);
        getBindProtocol.getDataFromServer(new BaseProtocol.IResultExecutor<GetBindBean>() {
            @Override
            public void execute(GetBindBean dataBean) {
                int rescode = dataBean.getRescode();
                if (rescode == 0) {
                    GetBindBean.DataBean data = dataBean.getData();
                    List<String> platforms = data.getPlatforms();
                    for (String platform : platforms) {
                        if (platform.equals("1")) {
                            isWinxinBing = true;
                        } else if (platform.equals("2")) {
                            isQQBing = true;
                        }
                    }
                    initView(isWinxinBing, isQQBing);
                }
            }

            @Override
            public void executeResultError(String result) {
                LogKit.d("result:" + result);
            }
        });
    }

    private void initView(boolean isWinxinBing, boolean isQQBing) {
        if (isWinxinBing) {
            weixinBinding.set(CommonUtils.getContext().getString(R.string.app_setting_unbind));
        } else {
            weixinBinding.set(CommonUtils.getContext().getString(R.string.app_setting_bind));
        }

        if (isQQBing) {
            qqBinding.set(CommonUtils.getContext().getString(R.string.app_setting_unbind));
        } else {
            qqBinding.set(CommonUtils.getContext().getString(R.string.app_setting_bind));
        }

    }

    private void jumpIntoActivity(int type) {
        Intent intentFindPassWordActivity = new Intent(activity, FindPassWordActivity.class);
        intentFindPassWordActivity.putExtra("type", type);
        activity.startActivityForResult(intentFindPassWordActivity, Constants.MYSETTING_SETPASSWORD);
    }

    //验证找回密码的状态  0为找回密码  1为设置密码
    private void checkPsdStatus(int prama) {
        MyManager.testFindPassWord(new BaseProtocol.IResultExecutor<CommonResultBean>() {
            @Override
            public void execute(CommonResultBean dataBean) {
                int rescode = dataBean.rescode;
                if (rescode == 0) {
                    CommonResultBean.Data data = dataBean.data;
                    int status = data.status;
                    switch (status) {
                        case 1://1有审核中的交易密码
                            showCommonDialog(CommonUtils.getContext().getString(R.string.app_setting_psd_find));
                            break;
                        case 2://2没有审核中的交易密码
                            switch (prama) {
                                case CHANGE_PSD:
                                    Intent intentRevisePasswordActivity = new Intent(activity, RevisePasswordActivity.class);
                                    activity.startActivityForResult(intentRevisePasswordActivity, 2);
                                    break;
                                case SET_PSD:
                                    jumpIntoActivity(type);
                                    break;
                            }

                            break;
                    }
                }
            }

            @Override
            public void executeResultError(String result) {
                LogKit.d("result:" + result);
            }
        });
    }


    //判断是否有交易密码
    private void testPassWord() {
        AccountManager.getTradePasswordStatus(new BaseProtocol.IResultExecutor<CommonResultBean>() {
            @Override
            public void execute(CommonResultBean dataBean) {
                int rescode = dataBean.rescode;
                if (rescode == 0) {
                    int status = dataBean.data.status;
                    switch (status) {
                        case 1://1表示当前有交易密码
                            changePsdShow.set(View.VISIBLE);
                            setPsd.set(CommonUtils.getContext().getString(R.string.app_setting_find_psd));
                            type = 1;
                            break;
                        case 2:// 2表示当前没有交易密码
                            changePsdShow.set(View.GONE);
                            setPsd.set(CommonUtils.getContext().getString(R.string.app_setting_set_psd));
                            type = 2;
                            break;
                        case 3://3有密码处于审核中
                            changePsdShow.set(View.GONE);
                            setPsd.set(CommonUtils.getContext().getString(R.string.app_setting_set_psd));
                            type = 3;
                            break;
                    }
                }
            }

            @Override
            public void executeResultError(String result) {
                LogKit.d("result：" + result);
            }
        });
    }

    private void getTimeState() {
        checkTimeUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            switch (data.getData().getDnd()) {
                case 0:
                    timeCheck.set(false);
                    break;
                case 1:
                    timeCheck.set(true);
                    break;
            }
        });

    }

    private void getMsgState() {
        SetMsgProtocol setMsgProtocol = new SetMsgProtocol();
        setMsgProtocol.getDataFromServer(new BaseProtocol.IResultExecutor<SetMsgBean>() {
            @Override
            public void execute(SetMsgBean dataBean) {
                int rescode = dataBean.getRescode();
                if (rescode == 0) {
                    SetMsgBean.DataBean data = dataBean.getData();
                    SetMsgBean.DataBean.DataBean1 msgData = data.getData();
                    int msgDnd = msgData.getDnd();//1表示已经设置 0表示未设置

                }
            }

            @Override
            public void executeResultError(String result) {
                LogKit.d("result:" + result);
            }
        });
    }

    //设置数据
    private int setData(String url, HashMap<String, String> paramsMap) {
        MySettingProtocol mySettingProtocol = new MySettingProtocol(url, paramsMap);
        mySettingProtocol.getDataFromServer(new BaseProtocol.IResultExecutor<SetBean>() {
            @Override
            public void execute(SetBean dataBean) {
                int rescode = dataBean.rescode;
                SetBean.DataBean data = dataBean.getData();
                //获取状态
                status = data.getStatus();
                if (status == 1) {
                } else {
                }
            }

            @Override
            public void executeResultError(String result) {
                LogKit.d("result:" + result);
            }
        });
        return status;
    }

    //登录退出的接口
    private void logout(String token) {
        LoginManager.logout(new onLogout(), token);
    }

    public class onLogout implements BaseProtocol.IResultExecutor<RecodeBean> {
        @Override
        public void execute(RecodeBean dataBean) {
            dismissProess();
            int rescode = dataBean.getRescode();
            switch (rescode) {
                case 0:
                    SlashApplication application = (SlashApplication) CommonUtils.getApplication();
                    ArrayList<Activity> listActivities = application.listActivities;
                    for (Activity activity : listActivities) {
                        if (activity != null) {
                            activity.finish();
                            activity = null;
                            listActivities.remove(activity);
                        } else {
                            listActivities.remove(activity);
                        }
                    }
                    Intent intentLoginActivity = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intentLoginActivity);
                    break;
                case 1:
                    LogKit.d("退出失败");
                    ToastUtils.shortToast("退出失败");
                    break;
            }
        }

        @Override
        public void executeResultError(String result) {
            LogKit.d("result:" + result);
        }
    }

}