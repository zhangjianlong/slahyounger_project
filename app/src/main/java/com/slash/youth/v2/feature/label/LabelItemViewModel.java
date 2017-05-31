package com.slash.youth.v2.feature.label;

import android.databinding.ObservableField;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.core.op.lib.base.BViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.R;
import com.slash.youth.domain.bean.LabelBean;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Observable;

/**
 * Created by acer on 2017/4/6.
 */

public class LabelItemViewModel extends BViewModel {

    public LabelBean data;

    public int index;
    public ObservableField<Integer> selected = new ObservableField<>(View.VISIBLE);

    public ObservableField<ColorDrawable> bgDrawable = new ObservableField<>();
    public ObservableField<Integer> textDrawable = new ObservableField<>();

    public ReplyCommand click = new ReplyCommand(() -> {
        Messenger.getDefault().send(index, MessageKey.LABEL_SELECT_STAIR);
    });

    public LabelItemViewModel(RxAppCompatActivity activity, LabelBean data, int index, boolean selected) {
        super(activity);
        this.data = data;
        this.index = index;

        if(selected){
            this.selected.set(View.GONE);
            bgDrawable.set(new ColorDrawable(activity.getResources().getColor(R.color.white)));
            textDrawable.set(activity.getResources().getColor(R.color.app_theme_colorPrimary));
        }else{
            this.selected.set(View.VISIBLE);
            bgDrawable.set(new ColorDrawable(activity.getResources().getColor(R.color.app_theme_background_f3f3f3)));
            textDrawable.set(activity.getResources().getColor(R.color.app_text_black999));
        }
    }

    public void isSelected(boolean selected){
        if(selected){
            bgDrawable.set(new ColorDrawable(activity.getResources().getColor(R.color.white)));
            textDrawable.set(activity.getResources().getColor(R.color.app_theme_colorPrimary));
        }else{
            bgDrawable.set(new ColorDrawable(activity.getResources().getColor(R.color.app_theme_background_f3f3f3)));
            textDrawable.set(activity.getResources().getColor(R.color.app_text_black999));
        }
    }
}
