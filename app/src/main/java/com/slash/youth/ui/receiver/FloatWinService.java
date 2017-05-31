package com.slash.youth.ui.receiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.core.op.lib.messenger.Messenger;
import com.slash.youth.engine.MsgManager;
import com.slash.youth.ui.view.ChatFloatWin;
import com.slash.youth.utils.LogKit;
import com.slash.youth.v2.util.MessageKey;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * @author: jl
 * @Time: 2017/3/13
 * @Desc:浮动窗口的Service
 */

public class FloatWinService extends Service {

    private ChatFloatWin chatFloatWin;


    @Override
    public void onCreate() {
        super.onCreate();
        chatFloatWin = new ChatFloatWin(this);
        Messenger.getDefault().register(this, MessageKey.NEW_MESSAGE, () -> {
            setIvMsgIconState();
            setMsgChangeListener();
        });
        Messenger.getDefault().register(this, MessageKey.HIDE_FLOAT_WINDOW, () -> {
            if (null != chatFloatWin) {
                chatFloatWin.showChatIv(false);
            }

        });
        Messenger.getDefault().register(this, MessageKey.SHOW_FLOAT_WINDOW, () -> {
            if (null != chatFloatWin) {
                chatFloatWin.showChatIv(true);
            }
        });

        Messenger.getDefault().register(this, MessageKey.REMOVE_FLOAT_WINDOW, () -> {
            if (null != chatFloatWin) {
                chatFloatWin.remove();
                chatFloatWin = null;
            }

            stopSelf();
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 刚进入消息页的时候，或者是回退到消息页的时候(这两种情况都会调用onStart方法)，通过融云的API获取总的未读消息数，消息Icon的颜色
     */
    private void setIvMsgIconState() {
        RongIMClient.getInstance().getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                int totalUnreadCount = integer;
                LogKit.v("HomeActivity unReadCount:" + totalUnreadCount);
                if (totalUnreadCount <= 0) {//没有聊天消息，显示灰色的Icon
                    chatFloatWin.changeIvColor(true);
                } else {//有聊天消息，显示红色的Icon
                    chatFloatWin.changeIvColor(false);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        }, Conversation.ConversationType.PRIVATE);
    }

    /**
     * 注册未读消息的监听器，这样每次来新的聊天消息都能根据未读数量来更新icon颜色
     */
    private void setMsgChangeListener() {
        MsgManager.setTotalUnReadCountListener(new MsgManager.TotalUnReadCountListener() {

            @Override
            public void displayTotalUnReadCount(int count) {
                LogKit.v("HomeActivity unReadCount:" + count);
                if (count <= 0) {//没有聊天消息，显示灰色的Icon
                    chatFloatWin.changeIvColor(true);
                } else {//有聊天消息，显示红色的Icon
                    chatFloatWin.changeIvColor(false);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        chatFloatWin.remove();
        super.onDestroy();
    }
}
