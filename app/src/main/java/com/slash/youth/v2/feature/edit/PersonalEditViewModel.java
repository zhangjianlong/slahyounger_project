package com.slash.youth.v2.feature.edit;


import android.content.Intent;
import android.databinding.ObservableField;

import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.JsonUtil;
import com.core.op.lib.utils.StrUtil;
import com.core.op.lib.weight.imgselector.MultiImageSelector;
import com.slash.youth.R;
import com.slash.youth.databinding.ActPersonaleditBinding;
import com.slash.youth.domain.TagBean;
import com.slash.youth.domain.bean.OtherInfo;
import com.slash.youth.domain.bean.UserInfo;
import com.slash.youth.domain.interactor.main.SaveCompanyUseCase;
import com.slash.youth.domain.interactor.main.MineInfoUseCase;
import com.slash.youth.domain.interactor.main.OtherInfoUseCase;
import com.slash.youth.domain.interactor.main.SaveHeadUserCase;
import com.slash.youth.domain.interactor.main.SaveInfoUseCase;
import com.slash.youth.domain.interactor.main.SaveLocationUseCase;
import com.slash.youth.domain.interactor.main.SaveNameUseCase;
import com.slash.youth.domain.interactor.main.SaveSexUseCase;
import com.slash.youth.domain.interactor.main.SaveTagUserCase;
import com.slash.youth.domain.interactor.main.UserHeadUseCase;
import com.slash.youth.domain.interactor.main.UserInfoUseCase;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.ui.activity.ReplacePhoneActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.Constants;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.v2.feature.label.LabelActivity;
import com.slash.youth.v2.feature.local.LocalActivity;
import com.slash.youth.v2.feature.profile.ProfileActivity;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class PersonalEditViewModel extends BAViewModel<ActPersonaleditBinding> {
    public final ObservableField<String> title = new ObservableField<>(CommonUtils.getContext().getString(R.string.app_personal_title));
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> phoneNumber = new ObservableField<>();
    public final ObservableField<String> company = new ObservableField<>();
    public final ObservableField<String> companyPostion = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> profile = new ObservableField<>();
    public final ObservableField<String> headUrl = new ObservableField<>();
    public final ObservableField<Boolean> man = new ObservableField<>(true);
    public final ObservableField<Boolean> woman = new ObservableField<>(false);
    public final ObservableField<Boolean> job = new ObservableField<>(true);
    public final ObservableField<Boolean> self = new ObservableField<>(false);
    public final ObservableField<UserInfo.UinfoBean> data = new ObservableField<>();

    private ArrayList<String> mSelectPath;
    private UserHeadUseCase userHeadUseCase;
    private MineInfoUseCase mineInfoUseCase;
    private OtherInfoUseCase infoUseCase;
    private SaveNameUseCase saveNameUseCase;
    private SaveSexUseCase saveSexUseCase;
    private SaveInfoUseCase saveInfoUseCase;
    private SaveCompanyUseCase saveCompanyUseCase;
    private SaveHeadUserCase saveHeadUserCase;
    private SaveTagUserCase saveTagUserCase;
    private SaveLocationUseCase saveLocationUseCase;
    private UserInfoUseCase userInfoUseCase;
    private String userName;
    private String userProvince;
    private String userCity;
    private String userDesc;
    private String userPosition;
    private String userCompany;
    private int userSex;
    private int userCareertype;
    private ArrayList<String> needTag = new ArrayList<>();
    private ArrayList<String> provideTag = new ArrayList<>();
    private boolean isSaveInfo;
    private boolean isSaveCompany;
    private boolean isSaveSex;
    private boolean isSaveNeedTag;
    private boolean isSaveLocation;
    private boolean isSaveProTag;


    @Inject
    public PersonalEditViewModel(RxAppCompatActivity activity, UserHeadUseCase userHeadUseCase, MineInfoUseCase mineInfoUseCase, OtherInfoUseCase infoUseCase,
                                 SaveNameUseCase saveNameUseCase, SaveSexUseCase saveSexUseCase, SaveInfoUseCase saveInfoUseCase,
                                 SaveCompanyUseCase saveCompanyUseCase, SaveHeadUserCase saveHeadUserCase, SaveTagUserCase saveTagUserCase
            , SaveLocationUseCase saveLocationUseCase, UserInfoUseCase userInfoUseCase) {
        super(activity);
        this.userHeadUseCase = userHeadUseCase;
        this.mineInfoUseCase = mineInfoUseCase;
        this.infoUseCase = infoUseCase;
        this.saveNameUseCase = saveNameUseCase;
        this.saveSexUseCase = saveSexUseCase;
        this.saveInfoUseCase = saveInfoUseCase;
        this.saveCompanyUseCase = saveCompanyUseCase;
        this.saveHeadUserCase = saveHeadUserCase;
        this.saveLocationUseCase = saveLocationUseCase;
        this.saveTagUserCase = saveTagUserCase;
        this.userInfoUseCase = userInfoUseCase;
    }

    @Override
    public void afterViews() {
        Messenger.getDefault().register(this, MessageKey.PUB_CITY_SELECTED, String.class, data -> {
            area.set(data);

        });

        Messenger.getDefault().register(this, MessageKey.USER_SAVE_PROFILE, String.class, data -> {
            profile.set(data);
        });

        Messenger.getDefault().register(this, MessageKey.USER_SAVE_PHONE, String.class, data -> {
            phoneNumber.set(data);
        });
        loadData();
        needTag.add("测试");
        needTag.add("开发");
        needTag.add("后台");
        provideTag.add("开发1");
        provideTag.add("后台1");

    }


    public final ReplyCommand setHead = new ReplyCommand(() -> {
        MultiImageSelector selector = MultiImageSelector.create(activity);
        selector.showCamera(true);
        selector.count(9);
        selector.single();
        selector.origin(mSelectPath);
        selector.setCrop(true);
        selector.start(activity, MultiImageSelector.REQUEST_IMAGE);
        //编辑头像的埋点
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_EDIT_AVATAR);
    });


    public final ReplyCommand setProfile = new ReplyCommand(() -> {
        Intent intent = new Intent(activity, ProfileActivity.class);
        activity.startActivityForResult(intent, Constants.USERINFO_PROFILE);
    });

    public final ReplyCommand setNeedTag = new ReplyCommand(() -> {
        LabelActivity.instance(activity);
    });

    public final ReplyCommand setProvideTag = new ReplyCommand(() -> {
        LabelActivity.instance(activity);
    });


    public void saveHead(String url) {
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        saveHeadUserCase.setParams(JsonUtil.mapToJson(map));
        saveHeadUserCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
        }, error -> {
        });
    }

    public void saveLocation() {
        Map<String, String> map = new HashMap<>();
        map.put("province", userProvince);
        map.put("city", userCity);
        saveLocationUseCase.setParams(JsonUtil.mapToJson(map));
        saveLocationUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            isSaveLocation = true;
            closeAct();
        }, error -> {
        });
    }


    public void saveTag(ArrayList<String> tag, int type) {
        TagBean tagBean = new TagBean();
        tagBean.setTag(tag);
        tagBean.setType(type);
        saveTagUserCase.setParams(JsonUtil.GsonString(tagBean));
        saveTagUserCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            switch (type) {
                case 1:
                    isSaveNeedTag = true;
                    break;
                case 2:
                    isSaveProTag = true;
                    break;
            }
            closeAct();
        }, error -> {
        });
    }


    public void saveNeedTag() {
        saveTag(needTag, 1);
    }

    public void saveProTag() {
        saveTag(provideTag, 2);
    }


    public void uploadImage(String imgPath) {
        userHeadUseCase.setParams(imgPath);
        userHeadUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            String url = data.getFileId();
            saveHead(url);
        }, error -> {
        });
    }


    public final ReplyCommand setPhoneNumber = new ReplyCommand(() -> {
        //埋点
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.MINE_CLICK_TELEPHONE_NUMBER);
        Intent replacePhoneActivity = new Intent(activity, ReplacePhoneActivity.class);
        activity.startActivity(replacePhoneActivity);
    });


    public final ReplyCommand setArea = new ReplyCommand(() -> {
        Intent replacePhoneActivity = new Intent(activity, LocalActivity.class);
        activity.startActivityForResult(replacePhoneActivity, Constants.USERINFO_AREA);

    });

    private void loadData() {
        userInfoUseCase.execute().compose(activity.bindToLifecycle()).subscribe(d -> {

            data.set(d.getUinfo());
            UserInfo.UinfoBean info = d.getUinfo();
            profile.set(info.getDesc());
            name.set(info.getName());
            company.set(info.getCompany());
            companyPostion.set(info.getPosition());
            phoneNumber.set(info.getPhone() + "");
            headUrl.set(GlobalConstants.HttpUrl.IMG_DOWNLOAD + "?fileId=" + info.getAvatar());
            if (StrUtil.isEmpty(info.getCity())) {
                area.set(info.getProvince());
            } else {
                area.set(info.getProvince() + "-" + info.getCity());
            }
            switch (info.getSex()) {
                case 1:
                    man.set(true);
                    woman.set(false);
                    break;
                case 2:
                    man.set(false);
                    woman.set(true);
                    break;
            }

            switch (info.getCareertype()) {
                case 1:
                    job.set(true);
                    self.set(false);
                    break;
                case 2:
                    job.set(false);
                    self.set(true);
                    break;
            }
        }, error -> {
        });

    }


    public void saveName() {
        Map<String, String> map = new HashMap<>();
        map.put("name", userName);
        saveNameUseCase.setParams(JsonUtil.mapToJson(map));
        saveNameUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {

        }, err -> {

        });
    }


    public void saveSex() {
        Map<String, String> map = new HashMap<>();
        map.put("sex", userSex + "");
        saveSexUseCase.setParams(JsonUtil.mapToJson(map));
        saveSexUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            isSaveSex = true;
            closeAct();
        }, err -> {
        });
    }

    public void saveInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("name", userName);
        map.put("careertype", userCareertype + "");
        map.put("desc", userDesc);
        saveInfoUseCase.setParams(JsonUtil.mapToJson(map));
        saveInfoUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            isSaveInfo = true;
            closeAct();
        }, err -> {
        });
    }

    public void saveCompany() {
        Map<String, String> map = new HashMap<>();
        map.put("company", userCompany);
        map.put("position", userPosition);
        saveCompanyUseCase.setParams(JsonUtil.mapToJson(map));
        saveCompanyUseCase.execute().compose(activity.bindToLifecycle()).subscribe(data -> {
            isSaveCompany = true;
            closeAct();
        }, err -> {
        });
    }

    public boolean checkData() {
        userName = name.get();
        if (StrUtil.isEmpty(userName)) {
            AppToast.show(activity, "姓名不能为空");
            return false;
        }

        userCompany = company.get();
        userPosition = companyPostion.get();
        if (StrUtil.isEmpty(userCompany)) {
            AppToast.show(activity, "公司不能为空");
            return false;
        }

        if (StrUtil.isEmpty(userPosition)) {
            AppToast.show(activity, "职位不能为空");
            return false;
        }

        if (!(needTag != null && needTag.size() > 0)) {
            AppToast.show(activity, "我需要的标签不能为空");
            return false;
        }

        if (!(provideTag != null && needTag.size() > 0)) {
            AppToast.show(activity, "能提供的标签不能为空");
            return false;
        }

        if (man.get()) {
            userSex = 1;
        }
        if (woman.get()) {
            userSex = 2;
        }

        if (job.get()) {
            userCareertype = 1;
        }
        if (self.get()) {
            userCareertype = 2;
        }

        userDesc = profile.get();
        if (StrUtil.isEmpty(userDesc)) {
            AppToast.show(activity, "个人简介不能为空");
            return false;
        }

        if (StrUtil.isEmpty(area.get())) {
            AppToast.show(activity, "区域不能为空");
            return false;
        }
        String areaTemp = area.get();
        String[] sourceStrArray = areaTemp.split("-");
        switch (sourceStrArray.length) {
            case 1:
                userProvince = sourceStrArray[0];
                break;
            case 2:
                userProvince = sourceStrArray[0];
                userCity = sourceStrArray[1];
                break;
        }

        return true;
    }


    private void closeAct() {
        if (isSaveInfo && isSaveCompany && isSaveLocation && isSaveNeedTag && isSaveProTag && isSaveSex) {
            activity.finish();
        }

    }


}