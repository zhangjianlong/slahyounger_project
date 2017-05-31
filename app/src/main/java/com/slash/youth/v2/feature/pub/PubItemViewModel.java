package com.slash.youth.v2.feature.pub;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.weight.imgselector.MultiImageSelector;
import com.slash.youth.R;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import static com.slash.youth.v2.util.MessageKey.PUB_DEL_IMG;

/**
 * Created by acer on 2017/4/7.
 */

public class PubItemViewModel extends BViewModel {

    public ObservableField<Integer> closeVisible = new ObservableField<>(View.VISIBLE);
    public ObservableField<String> uri = new ObservableField<>();

    public ObservableField<Drawable> defaultImg = new ObservableField<>();
    boolean isDefault;
    int index;
    public ReplyCommand closeClick = new ReplyCommand(() -> {
        Messenger.getDefault().send(index, MessageKey.PUB_DEL_IMG);
    });

    public ReplyCommand imgClick = new ReplyCommand(() -> {
        if (isDefault) {
            MultiImageSelector selector = MultiImageSelector.create(activity);
            selector.showCamera(true);
            selector.count(5 - index);
            selector.multi();
            selector.setCrop(true);
            selector.start(activity, MultiImageSelector.REQUEST_IMAGE);
        }
    });

    public PubItemViewModel(RxAppCompatActivity activity, int index, String uri, boolean isDefault) {
        super(activity);
        this.isDefault = isDefault;
        this.uri.set(uri);
        this.index = index;
        if (isDefault) {
            this.uri.set("");
            defaultImg.set(activity.getResources().getDrawable(R.mipmap.icon_pub_upload_img));
            closeVisible.set(View.GONE);
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
