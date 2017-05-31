package com.slash.youth.v2.feature.chat.list;


import android.databinding.ObservableBoolean;

import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.slash.youth.databinding.FrgChatlistBinding;
import com.slash.youth.domain.interactor.main.OtherInfoUseCase;
import com.slash.youth.domain.interactor.message.ChangePhoneUseCase;
import com.slash.youth.domain.interactor.message.FriendStatusUseCase;
import com.slash.youth.domain.repository.MessageRepository;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ResultCallback;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import rx.Observable;

@PerActivity
public class ChatListViewModel extends BFViewModel<FrgChatlistBinding> {

    private long uid;

    OtherInfoUseCase otherInfoUseCase;
    FriendStatusUseCase friendStatusUseCase;
    ChangePhoneUseCase changePhoneUseCase;

    public final List<ChatListItemViewModel> itemViewModels = new ArrayList<>();

    public final ItemViewSelector<ChatListItemViewModel> itemView = itemView();

    public final ObservableBoolean isRefreshing = new ObservableBoolean(false);


    public final ReplyCommand onRefreshCommand = new ReplyCommand<>(() -> {
    });

    @Inject
    public ChatListViewModel(RxAppCompatActivity activity,
                             OtherInfoUseCase otherInfoUseCase,
                             FriendStatusUseCase friendStatusUseCase,
                             ChangePhoneUseCase changePhoneUseCase) {
        super(activity);
        this.otherInfoUseCase = otherInfoUseCase;
        this.friendStatusUseCase = friendStatusUseCase;
        this.changePhoneUseCase = changePhoneUseCase;
        uid = activity.getIntent().getLongExtra("uid", 0);
    }

    @Override
    public void afterViews() {

    }

    private void updateView(List<Message> messages) {
        Observable.from(messages)
                .subscribe(data -> {
                });
    }

    /**
     * 从客户端本地读取聊天记录
     */
    private void getHistoryMessage(int historyMessageId) {
        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE, uid + "", historyMessageId, 20, new ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


    public ItemViewSelector<ChatListItemViewModel> itemView() {
        return new BaseItemViewSelector<ChatListItemViewModel>() {
            @Override
            public void select(ItemView itemView, int position, ChatListItemViewModel item) {
            }

            @Override
            public int viewTypeCount() {
                return 1;
            }
        };
    }
}