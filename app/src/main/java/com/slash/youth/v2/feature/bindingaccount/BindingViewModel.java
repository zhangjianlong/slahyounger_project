package com.slash.youth.v2.feature.bindingaccount;


import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.utils.RxCountDown;
import com.core.op.lib.weight.progress.Progress;
import com.slash.youth.R;
import com.slash.youth.data.util.SpUtil;
import com.slash.youth.databinding.ActBindingBinding;
import com.slash.youth.domain.bean.PhoneLoginResultBean;
import com.slash.youth.domain.interactor.login.CheckBindingUseCase;
import com.slash.youth.domain.interactor.login.PhoneLoginUseCase;
import com.slash.youth.domain.interactor.login.VerifyUseCase;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.LoginCheckUtil;
import com.slash.youth.utils.SpUtils;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.feature.dialog.common.CommonDialog;
import com.slash.youth.v2.feature.main.MainActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

@PerActivity
public class BindingViewModel extends BAViewModel<ActBindingBinding> {
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_binding_phone));
    public final ObservableField<String> sendVerifyText = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_binding_verify));
    public final ObservableField<Boolean> sendVerifyEnable = new ObservableField<>(true);
    public final ObservableField<String> verifyCode = new ObservableField<>();
    private String tempPhoneNum;
    private String tempVerifyCode;
    private VerifyUseCase verifyUseCase;
    private CheckBindingUseCase checkBindingUseCase;
    private PhoneLoginUseCase loginUseCase;
    private Progress progress;
    private CommonDialog commonDialog;
    private String userInfo;
    private String _3ptoken;
    private int platformType;


    public final ReplyCommand binding = new ReplyCommand(() -> {
        tempVerifyCode = verifyCode.get();
        tempPhoneNum = phoneNum.get();
        if (!LoginCheckUtil.checkBinding(tempPhoneNum, tempVerifyCode)) {
            return;
        }
        tempVerifyCode = tempVerifyCode.trim();
        tempPhoneNum = tempPhoneNum.trim();
        progress = Progress.create(activity).setStyle(Progress.Style.SPIN_INDETERMINATE);
        progress.show();
        Map<String, String> map = new HashMap<>();
        map.put("phone", tempPhoneNum);
        map.put("loginPlatform", platformType + "");
        checkBindingUseCase.setParams(JsonUtil.mapToJson(map));
        checkBindingUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            progress.dismiss();
            switch (data.getRescode()) {
                //手机号已注册，未绑定
                case 1:
                    openDialog();
                    break;
                //手机号未注册
                case 10:
                    bindingAccount();
                    break;
                //手机号已绑定
                case 0:
                    AppToast.show(CommonUtils.getContext(), R.string.app_binding_account_already_binding);
                    break;
                default:
                    AppToast.show(CommonUtils.getContext(), R.string.app_binding_account_check_fail);
                    break;
            }

        });


    });

    private void openDialog() {
        if (!commonDialog.isShowing())
            commonDialog.show();
    }


    public final ReplyCommand sendVerify = new ReplyCommand(() -> {
        tempPhoneNum = phoneNum.get();
        if (!LoginCheckUtil.checkPhoeNumFormat(tempPhoneNum)) {
            return;
        }
        RxCountDown.countdown(60).compose(activity.bindToLifecycle()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                sendVerifyEnable.set(true);
                sendVerifyText.set(CommonUtils.getContext().getString(R.string.app_binding_verify));
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
    public BindingViewModel(RxAppCompatActivity activity,
                            CheckBindingUseCase checkBindingUseCase,
                            VerifyUseCase verifyUseCase, PhoneLoginUseCase loginUseCase,
                            CommonDialog commonDialog) {
        super(activity);
        this.verifyUseCase = verifyUseCase;
        this.checkBindingUseCase = checkBindingUseCase;
        this.commonDialog = commonDialog;
        this.loginUseCase = loginUseCase;
    }

    @Override
    public void afterViews() {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle != null) {
            userInfo = bundle.getString("userInfo");
            _3ptoken = bundle.getString("3ptoken");
            platformType = bundle.getInt("platformType");
        }
        commonDialog.initValue(CommonUtils.getContext().getString(R.string.app_binding_account_binding));
        commonDialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {
                bindingAccount();
            }

            @Override
            public void onCancel() {
                activity.finish();
            }
        });
    }


    private void bindingAccount() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", tempPhoneNum);
        map.put("pin", tempVerifyCode);
        map.put("3pToken", _3ptoken);
        map.put("userInfo", userInfo);
        loginUseCase.setParams(JsonUtil.mapToJson(map));
        loginUseCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    if (data.getData() == null) {
                        ToastUtils.shortToast("登录失败");
                        return;
                    }
                    PhoneLoginResultBean.Data data1 = data.getData();
                    String rongToken = data1.getRongToken();//融云token
                    String token = data1.getToken();
                    long uid = data1.getUid();
                    switch (data.getRescode()) {
                        case 11:
                            break;
                        case 0:
                            MsgManager.connectRongCloud(rongToken);
                            savaLoginState(uid, token, rongToken);
                            Intent mainActIntent = new Intent(activity, MainActivity.class);
                            activity.startActivity(mainActIntent);
                            activity.finish();
                            break;
                        case 7:
                            ToastUtils.shortToast("验证码错误");
                            break;
                        default:
                            break;
                    }

                });
    }


    private void savaLoginState(long uid, String token, String rongToken) {
        LoginManager.currentLoginUserId = uid;
        LoginManager.token = token;
        LoginManager.rongToken = rongToken;

        SpUtil.write("uid", uid);
        SpUtil.write("token", token);
        SpUtil.write("rongToken", rongToken);
    }
}