package com.slash.youth.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.PreferenceUtil;
import com.slash.youth.R;
import com.slash.youth.databinding.DialogVersionUpdateBinding;
import com.slash.youth.domain.CustomerServiceBean;
import com.slash.youth.domain.TokenLoginResultBean;
import com.slash.youth.domain.VersionBean;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.http.protocol.BaseProtocol;
import com.slash.youth.ui.activity.base.BaseActivity;
import com.slash.youth.ui.view.fly.RandomLayout;
import com.slash.youth.ui.viewmodel.DialogVersionUpdateModel;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.LogKit;
import com.slash.youth.utils.PackageUtil;
import com.slash.youth.utils.SpUtils;
import com.slash.youth.utils.TimeUtils;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.feature.dialog.splash.SplashDialog;
import com.slash.youth.v2.feature.dialog.splash.SplashViewModel;
import com.slash.youth.v2.feature.login.*;
import com.slash.youth.v2.feature.login.LoginActivity;
import com.slash.youth.v2.feature.main.MainActivity;
import com.slash.youth.v2.util.MessageKey;
import com.slash.youth.v2.util.ShareKey;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zhouyifeng on 2016/12/11.
 */
public class SplashActivity extends BaseActivity {
    private int type = 1;//1表示android,2表示ios
    private int versionCode;
    private ProgressDialog mDialog;
    private DialogVersionUpdateBinding dialogVersionUpdateBinding;
    private AlertDialog dialogVersion;
    private AlertDialog permissionDialog;
    RxPermissions permissions;
    SplashDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.setScenarioType(CommonUtils.getContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        ImageView ivSplash = new ImageView(CommonUtils.getContext());
        ivSplash.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams ll = new RandomLayout.LayoutParams(-1, -1);
        ivSplash.setLayoutParams(ll);
        ivSplash.setImageResource(R.mipmap.splash);
        setContentView(ivSplash);
        dialog = new SplashDialog(this, new SplashViewModel(this));
        dialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {

            }

            @Override
            public void onCancel() {
                switchActivity();
            }
        });
        open();
    }

    private void switchActivity() {
        permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SYSTEM_ALERT_WINDOW)
                .subscribe(granted -> {
                    getCustomerServiceUid();
                    login();
                    checkVersion();
                });
    }


    private void getCustomerServiceUid() {
        long customerServiceUid = MsgManager.getCustomerServiceUidFromSp();
        if (customerServiceUid > 0) {
            //sp中保存有随机客服的uid
            MsgManager.customerServiceUid = customerServiceUid + "";
        } else {
            MsgManager.getCustomerService(new BaseProtocol.IResultExecutor<CustomerServiceBean>() {
                @Override
                public void execute(CustomerServiceBean dataBean) {
                    long customerServiceUid = dataBean.data.uid;
                    MsgManager.customerServiceUid = customerServiceUid + "";
                }

                @Override
                public void executeResultError(String result) {
                    ToastUtils.shortToast("获取客服UID失败:" + result);
                }
            });
        }
    }

    private void login() {
        CommonUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tokenLogin();
            }
        }, 3000);
    }

    /**
     * token自动登录
     */
    private void tokenLogin() {
        final String rongToken = SpUtils.getString("rongToken", "");//通过融云connect方法去检测是否还有效，如果有效，就直接连接，如果无效，重新从服务端接口获取
        final long uid = SpUtils.getLong("uid", -1);
        final String token = SpUtils.getString("token", "");
        LoginManager.token = token;
        LogKit.v("token:" + token + " uid:" + uid);
        if (!TextUtils.isEmpty(token)) {
            //如果本地保存的token不为空，尝试用token自动登录
            LoginManager.tokenLogin(new BaseProtocol.IResultExecutor<TokenLoginResultBean>() {
                @Override
                public void execute(TokenLoginResultBean dataBean) {
                    //页面跳转
                    if (uid != -1) {
                        LogKit.v("token登录成功，直接跳转到首页");
                        LoginManager.currentLoginUserId = uid;
                        LoginManager.currentLoginUserPhone = PreferenceUtil.readString(CommonUtils.getContext(), ShareKey.USER_PHONE);
//                        LoginManager.token = token;
                        LoginManager.token = dataBean.data.token;//每次token登录，都保存服务端返回过来的最新token
                        LoginManager.rongToken = rongToken;
                        SpUtils.setString("token", LoginManager.token);
                        //链接融云
                        MsgManager.connectRongCloud(LoginManager.rongToken);

//                        Intent intentHomeActivity = new Intent(CommonUtils.getContext(), HomeActivity.class);
//                        startActivity(intentHomeActivity);
                        Intent intentHomeActivity2 = new Intent(CommonUtils.getContext(), MainActivity.class);
                        startActivity(intentHomeActivity2);
                        finish();
                    } else {
//                        gotoLoginActivity();
                        gotoGuidActivity();
                    }
                }

                @Override
                public void executeResultError(String result) {
                    LogKit.v("token登录失败");
//                    gotoLoginActivity();
                    gotoGuidActivity();
                }
            });
        } else {
//            gotoLoginActivity();
            gotoGuidActivity();
        }
    }

