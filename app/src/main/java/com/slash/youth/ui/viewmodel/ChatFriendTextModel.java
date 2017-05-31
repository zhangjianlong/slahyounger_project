package com.slash.youth.ui.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.core.op.lib.utils.PreferenceUtil;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.ItemChatFriendTextBinding;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.ui.activity.MyTaskActivity;
import com.slash.youth.utils.BitmapKit;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.TextUtil;
import com.slash.youth.v2.feature.dialog.common.CopyTextDialog;
import com.slash.youth.v2.feature.dialog.common.CopyTextViewModel;
import com.slash.youth.v2.util.ShareKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Arrays;

import static android.databinding.tool.util.GenerationalClassUtil.ExtensionFilter.BR;

/**
 * Created by zhouyifeng on 2016/11/16.
 */
public class ChatFriendTextModel extends BaseObservable {
    ItemChatFriendTextBinding mItemChatFriendTextBinding;
    RxAppCompatActivity mActivity;
    String mTargetAvatar;
    String content;
    private CopyTextDialog copyTextDialog;
    public final ObservableField<SpannableString> textContent = new ObservableField<>();

    public ChatFriendTextModel(ItemChatFriendTextBinding itemChatFriendTextBinding, RxAppCompatActivity activity, String content, String targetAvatar) {
        this.mItemChatFriendTextBinding = itemChatFriendTextBinding;
        this.mActivity = activity;
        this.mTargetAvatar = targetAvatar;
        this.content = content;
        initView();
        setTextContent(content.trim());
    }


    private void initView() {
        mItemChatFriendTextBinding.tvRev.setMovementMethod(LinkMovementMethod.getInstance());
        copyTextDialog = new CopyTextDialog(mActivity, new CopyTextViewModel(mActivity));
        mItemChatFriendTextBinding.tvRev.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyTextDialog.setCopyText(textContent.get().toString());
                copyTextDialog.show();
                return true;
            }
        });

        if (PreferenceUtil.readBoolean(mActivity, ShareKey.USER_ANONYMITY + MsgManager.targetId, false)) {
            mItemChatFriendTextBinding.ivChatOtherAvatar.setImageResource(R.mipmap.anonymity_avater);
        } else {
            if ((!"1000".equals(MsgManager.targetId)) && (!MsgManager.customerServiceUid.equals(MsgManager.targetId))) {
                BitmapKit.bindImage(mItemChatFriendTextBinding.ivChatOtherAvatar, GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + mTargetAvatar);
            } else {
                mItemChatFriendTextBinding.ivChatOtherAvatar.setImageResource(MsgManager.targetAvatarResource);
            }
        }
    }

    public void gotoTask(View v) {
        //判断 如果是1000号（消息助手），且内容中带有tid，就跳转到我的任务列表页面
        if ("1000".equals(MsgManager.targetId)) {
            if (extraInfo.contains("\"tid\"")) {
                Intent intentMyTaskActivity = new Intent(CommonUtils.getContext(), MyTaskActivity.class);
                mActivity.startActivity(intentMyTaskActivity);
            }
        }
    }

//    private SpannableString textContent;

    private String extraInfo;

    @Bindable
    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
//        notifyPropertyChanged(BR.extraInfo);
    }
//
//    @Bindable
//    public SpannableString getTextContent() {
//        return textContent;
//    }

    public void setTextContent(String content) {
        if ("1000".equals(MsgManager.targetId)) {
            textContent.set(TextUtil.matcherSearchTitle(CommonUtils.getContext().getResources().getColor(R.color.app_text_bg_blue), content, Arrays.asList(CommonUtils.getContext().getResources().getStringArray(R.array.keyword))));
        } else {
            textContent.set(new TextUtil().matcherUrl(content));

        }
    }


}
