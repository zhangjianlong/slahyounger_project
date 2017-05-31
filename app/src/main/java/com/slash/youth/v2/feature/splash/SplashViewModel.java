package com.slash.youth.v2.feature.splash;


import android.Manifest;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.PreferenceUtil;
import com.orhanobut.logger.Logger;
import com.slash.youth.R;
import com.slash.youth.databinding.ActSplashBinding;
import com.slash.youth.domain.interactor.login.CustomServiceUseCase;
import com.slash.youth.domain.interactor.login.LoginResultUseCase;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.feature.dialog.splash.SplashDialog;
import com.slash.youth.v2.util.ShareKey;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

@PerActivity
public class SplashViewModel extends BAViewModel<ActSplashBinding> {

    CustomServiceUseCase useCase;

    LoginResultUseCase loginResultUseCase;
    RxPermissions permissions;

    SplashDialog dialog;

    @Inject
    public SplashViewModel(RxAppCompatActivity activity,
                           CustomServiceUseCase useCase,
                           LoginResultUseCase loginResultUseCase,
                           RxPermissions permissions,
                           SplashDialog dialog) {
        super(activity);
        this.useCase = useCase;
        this.loginResultUseCase = loginResultUseCase;
        this.permissions = permissions;

        this.dialog = dialog;
    }

    @Override
    public void afterViews() {
        MobclickAgent.setScenarioType(CommonUtils.getContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        permissions.request(Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SYSTEM_ALERT_WINDOW)
                .subscribe(granted -> {
                    if (granted) {
                        initdata();
                    } else {
                        activity.finish();
                    }
                });

        dialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {

            }

            @Override
            public void onCancel() {

            }
        });
        MobclickAgent.setScenarioType(activity, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void initdata() {
        long customerServiceUid = PreferenceUtil.readLong(activity, ShareKey.CUSTOMSERVERUUID, 0);

//        if (customerServiceUid <= 0) {
        useCase.execute().compose(activity.bindToLifecycle())
                .subscribe(data -> {
                    PreferenceUtil.write(activity, ShareKey.CUSTOMSERVERUUID, data.uid);
                }, error -> {
                    Logger.i(activity.getString(R.string.app_error_customservice) + error.getMessage());
                });

//        }
//
//        loginResultUseCase.execute().compose(activity.bindToLifecycle())
//                .subscribe(data -> {
//                    PreferenceUtil.write(activity, ShareKey.SERVER_TOKEN, data.token);
//                }, error -> {
//
//                });
    }
}