//    private void gotoLoginActivity() {
//        Intent intentLoginActivity = new Intent(CommonUtils.getContext(), LoginActivity.class);
//        startActivity(intentLoginActivity);
//        finish();
//    }

    private void gotoGuidActivity() {
        boolean isGuid = SpUtils.getBoolean(GlobalConstants.SpConfigKey.IS_GUID, false);
        if (isGuid) {
            Intent intentLoginActivity = new Intent(CommonUtils.getContext(),LoginActivity.class);
            startActivity(intentLoginActivity);
        } else {
            Intent intentGuidActivity = new Intent(CommonUtils.getContext(), GuidActivity.class);
            startActivity(intentGuidActivity);
            SpUtils.setBoolean(GlobalConstants.SpConfigKey.IS_GUID, true);
        }
        finish();
    }

    //检测版本
    private void checkVersion() {
        // 获取当前的版本号
        versionCode = PackageUtil.getVersionCode(this);
        //获取网络的版本号
        LoginManager.checkVersion(new onCheckVersion(), type, versionCode);
    }

    //检测版本
    public class onCheckVersion implements BaseProtocol.IResultExecutor<VersionBean> {
        @Override
        public void execute(VersionBean dataBean) {
            int rescode = dataBean.getRescode();
            if (rescode == 0) {
                VersionBean.VersionDataBean versionDataBean = dataBean.getData();
                VersionBean.VersionDataBean.DataBean data = versionDataBean.getData();

                long cts = data.getCts();
                String versionTime = TimeUtils.getTimeData(cts);//发版本时间

                String version = data.getVersion();//展示给用户的新版本信息
                SpUtils.setString("version", version);
                String content = data.getContent();//更新文案

                int type = data.getType();//1表示android,2表示ios
                long id = data.getId();

                String url = data.getUrl();//下载路径
                if (!SpUtils.getString("downloadurl", "none").equals("none")) {
                    SpUtils.setString("downloadurl", url);
                }

                // url = "http://dldir1.qq.com/weixin/android/weixin653android980.apk";
                int forceupdate = data.getForceupdate();//是否强制更新 0表示不强制，1表示强制
                long NetVersionCode = data.getCode();//服务端更新的版本
                // 检测版本更新
                if (NetVersionCode > versionCode) {
                    //需要更新
                    showVersionUpdateDialog(forceupdate, url);
                } else {
                    //不用更新
//                    login();
                }
            }
        }

        @Override
        public void executeResultError(String result) {
            LogKit.d("result:" + result);
        }
    }

    private void showVersionUpdateDialog(int forceupdate, String url) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogVersionUpdateBinding = DataBindingUtil.inflate(LayoutInflater.from(CommonUtils.getContext()), R.layout.dialog_version_update, null, false);
        DialogVersionUpdateModel dialogVersionUpdateModel = new DialogVersionUpdateModel(dialogVersionUpdateBinding, forceupdate, url);
        dialogVersionUpdateBinding.setDialogVersionUpdateModel(dialogVersionUpdateModel);
        dialogBuilder.setView(dialogVersionUpdateBinding.getRoot());
        dialogVersion = dialogBuilder.create();
        dialogVersion.show();
        dialogVersion.setCanceledOnTouchOutside(false);
        Window dialogSubscribeWindow = dialogVersion.getWindow();
        WindowManager.LayoutParams dialogParams = dialogSubscribeWindow.getAttributes();
        dialogParams.width = CommonUtils.dip2px(280);//宽度
        dialogParams.height = CommonUtils.dip2px(169);//高度
        dialogSubscribeWindow.setAttributes(dialogParams);
        cannel();
        update(url);
    }

    //取消
    private void cannel() {
        dialogVersionUpdateBinding.cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVersion.dismiss();
                //进入登录页
//                login();
            }
        });
    }

    //更新
    private void update(final String url) {
        dialogVersionUpdateBinding.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVersion.dismiss();
                //下载apk
                downLoadApk(url);
            }
        });
    }

    // 下载APK方法
    protected void downLoadApk(String url) {
        //校验是否有SD卡
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "没有SD卡！", Toast.LENGTH_SHORT).show();
//            login();
            return;
        }

        //在后端下载APK,xUtuils
        RequestParams params = new RequestParams(url);
        params.setAutoRename(true);//断点下载
        File file = new File(Environment.getExternalStorageDirectory(), "SlashYouth.apk");
        String absolutePath = file.getAbsolutePath();
        //params.setSaveFilePath("/mnt/sdcard/SlashYouth.apk");
        params.setSaveFilePath(absolutePath);

        x.http().get(params, new Callback.ProgressCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        installApk(new File(Environment.getExternalStorageDirectory(), "SlashYouth.apk"));
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                            ToastUtils.shortToast("更新失败");
                        }
                        LogKit.d("更新失败");
                        ex.printStackTrace();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }

                    @Override
                    public void onWaiting() {
                    }

                    @Override
                    public void onStarted() {
                        mDialog = new ProgressDialog(SplashActivity.this);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("正在下载中...");
                        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mDialog.setProgress(0);
                        mDialog.show();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                      /* mDialog.setMax((int) total);
                        mDialog.setProgress((int)current);*/

                        mDialog.setMax(100);
                        int progress = (int) (current * 100 / total);
                        mDialog.setProgress(progress);

                    }
                }
        );
    }

    // 安装APK的方法
    public void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri data = Uri.fromFile(file);
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Messenger.getDefault().sendNoMsg(MessageKey.HIDE_FLOAT_WINDOW);
    }


    public void open() {
        //开启悬浮框前先请求权限
        if ("Xiaomi".equals(Build.MANUFACTURER)) {//小米手机
            requestPermission();
        } else if ("Meizu".equals(Build.MANUFACTURER)) {//魅族手机
            requestPermission();
        } else {
            switchActivity();
        }
    }


    /**
     * 请求用户给予悬浮窗的权限
     */
    public void requestPermission() {
        if (isFloatWindowOpAllowed(this)) {//已经开启
            switchActivity();
        } else {
            dialog.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (isFloatWindowOpAllowed(this)) {//已经开启
                if (dialog != null) {
                    dialog.dismiss();
                }
                rx.Observable.timer(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(d -> {
                            switchActivity();
                        });
            }
        }
    }

    /**
     * 判断悬浮窗权限
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24);  // AppOpsManager.OP_SYSTEM_ALERT_WINDOW
        } else {
            if ((context.getApplicationInfo().flags & 1 << 27) == 1 << 27) {
                return true;
            } else {
                return false;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {

                Class<?> spClazz = Class.forName(manager.getClass().getName());
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op,
                        Binder.getCallingUid(), context.getPackageName());
                Log.e("399", " property: " + property);

                if (AppOpsManager.MODE_ALLOWED == property) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("399", "Below API 19 cannot invoke!");
        }
        return false;
    }
}
