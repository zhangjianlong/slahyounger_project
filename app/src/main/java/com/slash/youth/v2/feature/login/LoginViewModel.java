package com.slash.youth.v2.feature.login;


import android.Manifest;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.utils.PreferenceUtil;
import com.core.op.lib.utils.RxCountDown;
import com.core.op.lib.weight.progress.Progress;
import com.slash.youth.R;
import com.slash.youth.databinding.ActLoginBinding;
import com.slash.youth.domain.CustomerServiceBean;
import com.slash.youth.domain.RongTokenBean;
import com.slash.youth.domain.ThirdPartyLoginResultBean;
import com.slash.youth.domain.UserInfoBean;
import com.slash.youth.domain.bean.PhoneLoginResultBean;
import com.slash.youth.domain.interactor.login.LoginResultUseCase;
import com.slash.youth.domain.interactor.login.PhoneLoginUseCase;
import com.slash.youth.domain.interactor.login.VerifyUseCase;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.engine.UserInfoEngine;
import com.slash.youth.http.protocol.BaseProtocol;
import com.slash.youth.ui.activity.*;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.utils.LogKit;
import com.slash.youth.utils.LoginCheckUtil;
import com.slash.youth.utils.SpUtils;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.feature.bindingaccount.BindingActivity;
import com.slash.youth.v2.feature.main.MainActivity;
import com.slash.youth.v2.feature.protocol.ProtocolActivity;
import com.slash.youth.v2.util.ShareKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import io.rong.imlib.RongIMClient;
import rx.Subscriber;


