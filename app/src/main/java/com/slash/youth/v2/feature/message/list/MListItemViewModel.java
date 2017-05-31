package com.slash.youth.v2.feature.message.list;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.base.OnDialogLisetener;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.PreferenceUtil;
import com.google.gson.Gson;
import com.slash.youth.R;
import com.slash.youth.domain.ChatTaskInfoBean;
import com.slash.youth.domain.bean.ConversationBean;
import com.slash.youth.domain.interactor.message.DelConversationsUseCase;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.ui.activity.ChatActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.utils.IOUtils;
import com.slash.youth.v2.feature.dialog.message.DelMessageDialog;
import com.slash.youth.v2.feature.dialog.message.DelMessageViewModel;
import com.slash.youth.v2.util.MessageKey;
import com.slash.youth.v2.util.ShareKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Created by acer on 2017/3/13.
 */

public class MListItemViewModel extends BViewModel {

    public ConversationBean data;

    public ObservableField<String> company = new ObservableField<>();

    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> uri = new ObservableField<>();
    public ObservableField<String> unRead = new ObservableField<>();
    public ObservableField<Boolean> enable = new ObservableField<>(true);

    public ObservableField<Drawable> drawable = new ObservableField<>();

    public ObservableField<String> lastMsg = new ObservableField<>();
    public ObservableField<String> taskTitle = new ObservableField<>();
    public ObservableField<Integer> taskVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> unReadVisible = new ObservableField<>(View.INVISIBLE);
    DelConversationsUseCase delConversationsUseCase;
    private int position;
    public final ReplyCommand click = new ReplyCommand(() -> {
        long uid = data.getUid();
        if (uid == 1000) {
            MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MESSAGE_CLICK_SLASH_SIGNPICS);
        } else {
            MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MESSAGE_CLICK_OTHER_CHAT);
        }

//        Bundle bundle = new Bundle();
//        bundle.putLong("uid", uid);
//        ChatActivity.instance(activity, bundle);

