package com.slash.youth.v2.feature.search;

import android.databinding.ObservableField;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.StrUtil;
import com.slash.youth.v2.util.MessageKey;

/**
 * @author lenovo
 * @createDate 2017/4/12
 * @description
 */

public class SeachItemViewModel extends BViewModel {
    public ObservableField<String> historyText = new ObservableField<>();

    public SeachItemViewModel(String string) {
        historyText.set(string);

    }


    public SeachItemViewModel() {

    }

    public ReplyCommand clickItem = new ReplyCommand(() -> {
        if (StrUtil.isEmpty(historyText.get().trim())) {
            return;
        }
        String searchData = historyText.get().trim();
        Messenger.getDefault().send(searchData, MessageKey.SEARCH_CLICK);
    });

    public ReplyCommand delHistory = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(MessageKey.DEL_CLICK);
    });

    public ReplyCommand moreTask = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(MessageKey.MORE_USER);
    });

    public ReplyCommand moreUser = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(MessageKey.MORE_TASK);
    });

    public ReplyCommand taskDetail = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(MessageKey.TASK_DETAIL);
    });
    public ReplyCommand userDetail = new ReplyCommand(() -> {
        Messenger.getDefault().sendNoMsg(MessageKey.USER_DETAIL);
    });


}
