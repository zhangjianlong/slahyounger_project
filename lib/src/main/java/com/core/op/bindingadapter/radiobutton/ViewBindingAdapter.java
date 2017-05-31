package com.core.op.bindingadapter.radiobutton;

import android.databinding.BindingAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.core.op.lib.command.ReplyCommand;

public class ViewBindingAdapter {

    @BindingAdapter(value = {"checkedChangeCommand"}, requireAll = false)
    public static void onCheckedChangeCommand(final RadioButton radioButton,
                                              final ReplyCommand<Boolean> replyCommand) {
        if (replyCommand != null) {
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    replyCommand.execute(isChecked);
                }
            });
        }
    }
}
