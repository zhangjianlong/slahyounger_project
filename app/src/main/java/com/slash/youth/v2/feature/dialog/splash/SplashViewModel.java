package com.slash.youth.v2.feature.dialog.splash;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.AppToast;
import com.slash.youth.databinding.DlgSplashBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

@PerActivity
public class SplashViewModel extends BDViewModel<DlgSplashBinding> {
    public final ReplyCommand confim = new ReplyCommand(() -> {
        openSetting();
    });


    public final ReplyCommand cancel = new ReplyCommand(() -> {
        dialog.dismiss();
        onDialogLisetener.onCancel();
    });


    @Inject
    public SplashViewModel(RxAppCompatActivity activity) {
        super(activity);
    }


    /**
     * 打开权限设置界面
     */
    public void openSetting() {
        try {

            Intent localIntent = new Intent(
                    "miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter",
                    "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", activity.getPackageName());
            activity.startActivityForResult(localIntent, 11);

        } catch (ActivityNotFoundException localActivityNotFoundException) {
            Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent1.setData(uri);
            activity.startActivityForResult(intent1, 11);
        }

    }

}