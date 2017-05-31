package com.slash.youth.v2.feature.pub;


import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;

import com.core.op.bindingadapter.common.ItemView;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.weight.imgselector.MultiImageSelector;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.ActPubBinding;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.feature.label.LabelActivity;
import com.slash.youth.v2.feature.local.LocalActivity;
import com.slash.youth.v2.feature.pubaccept.PubAcceptActivity;
import com.slash.youth.v2.feature.pubcontent.PubContentActivity;
import com.slash.youth.v2.feature.release.ReleaseSucActivity;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

@PerActivity
public class PubViewModel extends BAViewModel<ActPubBinding> {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> nameNum = new ObservableField<>("0/15");
    public ObservableField<String> content = new ObservableField<>();
    public ObservableField<String> standard = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> unit = new ObservableField<>();
    public ObservableField<String> local = new ObservableField<>();

    private ArrayList<String> mSelectPath = new ArrayList<>();
    public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.item_pub_img);
    public final List<PubItemViewModel> itemViewModels = new ArrayList<>();

    public ReplyCommand localClick = new ReplyCommand(() -> {
        LocalActivity.instance(activity);
    });


    public ReplyCommand lableClick = new ReplyCommand(() -> {
        LabelActivity.instance(activity);
    });

    public ReplyCommand pubContent = new ReplyCommand(() -> {
        PubContentActivity.instance(activity);
    });

    public ReplyCommand pubAccept = new ReplyCommand(() -> {
        PubAcceptActivity.instance(activity);
    });

    public TextWatcher textWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            nameNum.set(temp.length() + "/15");
        }
    };

    int index = 0;

    @Inject
    public PubViewModel(RxAppCompatActivity activity) {
        super(activity);
        title.set(activity.getString(R.string.app_pub_title));
        local.set(activity.getString(R.string.app_pub_local_select));
    }

    @Override
    public void afterViews() {
        Messenger.getDefault().register(this, MessageKey.PUB_CITY_SELECTED, String.class, data -> {
            local.set(data);
        });
        Messenger.getDefault().register(this, MessageKey.PUB_CONTENT, String.class, data -> {
            content.set(data);
        });

        Messenger.getDefault().register(this, MessageKey.PUB_STANDED, String.class, data -> {
            standard.set(data);
        });

        Messenger.getDefault().register(this, MessageKey.PUB_DEL_IMG, Integer.class, data -> {
            mSelectPath.remove((int) data);
            itemViewModels.remove((int) data);
            index = 0;
            Observable.from(itemViewModels)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(d -> {
                        d.setIndex(index);
                        index++;
                    }, error -> {
                    }, () -> {
                        if (itemViewModels.size() == 4 && !itemViewModels.get(3).isDefault) {
                            itemViewModels.add(new PubItemViewModel(activity, index, "", true));
                        }
                        binding.hotRecyclerView.getAdapter().notifyDataSetChanged();
                    });
        });
        binding.tvPubContent.setHint(Html.fromHtml(CommonUtils.getContext().getString(R.string.app_pub_content_hint)));
        binding.tvPubAccept.setHint(Html.fromHtml(CommonUtils.getContext().getString(R.string.app_pub_accept_hint)));
        itemViewModels.add(new PubItemViewModel(activity, 0, "", true));
    }

    public void uploadImage(Intent data) {
        mSelectPath.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
        updataData();
    }

    public void updataData() {
        if (mSelectPath != null && mSelectPath.size() != 0) {
            itemViewModels.clear();
            index = 0;
            Observable.from(mSelectPath)
                    .subscribe(d -> {
                        itemViewModels.add(new PubItemViewModel(activity, index, d, false));
                        index++;
                    }, error -> {
                    }, () -> {
                        if (itemViewModels.size() < 5) {
                            itemViewModels.add(new PubItemViewModel(activity, index, "", true));
                        }
                        binding.hotRecyclerView.getAdapter().notifyDataSetChanged();
                    });
        }
    }

    public void commit() {
        ReleaseSucActivity.instance(activity);
    }
}