package com.slash.youth.v2.feature.login;

import android.content.Context;
import android.content.Intent;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.slash.youth.R;
import com.slash.youth.databinding.ActLoginBinding;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.v2.di.components.DaggerLoginComponent;
import com.slash.youth.v2.di.components.LoginComponent;
import com.slash.youth.v2.di.modules.LoginModule;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

@RootView(R.layout.act_login)
public final class LoginActivity extends BaseActivity<LoginViewModel, ActLoginBinding> {

    LoginComponent component;
    public QQLoginUiListener qqLoginUiListener;
    private AuthInfo mAuthInfo;
    public SsoHandler mSsoHandler;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerLoginComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .loginModule(new LoginModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        qqLoginUiListener = new QQLoginUiListener();
        mAuthInfo = new AuthInfo(CommonUtils.getContext(), GlobalConstants.ThirdAppId.APPID_QQ, "www.slashyouth.com", "");
        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, qqLoginUiListener);

        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public class QQLoginUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }
}
