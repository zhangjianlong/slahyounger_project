package com.core.op.bindingadapter.checkbox;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.core.op.lib.command.ReplyCommand;

public class ViewBindingAdapter {

    @BindingAdapter(value = {"checkedChangeCommand"}, requireAll = false)
    public static void onCheckedChangeCommand(final AppCompatCheckBox checkBox,
                                              final ReplyCommand<Boolean> replyCommand) {
        if (replyCommand != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    replyCommand.execute(isChecked);
                }
            });
        }
    }
}
