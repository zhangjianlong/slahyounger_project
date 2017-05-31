package com.slash.youth.v2.feature.profile;


import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.StrUtil;
import com.slash.youth.R;
import com.slash.youth.databinding.ActProfileBinding;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

@PerActivity
public class ProfileViewModel extends BAViewModel<ActProfileBinding> {
    public final ObservableField<String> title = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_profile_title));
    public final ObservableField<String> templateInput = new ObservableField<>();
    public final ObservableField<String> profileTemplate = new ObservableField<>();
    public final ObservableField<String> templateSize = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_profile_template_limit));
    private String[] templates = CommonUtils.getContext().getResources().getStringArray(R.array.profile_template);
    private int index = 0;

    public TextWatcher templateWatch = new TextWatcher() {
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
            templateSize.set(temp.length() + "/300");
        }
    };

    @Inject
    public ProfileViewModel(RxAppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void afterViews() {
        profileTemplate.set(templates[index]);
    }


    public ReplyCommand changeTemplate = new ReplyCommand(() -> {
        if (index >= templates.length - 1) {
            index = 0;
        }
        index = index + 1;
        profileTemplate.set(templates[index]);
    });

    public void savaTemplate() {
        String content = templateInput.get();
        if (content == null || StrUtil.isEmpty(content.toString().trim())) {
            AppToast.show(CommonUtils.getContext(), CommonUtils.getContext().getString(R.string.app_profile_empty));
            return;
        }
        content = content.toString().trim();
        HashMap<String, String> data = new HashMap<>();
        data.put("", content);

        Messenger.getDefault().send(content, MessageKey.USER_SAVE_PROFILE);
        activity.finish();
    }
}