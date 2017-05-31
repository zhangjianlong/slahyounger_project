package com.slash.youth.ui.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.core.op.lib.utils.PreferenceUtil;
import com.sina.weibo.sdk.constant.WBConstants;
import com.slash.youth.R;
import com.slash.youth.databinding.ItemChatOtherChangeContactWayBinding;
import com.slash.youth.databinding.ItemChatWebChangeContactWayBinding;
import com.slash.youth.domain.ChatCmdWebChangeContactBean;
import com.slash.youth.domain.CommonResultBean;
import com.slash.youth.domain.IsChangeContactBean;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.http.protocol.BaseProtocol;
import com.slash.youth.ui.activity.UserInfoActivity;
import com.slash.youth.utils.BitmapKit;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.util.ShareKey;

import java.io.File;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;

import static com.slash.youth.engine.MsgManager.targetAvatar;
import static com.slash.youth.engine.MsgManager.targetId;

/**
 * Created by zhouyifeng on 2016/11/17.
 */
public class ChatWebChangeContactWayModel extends BaseObservable {

    ItemChatWebChangeContactWayBinding itemChatWebChangeContactWayBinding;
    Activity mActivity;
    ChatModel mChatModel;
    String mOtherPhone;
    String mTargetAvatar;
    public ObservableField<String> content = new ObservableField<>();
    boolean mIsChangeContact;
    ChatCmdWebChangeContactBean chatCmdWebChangeContactBean;

    public ChatWebChangeContactWayModel(ItemChatWebChangeContactWayBinding itemChatWebChangeContactWayBinding, Activity activity, ChatModel chatModel, ChatCmdWebChangeContactBean chatCmdWebChangeContactBean) {
        this.itemChatWebChangeContactWayBinding = itemChatWebChangeContactWayBinding;
        this.mActivity = activity;
        this.mChatModel = chatModel;
        this.mTargetAvatar = targetAvatar;
        this.chatCmdWebChangeContactBean = chatCmdWebChangeContactBean;

        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        content.set("了解" + chatCmdWebChangeContactBean.getName());

        MsgManager.getIsChangeContact(new BaseProtocol.IResultExecutor<IsChangeContactBean>() {
            @Override
            public void execute(IsChangeContactBean dataBean) {
                if (dataBean.data.data.status == 1) {
                    itemChatWebChangeContactWayBinding.tvDeny.setBackgroundResource(R.drawable.shape_chat_deny_change_contact_way_bg);
                    itemChatWebChangeContactWayBinding.tvAgree.setBackgroundResource(R.drawable.shape_chat_agree_change_contact_way_bg);
                    itemChatWebChangeContactWayBinding.tvDeny.setEnabled(false);
                    itemChatWebChangeContactWayBinding.tvAgree.setEnabled(false);
                } else {
                    itemChatWebChangeContactWayBinding.tvDeny.setBackgroundResource(R.drawable.shape_chat_deny_add_friend_bg);
                    itemChatWebChangeContactWayBinding.tvAgree.setBackgroundResource(R.drawable.shape_chat_agree_add_friend_bg);
                }
            }

            @Override
            public void executeResultError(String result) {

            }
        }, chatCmdWebChangeContactBean.getUid());
//        if (mIsChangeContact) {
//            itemChatWebChangeContactWayBinding.tvDeny.setBackgroundResource(R.drawable.shape_chat_deny_change_contact_way_bg);
//            itemChatWebChangeContactWayBinding.tvAgree.setBackgroundResource(R.drawable.shape_chat_agree_change_contact_way_bg);
//            itemChatWebChangeContactWayBinding.tvDeny.setEnabled(false);
//            itemChatWebChangeContactWayBinding.tvAgree.setEnabled(false);
//        } else {
//            itemChatWebChangeContactWayBinding.tvDeny.setBackgroundResource(R.drawable.shape_chat_deny_change_contact_way_bg);
//            itemChatWebChangeContactWayBinding.tvAgree.setBackgroundResource(R.drawable.shape_chat_agree_change_contact_way_bg);
//            itemChatWebChangeContactWayBinding.tvDeny.setEnabled(false);
//            itemChatWebChangeContactWayBinding.tvAgree.setEnabled(false);
//        }
        if (PreferenceUtil.readBoolean(mActivity, ShareKey.USER_ANONYMITY + targetId, false))

        {
            itemChatWebChangeContactWayBinding.ivChatOtherAvatar.setImageResource(R.mipmap.anonymity_avater);
        } else

        {
            if ((!"1000".equals(targetId)) && (!MsgManager.customerServiceUid.equals(targetId))) {
                BitmapKit.bindImage(itemChatWebChangeContactWayBinding.ivChatOtherAvatar, GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + mTargetAvatar);
            } else {
                itemChatWebChangeContactWayBinding.ivChatOtherAvatar.setImageResource(MsgManager.targetAvatarResource);
            }

        }
    }