@PerActivity
public class LoginViewModel extends BAViewModel<ActLoginBinding> {
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> verifyNum = new ObservableField<>();
    public final ObservableField<String> sendVerifyText = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_login_verify));
    public final ObservableField<Boolean> agreeAgreement = new ObservableField<>(true);
    public final ObservableField<Boolean> sendVerifyEnable = new ObservableField<>(true);
    private String tempPhoneNum;
    private String tempVerifyNum;
    private Progress progress;
    VerifyUseCase verifyUseCase;
    PhoneLoginUseCase phoneLoginCase;
    private String QQ_access_token;//17301782584  18915521461
    private String QQ_uid;//这个值应该取openid,而不是uid,为了避免大幅度改代码，不改变变量名
    private String WEIXIN_access_token;
    private String WEIXIN_unionid;//这个值应该取openid,而不是unionid,为了避免大幅度改代码，不改变变量名
    private String WEIXIN_openid;//微信为了和WEB兼容，增加openid参数
    private String QQ_nickname;
    private String QQ_gender;
    private String QQ_avatar;
    private String QQ_province;
    private String QQ_city;
    private String WEIXIN_nickname;
    private String WEIXIN_gender;
    private String WEIXIN_avatar;
    private String WEIXIN_province;
    private String WEIXIN_city;
    private int thirdLoginPlatformType = -1;//WECHAT = 1    QQ = 2    WEIBO = 3
    private final int QQTYPE = 2;
    private final int WECHATTYPE = 1;


    public final ReplyCommand login = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.REGISTER_CLICK_ENTER);
        tempVerifyNum = verifyNum.get();
        tempPhoneNum = phoneNum.get();
        if (!LoginCheckUtil.checkLogin(tempPhoneNum, tempVerifyNum, agreeAgreement.get())) {
            return;
        }
        tempVerifyNum = tempVerifyNum.trim();
        tempPhoneNum = tempPhoneNum.trim();
        progress = Progress.create(activity).setStyle(Progress.Style.SPIN_INDETERMINATE);
        progress.show();
        Map<String, String> map = new HashMap<>();
        map.put("phone", tempPhoneNum);
        map.put("pin", tempVerifyNum);
        map.put("3pToken", "");
        map.put("userInfo", "");
        phoneLoginCase.setParams(JsonUtil.mapToJson(map));
        phoneLoginCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            if (data != null && data.getData() == null) {
                ToastUtils.shortToast(CommonUtils.getContext().getString(R.string.app_login_fail));
                return;
            }
            PreferenceUtil.write(CommonUtils.getContext(), ShareKey.USER_PHONE, tempPhoneNum);
            LoginManager.currentLoginUserPhone = tempPhoneNum;
            int rescode = data.getRescode();
            PhoneLoginResultBean.Data loginRes = data.getData();
            String rongToken = loginRes.getRongToken();//融云token
            String token = loginRes.getToken();
            long uid = loginRes.getUid();
            switch (rescode) {
                case 0:
                    //登陆成功，老用户
                case 11:
                    //登陆成功，新用户
                    savaLoginState(uid, token, rongToken);
                    //链接融云
                    MsgManager.connectRongCloud(rongToken);
                    Intent intentHomeActivity2 = new Intent(activity, MainActivity.class);
                    activity.startActivity(intentHomeActivity2);
                    activity.finish();
                    break;
                case 7:
                    ToastUtils.shortToast(CommonUtils.getContext().getString(R.string.app_login_verify_code_error));
                    break;
                default:
                    ToastUtils.shortToast(CommonUtils.getContext().getString(R.string.app_login_fail));
                    break;
            }
        }, error -> {
            progress.dismiss();
        }, () -> {
            progress.dismiss();
        });
    });


    public final ReplyCommand sendVerify = new ReplyCommand(() -> {
        tempPhoneNum = phoneNum.get();
        if (!LoginCheckUtil.checkPhoeNumFormat(tempPhoneNum)) {
            return;
        }
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.REGISTER_CLICK_VERTIFYCODE);
        RxCountDown.countdown(60).compose(activity.bindToLifecycle()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                sendVerifyEnable.set(true);
                sendVerifyText.set(CommonUtils.getContext().getString(R.string.app_login_verify));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                sendVerifyEnable.set(false);
                sendVerifyText.set(integer + "S");
            }
        });

        tempPhoneNum = tempPhoneNum.trim();
        Map<String, String> map = new HashMap<>();
        map.put("phone", tempPhoneNum);
        verifyUseCase.setParams(JsonUtil.mapToJson(map));
        verifyUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    switch (data.getRescode()) {
                        case 0:
                            AppToast.show(CommonUtils.getContext(), R.string.app_login_verify_code_suc);
                            break;
                        default:
                            AppToast.show(CommonUtils.getContext(), R.string.app_login_verify_code_fail);
                    }
                });
    });


    @Inject
    public LoginViewModel(RxAppCompatActivity activity,
                          VerifyUseCase verifyUseCase, PhoneLoginUseCase phoneLoginCase) {
        super(activity);
        this.verifyUseCase = verifyUseCase;
        this.phoneLoginCase = phoneLoginCase;
    }


    @Override
    public void afterViews() {

    }


    private void connectToRongCloud(String tmpRongToken) {
        RongIMClient.connect(tmpRongToken, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
            }

            @Override
            public void onSuccess(String s) {
                gotoChatSlashHelperActivity();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void gotoChatSlashHelperActivity() {
        final Intent intentChatActivity = new Intent(activity, ChatActivity.class);
//        intentChatActivity.putExtra("targetId", "1000");
        long customerServiceUid = MsgManager.getCustomerServiceUidFromSp();
        if (customerServiceUid > 0) {
            //sp中保存有随机客服的uid
            MsgManager.customerServiceUid = customerServiceUid + "";
            intentChatActivity.putExtra("targetId", customerServiceUid + "");
            activity.startActivity(intentChatActivity);
        } else {
            MsgManager.getCustomerService(new BaseProtocol.IResultExecutor<CustomerServiceBean>() {
                @Override
                public void execute(CustomerServiceBean dataBean) {
                    long customerServiceUid = dataBean.data.uid;
                    MsgManager.customerServiceUid = customerServiceUid + "";
                    intentChatActivity.putExtra("targetId", customerServiceUid + "");
                    activity.startActivity(intentChatActivity);
                }

                @Override
                public void executeResultError(String result) {
                    ToastUtils.shortToast("获取客服UID失败:" + result);
                }
            });
        }
    }


    private void savaLoginState(long uid, String token, String rongToken) {
        LoginManager.currentLoginUserId = uid;
        LoginManager.token = token;
        LoginManager.rongToken = rongToken;
        SpUtils.setLong("uid", uid);
        SpUtils.setString("token", token);
        SpUtils.setString("rongToken", rongToken);
    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            UMShareAPI mShareAPI = UMShareAPI.get(activity);
            switch (platform) {
                case QQ:
                    QQ_access_token = data.get("access_token");
                    QQ_uid = data.get("openid");//通过日志发现， 这里的uid和openid是一样的
                    SpUtils.setString("QQ_token", QQ_access_token);
                    SpUtils.setString("QQ_uid", QQ_uid);
                    if (TextUtils.isEmpty(QQ_access_token) || TextUtils.isEmpty(QQ_uid)) {
                        ToastUtils.shortToast("QQ登录失败");
                        return;
                    }
                    mShareAPI.getPlatformInfo(activity, SHARE_MEDIA.QQ, umAuthListenerForUserInfo);
                    break;
                case WEIXIN:
                    WEIXIN_access_token = data.get("access_token");
                    WEIXIN_unionid = data.get("unionid");
                    WEIXIN_openid = data.get("openid");//微信为了和WEB兼容，增加openid参数
                    SpUtils.setString("WEIXIN_token", WEIXIN_access_token);
                    SpUtils.setString("WEIXIN_uid", WEIXIN_unionid);
                    SpUtils.setString("WEIXIN_openid", WEIXIN_openid);
                    LogKit.v("WEIXIN_access_token:" + WEIXIN_access_token + " WEIXIN_unionid:" + WEIXIN_unionid + " WEIXIN_openid:" + WEIXIN_openid);
                    if (TextUtils.isEmpty(WEIXIN_access_token) || TextUtils.isEmpty(WEIXIN_unionid)) {
                        ToastUtils.shortToast("微信登录失败");
                        return;
                    }
                    mShareAPI.getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, umAuthListenerForUserInfo);
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.shortToast("登录失败！");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.shortToast("登录失败！");
        }
    };

    private UMAuthListener umAuthListenerForUserInfo = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            switch (platform) {
                case QQ:
                    QQ_nickname = data.get("screen_name");
                    String gender = data.get("gender");
                    if (gender.equals("男")) {
                        QQ_gender = "1";
                    } else {
                        QQ_gender = "2";
                    }
                    QQ_avatar = data.get("profile_image_url");
                    QQ_province = data.get("province");
                    QQ_city = data.get("city");
                    thirdLoginPlatformType = QQTYPE;
                    LoginManager.serverThirdPartyLogin(onThirdPartyLoginFinished, QQ_access_token, QQ_uid, thirdLoginPlatformType + "", null);
                    break;
                case WEIXIN:
                    WEIXIN_nickname = data.get("screen_name");
                    WEIXIN_gender = data.get("gender");
                    WEIXIN_avatar = data.get("profile_image_url");
                    WEIXIN_province = data.get("province");
                    WEIXIN_city = data.get("city");
                    thirdLoginPlatformType = WECHATTYPE;
                    LoginManager.serverThirdPartyLogin(onThirdPartyLoginFinished, WEIXIN_access_token, WEIXIN_unionid, thirdLoginPlatformType + "", WEIXIN_openid);
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };

    public BaseProtocol.IResultExecutor onThirdPartyLoginFinished = new BaseProtocol.IResultExecutor<ThirdPartyLoginResultBean>() {
        @Override
        public void execute(ThirdPartyLoginResultBean dataBean) {
            switch (dataBean.rescode) {
                case 9:
                    String _3ptoken = dataBean.data.token;
                    if (_3ptoken.split("&").length <= 3) {
                        _3ptoken = _3ptoken + "&" + WEIXIN_openid;
                    }
                    Bundle thirdPlatformBundle = new Bundle();
                    thirdPlatformBundle.putString("_3ptoken", _3ptoken);
                    String userInfo = "";
                    if (thirdLoginPlatformType == 1) {
                        //微信登录
                        userInfo = WEIXIN_nickname + "&" + WEIXIN_gender + "&" + WEIXIN_avatar + "&" + WEIXIN_province + "&" + WEIXIN_city;
                    } else if (thirdLoginPlatformType == 2) {
                        //QQ登录
                        userInfo = QQ_nickname + "&" + QQ_gender + "&" + QQ_avatar + "&" + QQ_province + "&" + QQ_city;
                    } else {
                        ToastUtils.shortToast("第三方平台 type 编号错误");
                    }
                    thirdPlatformBundle.putString("userInfo", userInfo);
                    thirdPlatformBundle.putString("3ptoken", _3ptoken);
                    thirdPlatformBundle.putInt("platformType", thirdLoginPlatformType);
                    Intent bindingIntent = new Intent(activity, BindingActivity.class);
                    bindingIntent.putExtras(thirdPlatformBundle);
                    activity.startActivity(bindingIntent);
                    break;
                case 0:
                    LogKit.v("已经登录过的用户");
                    String token = dataBean.data.token;
                    long uid = dataBean.data.uid;
                    LoginManager.token = token;
                    LoginManager.currentLoginUserId = uid;
                    SpUtils.setString("token", token);
                    SpUtils.setLong("uid", uid);
                    //获取融云token，并连接融云
                    MsgManager.getRongToken(new BaseProtocol.IResultExecutor<RongTokenBean>() {
                        @Override
                        public void execute(RongTokenBean dataBean) {
                            String rongToken = dataBean.data.token;
                            SpUtils.setString("rongToken", rongToken);
                            //这里可能需要先断开和融云的链接，然后重新再链接
                            MsgManager.connectRongCloud(rongToken);
                        }

                        @Override
                        public void executeResultError(String result) {
                            ToastUtils.shortToast("第三方登录，获取融云token失败:" + result);
                        }
                    }, uid + "", "111");//这里的phone随便传一直值
                    Intent mainActIntent = new Intent(activity, MainActivity.class);
                    activity.startActivity(mainActIntent);
                    break;
                default:
                    ToastUtils.shortToast("服务端第三方登录失败");
                    break;

            }
        }

        @Override
        public void executeResultError(String result) {
        }
    };

    public final ReplyCommand weixinLogin = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.REGISTER_CLICK_WECHAT_ENTER);
        UMShareAPI mShareAPI = UMShareAPI.get(activity);
        if (mShareAPI.isInstall(activity, SHARE_MEDIA.WEIXIN)) {
            mShareAPI.doOauthVerify(activity, SHARE_MEDIA.WEIXIN, umAuthListener);
        } else {
            ToastUtils.shortToast("请先安装WEIXIN客户端");
        }
    });


    public final ReplyCommand help = new ReplyCommand(() -> {
        final String tmpRongToken = SpUtils.getString("tmpRongToken", "");
        if (TextUtils.isEmpty(tmpRongToken)) {
            UUID uuid = UUID.randomUUID();
            String tmpUid = uuid.toString();//创建一个临时的uid，用来获取临时的融云token
            LogKit.v("tmpUid:" + tmpUid);
            MsgManager.getRongToken(new BaseProtocol.IResultExecutor<RongTokenBean>() {
                @Override
                public void execute(RongTokenBean dataBean) {
                    String newTmpRongToken = dataBean.data.token;
                    SpUtils.setString("tmpRongToken", newTmpRongToken);
                    connectToRongCloud(newTmpRongToken);
                }

                @Override
                public void executeResultError(String result) {
                    ToastUtils.shortToast("获取临时融云token失败");
                }
            }, tmpUid, "11");
        } else {
            connectToRongCloud(tmpRongToken);
        }

    });

    public final ReplyCommand qqLogin = new ReplyCommand(() -> {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.REGISTER_CLICK_QQ_ENTER);
        UMShareAPI mShareAPI = UMShareAPI.get(activity);
        if (mShareAPI.isInstall(activity, SHARE_MEDIA.QQ)) {
            mShareAPI.doOauthVerify(activity, SHARE_MEDIA.QQ, umAuthListener);
        } else {
            ToastUtils.shortToast("请先安装QQ客户端");
        }
    });


    public final ReplyCommand protocol = new ReplyCommand(() -> {
        Intent intentSlashProtocolActivity = new Intent(activity, ProtocolActivity.class);
        activity.startActivity(intentSlashProtocolActivity);

    });

    public final ReplyCommand agreeProtocol = new ReplyCommand(() -> {

    });
}