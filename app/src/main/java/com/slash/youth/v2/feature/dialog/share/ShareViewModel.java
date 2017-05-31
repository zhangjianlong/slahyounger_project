package com.slash.youth.v2.feature.dialog.share;


import android.widget.Toast;

import com.core.op.lib.base.BDViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.databinding.DlgShareBinding;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;

import javax.inject.Inject;

public class ShareViewModel extends BDViewModel<DlgShareBinding> {

    public ArrayList<SnsPlatform> platforms = new ArrayList<>();

    private SHARE_MEDIA share_media = SHARE_MEDIA.ALIPAY;

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(activity,platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity,platform + " 分享失败啦"+t.getMessage(), Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    public final ReplyCommand friendPlace = new ReplyCommand(()->{
        share_media = platforms.get(8).mPlatform;
        ShareAction shareAction = new ShareAction(activity);
        shareAction.setPlatform(share_media).setCallback(umShareListener).share();
        this.dialog.dismiss();
    });

    public final ReplyCommand weixin = new ReplyCommand(()->{
        share_media = platforms.get(7).mPlatform;
        ShareAction shareAction = new ShareAction(activity);
        shareAction.setPlatform(share_media).setCallback(umShareListener).share();
        this.dialog.dismiss();
    });


    public final ReplyCommand slashFriend = new ReplyCommand(()->{
        Messenger.getDefault().sendNoMsg(MessageKey.SHARE_FRIEND);
        this.dialog.dismiss();

    });

    public final ReplyCommand qq = new ReplyCommand(()->{

        share_media = platforms.get(5).mPlatform;
        ShareAction shareAction = new ShareAction(activity);
        shareAction.setPlatform(share_media).setCallback(umShareListener).share();
        this.dialog.dismiss();
    });

    public final ReplyCommand cannel = new ReplyCommand(()->{
        this.dialog.dismiss();
    });

    @Inject
    public ShareViewModel(RxAppCompatActivity activity) {
        super(activity);
        initPlatforms();
    }


    //加载平台
    private void initPlatforms(){
        platforms.clear();
        for (SHARE_MEDIA e : SHARE_MEDIA.values()) {
            if (!e.toString().equals(SHARE_MEDIA.GENERIC.toString())){
                platforms.add(e.toSnsPlatform());
            }
        }
    }

}