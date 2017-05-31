package com.slash.youth.ui.viewmodel;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.net.Uri;

import com.core.op.lib.utils.PreferenceUtil;
import com.slash.youth.R;
import com.slash.youth.databinding.ItemChatFriendPicBinding;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.utils.BitmapKit;
import com.slash.youth.utils.LogKit;
import com.slash.youth.v2.util.ShareKey;

import org.xutils.x;

/**
 * Created by zhouyifeng on 2016/11/16.
 */
public class ChatFriendPicModel extends BaseObservable {

    ItemChatFriendPicBinding mItemChatFriendPicBinding;
    Activity mActivity;
    Uri mThumUri;
    String mTargetAvatar;

    public ChatFriendPicModel(ItemChatFriendPicBinding itemChatFriendPicBinding, Activity activity, Uri thumUri, String targetAvatar) {
        this.mItemChatFriendPicBinding = itemChatFriendPicBinding;
        this.mActivity = activity;
        this.mThumUri = thumUri;
        this.mTargetAvatar = targetAvatar;

        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
//        mItemChatFriendPicBinding.ivChatFriendPic.setImageURI(mThumUri);
        LogKit.v(mThumUri.toString());
        x.image().bind(mItemChatFriendPicBinding.ivChatFriendPic, mThumUri.toString());


        if (PreferenceUtil.readBoolean(mActivity, ShareKey.USER_ANONYMITY + MsgManager.targetId, false)) {
            mItemChatFriendPicBinding.ivChatOtherAvatar.setImageResource(R.mipmap.anonymity_avater);
        } else {
            if ((!"1000".equals(MsgManager.targetId)) && (!MsgManager.customerServiceUid.equals(MsgManager.targetId))) {
                BitmapKit.bindImage(mItemChatFriendPicBinding.ivChatOtherAvatar, GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + mTargetAvatar);
            } else {
                mItemChatFriendPicBinding.ivChatOtherAvatar.setImageResource(MsgManager.targetAvatarResource);
            }
        }
    }

}
