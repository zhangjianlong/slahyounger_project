package com.slash.youth.v2.feature.chat.list;

import android.databinding.ObservableField;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by acer on 2017/3/31.
 */

public class ChatListItemViewModel extends BViewModel {

    //发送和接收消息的时间间隔  11月7日 14:17
    public final ObservableField<String> infoText = new ObservableField<>();

    //交换手机号码
    public final ObservableField<String> otherContactInfo = new ObservableField<>();
    public final ReplyCommand callToOther = new ReplyCommand(() -> {

    });
    //接受到的图片
    public final ObservableField<String> imgUrl = new ObservableField<>();
    //接受到的文本
    public final ReplyCommand gotoTask = new ReplyCommand(() -> {

    });
    public final ObservableField<String> textContent = new ObservableField<>();
    //发送的图片
    public final ReplyCommand sendMsgAgain = new ReplyCommand(() -> {

    });
    public final ObservableField<String> sendImgUrl = new ObservableField<>();
    //发送的语音
    public final ReplyCommand sendVoiceMsgAgain = new ReplyCommand(() -> {

    });
    public final ReplyCommand playVoice = new ReplyCommand(() -> {

    });
    public final ObservableField<String> voiceDuration = new ObservableField<>();
    //发送的task
    public final ReplyCommand sendTaskMsgAgain = new ReplyCommand(() -> {

    });
    //发送的task
    public final ReplyCommand sendTextMsgAgain = new ReplyCommand(() -> {

    });
    public final ObservableField<String> mySendText = new ObservableField<>();
    //更换联系方式
    public final ReplyCommand refuseChangeContact = new ReplyCommand(() -> {

    });
    public final ReplyCommand agreeChangeContact = new ReplyCommand(() -> {

    });
    //添加好友
    public final ReplyCommand refuseAddFriend = new ReplyCommand(() -> {

    });
    public final ReplyCommand agreeAddFriend = new ReplyCommand(() -> {

    });
    //去用户信息页
    public final ReplyCommand gotoUserInfoPage = new ReplyCommand(() -> {

    });
    public final ReplyCommand sendInfoMsgAgain = new ReplyCommand(() -> {

    });
    //接受到的语音
    public final ReplyCommand playRevVoice = new ReplyCommand(() -> {

    });
    public final ObservableField<String> revVoiceDuration = new ObservableField<>();

    public enum ChatType {
        SEND_TEXT,
        SEND_IMG,
        SEND_SHARE,
        RECEIVE_TEXT,
        RECEIVE_IMG,
        RECEIVE_SHARE,
        RECEIVE_PHONE,
        INFO,
        PHONE;
    }

    /*text显示内容*/
    public ObservableField<String> text = new ObservableField<>();

    public ChatListItemViewModel(RxAppCompatActivity activity) {
        super(activity);
    }
}