    //同意交换联系方式

    public void agreeChangeContact(View v) {
        MsgManager.getIsChangeContact(new BaseProtocol.IResultExecutor<IsChangeContactBean>() {
            @Override
            public void execute(IsChangeContactBean dataBean) {
                if (dataBean.data.data.status == 1) {
                    itemChatWebChangeContactWayBinding.tvDeny.setBackgroundResource(R.drawable.shape_chat_deny_change_contact_way_bg);
                    itemChatWebChangeContactWayBinding.tvAgree.setBackgroundResource(R.drawable.shape_chat_agree_change_contact_way_bg);
                    itemChatWebChangeContactWayBinding.tvDeny.setEnabled(false);
                    itemChatWebChangeContactWayBinding.tvAgree.setEnabled(false);
                    mChatModel.addViewInfo("手机号已经交换过");
                    mChatModel.addViewPhone(chatCmdWebChangeContactBean.getPhone());
                } else {
                    itemChatWebChangeContactWayBinding.tvDeny.setBackgroundResource(R.drawable.shape_chat_deny_add_friend_bg);
                    itemChatWebChangeContactWayBinding.tvAgree.setBackgroundResource(R.drawable.shape_chat_agree_add_friend_bg);

                    mChatModel.agreeChangePhone(chatCmdWebChangeContactBean.getUid(), chatCmdWebChangeContactBean.getRelationTitle(), "1");
                }
            }

            @Override
            public void executeResultError(String result) {

            }
        }, chatCmdWebChangeContactBean.getUid());
//        mChatModel.agreeChangeContact(chatCmdWebChangeContactBean.getPhone(), itemChatWebChangeContactWayBinding.tvDeny, itemChatWebChangeContactWayBinding.tvAgree);
    }

    //拒绝交换联系方式
    public void refuseChangeContact(View v) {
        MsgManager.getIsChangeContact(new BaseProtocol.IResultExecutor<IsChangeContactBean>() {
            @Override
            public void execute(IsChangeContactBean dataBean) {
                if (dataBean.data.data.status == 1) {
                    itemChatWebChangeContactWayBinding.tvDeny.setBackgroundResource(R.drawable.shape_chat_deny_change_contact_way_bg);
                    itemChatWebChangeContactWayBinding.tvAgree.setBackgroundResource(R.drawable.shape_chat_agree_change_contact_way_bg);
                    itemChatWebChangeContactWayBinding.tvDeny.setEnabled(false);
                    itemChatWebChangeContactWayBinding.tvAgree.setEnabled(false);
                    mChatModel.addViewInfo("手机号已经交换过");
                    mChatModel.addViewPhone(chatCmdWebChangeContactBean.getPhone());
                } else {
                    itemChatWebChangeContactWayBinding.tvDeny.setBackgroundResource(R.drawable.shape_chat_deny_add_friend_bg);
                    itemChatWebChangeContactWayBinding.tvAgree.setBackgroundResource(R.drawable.shape_chat_agree_add_friend_bg);

                    mChatModel.agreeChangePhone(chatCmdWebChangeContactBean.getUid(), chatCmdWebChangeContactBean.getRelationTitle(), "0");
                }
            }

            @Override
            public void executeResultError(String result) {

            }
        }, chatCmdWebChangeContactBean.getUid());
//        mChatModel.refuseChangeContact(itemChatWebChangeContactWayBinding.tvDeny, itemChatWebChangeContactWayBinding.tvAgree);
    }

    public void click(View v) {
        Intent intentUserInfoActivity = new Intent(CommonUtils.getContext(), UserInfoActivity.class);
        intentUserInfoActivity.putExtra("Uid", chatCmdWebChangeContactBean.getUid());
        mActivity.startActivity(intentUserInfoActivity);
    }
}
