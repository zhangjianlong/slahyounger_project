package com.slash.youth.v2.feature.label;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.domain.bean.LabelBean;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by acer on 2017/4/6.
 */

public class LabelSItemViewModel extends BViewModel {

    public boolean isCustom = false;
    public LabelBean data;

    public int index;

    public ObservableField<Boolean> checked = new ObservableField<>();

    public ReplyCommand<Boolean> checkedChangeCommand = new ReplyCommand(data->{
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putBoolean("checked", (boolean)data);
        Messenger.getDefault().send(bundle,MessageKey.LABEL_SELECT_SCOND);
    });

    public ReplyCommand reduce = new ReplyCommand(()->{
        Messenger.getDefault().send(index,MessageKey.LABEL_DELETE);
    });

    public LabelSItemViewModel(RxAppCompatActivity activity,boolean isCustom) {
        super(activity);
        this.isCustom = isCustom;
    }

    public LabelSItemViewModel(RxAppCompatActivity activity, LabelBean data, int index, boolean selected) {
        super(activity);
        this.data = data;
        this.checked.set(selected);
        this.index = index;
    }

    public void setSelected(boolean selected){
        this.checked.set(selected);
    }

    public boolean getSelected(){
        return checked.get();
    }
}
