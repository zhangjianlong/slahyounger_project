package com.slash.youth.ui.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.core.op.lib.weight.picker.OptionsPickerView;
import com.slash.youth.BR;
import com.slash.youth.databinding.ActivitySubscribeBinding;
import com.slash.youth.domain.SkillLabelBean;
import com.slash.youth.engine.MyManager;
import com.slash.youth.ui.activity.SearchActivity;
import com.slash.youth.ui.activity.SubscribeActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.Constants;
import com.slash.youth.utils.LogKit;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.base.BaseDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhouyifeng on 2016/9/8.
 */
public class ActivitySubscribeModel extends BaseObservable {
    ActivitySubscribeBinding mActivitySubscribeBinding;
    SubscribeActivity mActivity;
    public String[] mainLabelsArr;
    private Iterator iter;
    private Map.Entry entry;
    private int value;
    private boolean isEditor;
    private boolean isPublish;
    private boolean isAddSkill;
    private OptionsPickerView pvOptions;
    private ArrayList<String> options1Items = new ArrayList<String>();

    public ActivitySubscribeModel(ActivitySubscribeBinding activitySubscribeBinding, SubscribeActivity activity,
                                  boolean isEditor, boolean isAddSkill) {
        this.mActivitySubscribeBinding = activitySubscribeBinding;
        this.isEditor = isEditor;
        this.mActivity = activity;
        this.isPublish = mActivity.getIntent().getBooleanExtra("isPublish", false);
        this.isAddSkill = isAddSkill;
        initView();
    }

    private void initpicker() {
        pvOptions = new OptionsPickerView(BaseDialog.newDialog(mActivitySubscribeBinding.getRoot().getContext()));
        //三级联动效果
        pvOptions.setPicker(options1Items);
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                int value = options1;
                mActivitySubscribeBinding.tvFirstSkillLabelTitle.setText(options1Items.get(options1));
                mActivity.checkedFirstLabel = options1Items.get(options1);
                listener.OnOkChooseMainLabelListener(value);
            }
        });
    }

    private void initView() {
        initData();
    }

    private void initData() {
        mActivity.setOnListener(new SubscribeActivity.OnListener() {
            @Override
            public void OnListener(ArrayList<SkillLabelBean> firstList) {
                int i = 0;
                mainLabelsArr = new String[firstList.size()];
                for (SkillLabelBean skillLabelBean : firstList) {
                    String tag = skillLabelBean.getTag();
                    mainLabelsArr[i] = tag;
                    i += 1;
                    options1Items.add(tag);
                    initpicker();
                }

            }


        });
    }

    private int rlChooseMainLabelVisible = View.INVISIBLE;

    @Bindable
    public int getRlChooseMainLabelVisible() {
        return rlChooseMainLabelVisible;
    }

    public void setRlChooseMainLabelVisible(int rlChooseMainLabelVisible) {
        this.rlChooseMainLabelVisible = rlChooseMainLabelVisible;
        notifyPropertyChanged(BR.rlChooseMainLabelVisible);
    }

    public void openRlChooseMainLabel(View v) {
        if (SubscribeActivity.clickCount == 0) {
            if (null == pvOptions || (null == options1Items || options1Items.size() == 0)) {
                ToastUtils.shortCenterToast("标签数据为空");
                return;
            }
            pvOptions.show();
        } else {
            ToastUtils.shortCenterToast("取消已选标签，重新选择");
        }
    }

    //点击按钮
    public void okChooseMainLabel(View v) {
        setRlChooseMainLabelVisible(View.INVISIBLE);
        mActivity.checkedFirstLabel = mainLabelsArr[value];
        listener.OnOkChooseMainLabelListener(value);
    }

    //取消
    public void cannelChooseMainLabel(View view) {
        setRlChooseMainLabelVisible(View.GONE);
    }

    /**
     * 提交标签
     *
     * @param v
     */
    public void submitChooseSkillLabel(View v) {
        Intent intentResult = new Intent();
        Bundle bundleCheckedLabelsData = new Bundle();
        bundleCheckedLabelsData.putString("checkedFirstLabel", mActivity.checkedFirstLabel);
        bundleCheckedLabelsData.putString("checkedSecondLabel", mActivity.checkedSecondLabel);
        bundleCheckedLabelsData.putStringArrayList("listCheckedLabelName", mActivity.listCheckedLabelName);
        bundleCheckedLabelsData.putStringArrayList("listCheckedLabelTag", mActivity.listCheckedLabelTag);
        SubscribeActivity.saveListCheckedLabelName = mActivity.listCheckedLabelName;
        intentResult.putExtra("bundleCheckedLabelsData", bundleCheckedLabelsData);
        if (isEditor) {
            mActivity.setResult(Activity.RESULT_OK, intentResult);
        } else {
            if (isPublish) {
                mActivity.setResult(10, intentResult);
            } else {
                mActivity.setResult(Constants.SKILL_MANAGER_ADD_LABEL, intentResult);
            }
        }

        mActivity.finish();
    }

    public void goBack(View v) {
        if (isEditor) {
            LogKit.d("是编辑页面返回的");
        }
        mActivity.finish();
    }


    //接口回调传递数据
    public interface OnOkChooseMainLabelListener {
        void OnOkChooseMainLabelListener(int tagId);
    }

    private OnOkChooseMainLabelListener listener;

    public void setOnOkChooseMainLabelListener(OnOkChooseMainLabelListener listener) {
        this.listener = listener;
    }
}
