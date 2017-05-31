package com.slash.youth.v2.feature.userinfo.tab.about;


import android.databinding.ObservableField;

import com.core.op.lib.base.BFViewModel;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.utils.JsonUtil;
import com.slash.youth.databinding.FrgAboutBinding;
import com.slash.youth.domain.bean.OtherInfo;
import com.slash.youth.domain.interactor.main.OtherInfoUseCase;
import com.slash.youth.engine.LoginManager;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;

import javax.inject.Inject;

@PerActivity
public class AboutViewModel extends BFViewModel<FrgAboutBinding> {
    private OtherInfoUseCase useCase;
    public ObservableField<OtherInfo.UinfoBean> userInfo = new ObservableField<>();

    @Inject
    public AboutViewModel(RxAppCompatActivity activity, OtherInfoUseCase useCase) {
        super(activity);
        this.useCase = useCase;
    }

    @Override
    public void afterViews() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", LoginManager.currentLoginUserId + "");
        map.put("anonymity", "1");
        useCase.setParams(JsonUtil.mapToJson(map));
        useCase.execute().compose(activity.bindToLifecycle())
                .map(d -> d.getUinfo())
                .subscribe(data -> {
                    userInfo.set(data);
                }, error -> {

                });


    }

}