        Intent intentChatActivity = new Intent(CommonUtils.getContext(), ChatActivity.class);
        intentChatActivity.putExtra("targetId", uid + "");
        intentChatActivity.putExtra(ShareKey.USER_ANONYMITY, PreferenceUtil.readBoolean(CommonUtils.getContext(), ShareKey.USER_ANONYMITY + data.getUid(), false));
        activity.startActivity(intentChatActivity);
    });

    public final ReplyCommand delClick = new ReplyCommand(() -> {

        DelMessageDialog delMessageDialog = new DelMessageDialog(activity, new DelMessageViewModel(activity));
        delMessageDialog.setOnDialogLisetener(new OnDialogLisetener() {
            @Override
            public void onConfirm() {

                try {
                    JSONObject jo = new JSONObject();
                    JSONArray jaUid = new JSONArray();
                    long uid = data.getUid();
                    jaUid.put(uid);
                    jo.put("list", jaUid);
                    delConversationsUseCase.setParams(jo.toString());
                    delConversationsUseCase.execute().compose(activity.bindToLifecycle())
                            .subscribe(d -> {
                                if (d.getStatus() == 1) {
                                    Messenger.getDefault().sendNoMsg(MessageKey.DELETE_MESSAGE);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {
            }
        });
        delMessageDialog.show();
    });

    public MListItemViewModel(RxAppCompatActivity activity, ConversationBean data, DelConversationsUseCase delConversationsUseCase, int position) {
        super(activity);
        this.data = data;
        this.position = position;
        this.delConversationsUseCase = delConversationsUseCase;

        if (!TextUtils.isEmpty(data.getCompany()) && !TextUtils.isEmpty(data.getPosition())) {//两个都不为空
            company.set("(" + data.getCompany() + "," + data.getPosition() + ")");
        } else if (!TextUtils.isEmpty(data.getCompany()) || !TextUtils.isEmpty(data.getPosition())) {//其中一个不为空,另一个为空
            company.set("(" + data.getCompany() + data.getPosition() + ")");
        }

        if (data.getUts() == 0) {
            //如果data.uts为0，代表没有消息记录时间，需要隐藏时间，这种情况应该只有消息助手和客服助手才会出现
            time.set("");
        } else {
            long timeSpan = System.currentTimeMillis() - data.getUts();
            long minutes = timeSpan / 1000 / 60;
            if (minutes <= 0) {
                if (data.getUid() == 1000 || MsgManager.customerServiceUid.equals(data.getUts() + "")) {
                    //没有和斜杠助手或客服助手收发消息，列表上显示“0 分钟前”-》应该不显示时间
                    time.set("");
                } else {
                    time.set("刚刚");
                }
            } else if (minutes < 60) {

                time.set(minutes + "分钟前");
            } else {
                long hours = minutes / 60;
                if (hours < 24) {
                    time.set(hours + "小时前");
                } else {
                    long days = hours / 24;
                    time.set(days + "天前");
                }
            }
        }


        if (PreferenceUtil.readBoolean(CommonUtils.getContext(), ShareKey.USER_ANONYMITY + data.getUid(), false)) {
            name.set("匿名");
            drawable.set(activity.getResources().getDrawable(R.mipmap.anonymity_avater));
            uri.set("");
        } else {
            if (data.getUid() == 1000) {
                enable.set(false);
                name.set("消息小助手");
                drawable.set(activity.getResources().getDrawable(R.mipmap.message_icon));
                uri.set("");
            } else if (MsgManager.customerServiceUid.equals(data.getUid() + "")) {
                enable.set(false);
                name.set("斜杠客服");
                drawable.set(activity.getResources().getDrawable(R.mipmap.customer_service_icon));
                uri.set("");
            } else {
                name.set(data.getName());
                uri.set(GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + data.getAvatar());
            }
        }
        RongIMClient.getInstance().getUnreadCount(Conversation.ConversationType.PRIVATE, data.getUid() + "", new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                int unreadCount = integer;
                if (unreadCount > 0) {
                    unReadVisible.set(View.VISIBLE);
                    unRead.set(unreadCount + "");
                } else {
                    unReadVisible.set(View.INVISIBLE);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        RongIMClient.getInstance().getLatestMessages(Conversation.ConversationType.PRIVATE, data.getUid() + "", 1, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                String lastMsg = "";
                if (messages != null) {
                    if (messages.size() > 0) {
                        Message message = messages.get(0);
                        String objectName = message.getObjectName();
                        Message.MessageDirection messageDirection = message.getMessageDirection();
                        if (messageDirection == Message.MessageDirection.SEND) {
                            //自己发送的消息
                            //如果是向斜杠小助手发送消息，只能发送文本，所以和下面的发送文本消息的处理方式是一样的
                            if (objectName.equals("RC:TxtMsg")) {
                                TextMessage textMessage = (TextMessage) message.getContent();
                                String extra = textMessage.getExtra();
                                if (TextUtils.isEmpty(extra)) {
                                    lastMsg = textMessage.getContent();
                                } else {
                                    String content = textMessage.getContent();
                                    if (content.contentEquals(MsgManager.CHAT_CMD_ADD_FRIEND)) {
                                        lastMsg = "您请求添加对方为好友";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_SHARE_TASK)) {
                                        lastMsg = "您向对方分享了一个任务";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_BUSINESS_CARD)) {
                                        lastMsg = "您向对方分享了个人名片";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_CHANGE_CONTACT)) {
                                        lastMsg = "您请求与对方交换联系方式";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_AGREE_ADD_FRIEND)) {
                                        lastMsg = "您同意添加对方为好友";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_REFUSE_ADD_FRIEND)) {
                                        lastMsg = "您拒绝添加对方为好友";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_AGREE_CHANGE_CONTACT)) {
                                        lastMsg = "您同意与对方交换联系方式";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_REFUSE_CHANGE_CONTACT)) {
                                        lastMsg = "您拒绝与对方交换联系方式";
                                    }
                                }
                            } else if (objectName.equals("RC:ImgMsg")) {
                                lastMsg = "有图片消息";
                            } else if (objectName.equals("RC:VcMsg")) {
                                lastMsg = "有语音消息";
                            }
                        } else if (messageDirection == Message.MessageDirection.RECEIVE) {
                            if (objectName.equals("RC:TxtMsg")) {
                                TextMessage textMessage = (TextMessage) message.getContent();
                                String extra = textMessage.getExtra();
                                if (TextUtils.isEmpty(extra)) {
                                    lastMsg = textMessage.getContent();
                                } else {
                                    String content = textMessage.getContent();
                                    if (content.contentEquals(MsgManager.CHAT_CMD_ADD_FRIEND)) {
                                        lastMsg = "对方请求添加您为好友";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_SHARE_TASK)) {
                                        lastMsg = "对方分享了一个任务";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_BUSINESS_CARD)) {
                                        lastMsg = "对方分享了个人名片";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_CHANGE_CONTACT)) {
                                        lastMsg = "对方请求交换联系方式";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_AGREE_ADD_FRIEND)) {
                                        lastMsg = "对方同意添加您为好友";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_REFUSE_ADD_FRIEND)) {
                                        lastMsg = "对方拒绝添加您为好友";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_AGREE_CHANGE_CONTACT)) {
                                        lastMsg = "对方同意交换联系方式";
                                    } else if (content.contentEquals(MsgManager.CHAT_CMD_REFUSE_CHANGE_CONTACT)) {
                                        lastMsg = "对方拒绝交换联系方式";
                                    }
                                }
                            } else if (objectName.equals("RC:ImgMsg")) {
                                lastMsg = "有图片消息";
                            } else if (objectName.equals("RC:VcMsg")) {
                                lastMsg = "有语音消息";
                            }
                        }
                    }
                }
                MListItemViewModel.this.lastMsg.set(lastMsg);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        displayRelatedTask(data);
    }

    public void displayRelatedTask(ConversationBean data) {
        File dataDir = CommonUtils.getContext().getFilesDir();
        File relatedTaskFiles = new File(dataDir,
                "relatedTaskDir/" + LoginManager.currentLoginUserId + "to" + data.getUid());
        if (relatedTaskFiles.exists()) {
            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                fis = new FileInputStream(relatedTaskFiles);
                isr = new InputStreamReader(fis);
                br = new BufferedReader(isr);
                String jsonData = br.readLine();
                Gson gson = new Gson();
                ChatTaskInfoBean chatTaskInfoBean = gson.fromJson(jsonData, ChatTaskInfoBean.class);
                taskVisible.set(View.VISIBLE);
                taskTitle.set(chatTaskInfoBean.title);
            } catch (FileNotFoundException e) {
                taskVisible.set(View.INVISIBLE);
                e.printStackTrace();
            } catch (IOException e) {
                taskVisible.set(View.INVISIBLE);
                e.printStackTrace();
            } finally {
                IOUtils.close(br);
                IOUtils.close(isr);
                IOUtils.close(fis);
            }
        } else {
            taskVisible.set(View.INVISIBLE);
        }
    }
}
