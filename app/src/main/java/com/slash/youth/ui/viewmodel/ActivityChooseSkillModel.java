package com.slash.youth.ui.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.core.op.lib.utils.PreferenceUtil;
import com.core.op.lib.weight.picker.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.ActivityChooseSkillBinding;
import com.slash.youth.domain.AllSkillLablesBean;
import com.slash.youth.domain.CommonResultBean;
import com.slash.youth.domain.LoginTagBean;
import com.slash.youth.engine.LoginManager;
import com.slash.youth.http.protocol.BaseProtocol;
import com.slash.youth.ui.activity.HomeActivity2;
import com.slash.youth.ui.activity.LoginActivity;
import com.slash.youth.ui.activity.PerfectInfoActivity;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.utils.CustomEventAnalyticsUtils;
import com.slash.youth.utils.IOUtils;
import com.slash.youth.utils.LogKit;
import com.slash.youth.utils.ToastUtils;
import com.slash.youth.v2.base.BaseDialog;
import com.slash.youth.v2.feature.main.MainActivity;
import com.slash.youth.v2.util.ShareKey;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by zhouyifeng on 2016/9/12.
 */
public class ActivityChooseSkillModel extends BaseObservable {

    ActivityChooseSkillBinding mActivityChooseSkillBinding;
    Activity mActivity;
    boolean isChooseMainLabel;//true为选择添加一级标签，false为选择添加二级标签
    String[] optionalMainLabels;
    String[] optionalSecondLabels;
    ArrayList<AllSkillLablesBean.Tag_3> choosedThirdLabels = new ArrayList<AllSkillLablesBean.Tag_3>();
    ArrayList<AllSkillLablesBean.Tag_3> selectThirdLabels = new ArrayList<AllSkillLablesBean.Tag_3>();
    private OptionsPickerView pvOptions1;
    private OptionsPickerView pvOptions2;
    private List<String> options1Items = new ArrayList<String>();
    private List<String> options2Items = new ArrayList<String>();
    private LinearLayout llSkillLabelsLine = null;

    public ActivityChooseSkillModel(ActivityChooseSkillBinding activityChooseSkillBinding, Activity activity) {
        this.mActivityChooseSkillBinding = activityChooseSkillBinding;
        this.mActivity = activity;
        initView();
    }

    private void initView() {
        initData();
    }

    private void initData() {
        //清空SharePreferences中的登录信息,解决token时未完成个人信息的注册账号直接进入首页的问题
//        LoginManager.clearSpLoginInfo();
        getDataFromServer();
    }

    AllSkillLablesBean allSkillLablesBean;

    private void getDataFromServer() {
        //先获取本地的标签缓存
        allSkillLablesBean = getTagCache();
        if (allSkillLablesBean != null) {
            firstLoadLabels();
        } else {
            LoginManager.loginGetTag(new BaseProtocol.IResultExecutor<String>() {
                @Override
                public void execute(String dataBean) {
                    setTagCache(dataBean);
                    allSkillLablesBean = getTagCache();
                    if (allSkillLablesBean == null) {
                        ToastUtils.shortToast("后台返回标签数据为空");
                    } else {
                        firstLoadLabels();
                    }
                }

                @Override
                public void executeResultError(String result) {
                    //这里不会执行
                }
            });
        }
    }

    /**
     * 刚进入页面的时候，显示标签
     */
    private void firstLoadLabels() {
        chooseTag1Index = 0;
        chooseTag2Index = 0;
        Collection<AllSkillLablesBean.Tag_1> tag1Coll = allSkillLablesBean.mapTag_1.values();
        tag1Arr = new AllSkillLablesBean.Tag_1[tag1Coll.size()];
        tag1Coll.toArray(tag1Arr);
        //默认选中的一级标签是“发展|规划”,所以需要遍历出“发展|规划”这个一级标签
        for (int i = 0; i < tag1Arr.length; i++) {
            String tag1Name = tag1Arr[i].tag;
            if (tag1Name.equals("发展|规划")) {
                chooseTag1Index = i;
                break;
            }
        }

        AllSkillLablesBean.Tag_1 tag_1 = tag1Arr[chooseTag1Index];
        Collection<AllSkillLablesBean.Tag_2> tag2Coll = tag_1.mapTag_2.values();
        tag2Arr = new AllSkillLablesBean.Tag_2[tag2Coll.size()];
        tag2Coll.toArray(tag2Arr);
        setChoosedMainLabel(tag1Arr[chooseTag1Index].tag);
        setChoosedSecondLabel(tag2Arr[chooseTag2Index].tag);
        initOptionItem2(chooseTag2Index);
        setThirdSkillLabels();
    }

    private AllSkillLablesBean getTagCache() {
        File fileCache = CommonUtils.getContext().getCacheDir();
        File fileTagJson = new File(fileCache, "sys_tag.json");
        if (!fileTagJson.exists()) {
            return null;
        } else {
            FileInputStream fisTagJson = null;
            ObjectInputStream oisTagJson = null;
            try {
                fisTagJson = new FileInputStream(fileTagJson);
                oisTagJson = new ObjectInputStream(fisTagJson);
                AllSkillLablesBean allSkillLablesBean = null;
                if ((allSkillLablesBean = (AllSkillLablesBean) oisTagJson.readObject()) != null) {
                    return allSkillLablesBean;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(oisTagJson);
                IOUtils.close(fisTagJson);
            }
        }
        return null;
    }

    private void setTagCache(String tagJson) {
        File fileCache = CommonUtils.getContext().getCacheDir();
        if (!fileCache.exists()) {
            fileCache.mkdirs();
        }
        File fileTagJson = new File(fileCache, "sys_tag.json");
        FileOutputStream fosTagJson = null;
        ObjectOutputStream oosTagJson = null;
        try {
            fosTagJson = new FileOutputStream(fileTagJson);
            oosTagJson = new ObjectOutputStream(fosTagJson);
            Gson gson = new Gson();

            Type listTagsType = new TypeToken<ArrayList<LoginTagBean>>() {
            }.getType();
            ArrayList<LoginTagBean> listTags = gson.fromJson(tagJson, listTagsType);
            AllSkillLablesBean allSkillLablesBean = new AllSkillLablesBean();
            allSkillLablesBean.addListTags(listTags);
            oosTagJson.writeObject(allSkillLablesBean);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(oosTagJson);
            IOUtils.close(fosTagJson);
        }
    }


    int lineLeftRightMargin = CommonUtils.dip2px(11);
    //    int labelRightMargin = CommonUtils.dip2px(20);
    int labelRightMargin = CommonUtils.dip2px(18);
    int currentLabelsWidth = 0;

    AllSkillLablesBean.Tag_3[] tag3Arr;

    /**
     * 根据选择一级标签和二级标签，展示对应的三级标签
     */
    private void setThirdSkillLabels() {
        if (TextUtils.isEmpty(choosedMainLabel) || TextUtils.isEmpty(choosedSecondLabel)) {
            return;
        }

        llSkillLabelsLine = null;
        llSkillLabelsLine = createSkillLabelsLine();
        choosedThirdLabels.clear();
        currentLabelsWidth = 0;
        AllSkillLablesBean.Tag_2 tag_2 = tag2Arr[chooseTag2Index];
        Collection<AllSkillLablesBean.Tag_3> tag3Coll = tag_2.mapTag_3.values();
        tag3Arr = new AllSkillLablesBean.Tag_3[tag3Coll.size()];
        tag3Coll.toArray(tag3Arr);
        choosedThirdLabels.addAll(new ArrayList<>(Arrays.asList(tag3Arr)));
        mActivityChooseSkillBinding.llActivityChooseSkillLabels.removeAllViews();
        mActivityChooseSkillBinding.llActivityChooseSkillLabels.addView(llSkillLabelsLine);
        mActivityChooseSkillBinding.llActivityChooseSkillLabels.post(new Runnable() {
            @Override
            public void run() {
                if (choosedThirdLabels.size() == 0) {
                    return;
                }
                int labelsTotalWidth = mActivityChooseSkillBinding.llActivityChooseSkillLabels.getMeasuredWidth() - 2 * lineLeftRightMargin;
                for (AllSkillLablesBean.Tag_3 tag_3 : choosedThirdLabels) {
                    String labelName = tag_3.tag;
                    if (labelName.length() <= 3) {
                        labelName = " " + labelName + " ";
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, CommonUtils.dip2px(40));
                    params.rightMargin = labelRightMargin;
                    TextView tvSkillLabel = new TextView(CommonUtils.getContext());
                    tvSkillLabel.setTag(false);
                    tvSkillLabel.setText(labelName);
                    tvSkillLabel.setPadding(CommonUtils.dip2px(21), CommonUtils.dip2px(10), CommonUtils.dip2px(21), CommonUtils.dip2px(10));
                    tvSkillLabel.setBackgroundResource(R.drawable.label_unselected);
                    tvSkillLabel.setGravity(Gravity.CENTER);
                    tvSkillLabel.setTextColor(0xff31c5e4);
                    tvSkillLabel.setTextSize(13.5f);
                    tvSkillLabel.setLayoutParams(params);
                    tvSkillLabel.measure(0, 0);
                    setSkillLabelSelectedListener(tvSkillLabel, tag_3);
                    int labelWidth = tvSkillLabel.getMeasuredWidth() + labelRightMargin;
                    currentLabelsWidth = currentLabelsWidth + labelWidth;
                    if (currentLabelsWidth >= labelsTotalWidth) {
                        llSkillLabelsLine = createSkillLabelsLine();
                        mActivityChooseSkillBinding.llActivityChooseSkillLabels.addView(llSkillLabelsLine);
                        llSkillLabelsLine.addView(tvSkillLabel);
                        currentLabelsWidth = 0;
                        currentLabelsWidth = labelWidth;

                    } else {

                        llSkillLabelsLine.addView(tvSkillLabel);
                    }
                }
            }
        });
    }

    private LinearLayout createSkillLabelsLine() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(lineLeftRightMargin, CommonUtils.dip2px(24), lineLeftRightMargin, 0);
        LinearLayout llSkillLabelsLine = new LinearLayout(CommonUtils.getContext());
        llSkillLabelsLine.setLayoutParams(params);
        return llSkillLabelsLine;
    }

    int checkedThirdLabelsCount = 0;

    private void setSkillLabelSelectedListener(final TextView tvSkillLabel, final AllSkillLablesBean.Tag_3 tag_3) {
        tvSkillLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = (boolean) tvSkillLabel.getTag();
                if (isSelected) {
                    checkedThirdLabelsCount--;
                    if (checkedThirdLabelsCount < 0) {
                        checkedThirdLabelsCount = 0;
                    }
                    tvSkillLabel.setTextColor(0xff31c5e4);
                    tvSkillLabel.setBackgroundResource(R.drawable.label_unselected);
                    selectThirdLabels.remove(tag_3);
                } else {
                    MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.REGISTER_EXCLUSIVE_SKILLS_CHOOSE_LABEL);

                    if (checkedThirdLabelsCount >= 3) {
                        ToastUtils.shortToast("最多选择3个技能标签");
                        return;
                    }
                    checkedThirdLabelsCount++;
                    tvSkillLabel.setTextColor(0xffffffff);
                    tvSkillLabel.setBackgroundResource(R.drawable.label_selected);
                    selectThirdLabels.add(tag_3);
                }
                tvSkillLabel.setTag(!isSelected);
            }
        });
    }

    public void finishChooseSkill(View v) {
        if (selectThirdLabels.size() <= 0) {
            ToastUtils.shortToast("请选择技能标签");
            return;
        }

        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.REGISTER_EXCLUSIVE_SKILLS_CLICK_ENTER);

        final ArrayList<String> listTag = new ArrayList<String>();//这里存放三级标签
        final ArrayList<String> listTagName = new ArrayList<String>();//这里存放三级标签

        AllSkillLablesBean.Tag_1 tag_1 = tag1Arr[chooseTag1Index];
        String choosedTag1 = tag_1.tag;

        AllSkillLablesBean.Tag_2 tag_2 = tag2Arr[chooseTag2Index];
        String choosedTag2 = tag_2.tag;

        for (AllSkillLablesBean.Tag_3 tag_3 : selectThirdLabels) {
            String choosedTag3 = tag_3.f1 + "-" + tag_3.f2 + "-" + tag_3.tag;
            listTagName.add(tag_3.tag);
            listTag.add(choosedTag3);
        }
        //调用设置行业和方向的接口（一级和二级标签）
        LoginManager.loginSetIndustryAndDirection(new BaseProtocol.IResultExecutor<CommonResultBean>() {
            @Override
            public void execute(CommonResultBean dataBean) {
                //设置行业和方向成功，继续设置三级标签
                LoginManager.loginSetTag(new BaseProtocol.IResultExecutor<CommonResultBean>() {
                    @Override
                    public void execute(CommonResultBean dataBean) {
                        PreferenceUtil.save(CommonUtils.getContext(), ShareKey.USER_TAG_NAME, listTagName);
                        PreferenceUtil.save(CommonUtils.getContext(), ShareKey.USER_TAG, listTag);
                        Intent intentHomeActivity2 = new Intent(CommonUtils.getContext(), MainActivity.class);
                        mActivity.startActivity(intentHomeActivity2);
                        if (LoginActivity.activity != null) {
                            LoginActivity.activity.finish();
                            LoginActivity.activity = null;
                        }
                        if (PerfectInfoActivity.activity != null) {
                            PerfectInfoActivity.activity.finish();
                            PerfectInfoActivity.activity = null;
                        }
                        mActivity.finish();
                    }

                    @Override
                    public void executeResultError(String result) {
                        ToastUtils.shortToast("设置用户技能标签失败:" + result);
                    }
                }, listTag);
            }

            @Override
            public void executeResultError(String result) {
                ToastUtils.shortToast("设置行业和方向失败:" + result);
            }
        }, choosedTag1, choosedTag2);
    }

    private AllSkillLablesBean.Tag_1[] tag1Arr;
    private int chooseTag1Index = -1;

    private void initpicker1() {
        options1Items.clear();
        options1Items.addAll(new ArrayList<>(Arrays.asList(optionalMainLabels)));
        pvOptions1 = new OptionsPickerView(BaseDialog.newDialog(mActivityChooseSkillBinding.getRoot().getContext()));
        //三级联动效果
        pvOptions1.setPicker((ArrayList) options1Items);
        pvOptions1.setCyclic(false, false, false);
        //设置默认选中的三级项目
        pvOptions1.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        pvOptions1.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                clearCheckedThirdLabels();//选择了不同的一级标签，清空已选择的三级标签
                chooseTag1Index = options1;
                chooseTag2Index = 0;
                setChoosedMainLabel(options1Items.get(options1));
                initOptionItem2(options1);
                setChoosedSecondLabel(options2Items.get(chooseTag2Index));
                setThirdSkillLabels();

            }
        });
        pvOptions1.show();
    }

    private void initOptionItem2(int option) {
        AllSkillLablesBean.Tag_1 tag_1 = tag1Arr[option];
        Collection<AllSkillLablesBean.Tag_2> tag2Coll = tag_1.mapTag_2.values();
        tag2Arr = new AllSkillLablesBean.Tag_2[tag2Coll.size()];
        tag2Coll.toArray(tag2Arr);
        optionalSecondLabels = new String[tag2Arr.length];
        for (int i = 0; i < tag2Arr.length; i++) {
            optionalSecondLabels[i] = tag2Arr[i].tag;
        }
        options2Items.clear();
        options2Items.addAll(new ArrayList<>(Arrays.asList(optionalSecondLabels)));
    }

    private void initpicker2() {
        pvOptions2 = new OptionsPickerView(BaseDialog.newDialog(mActivityChooseSkillBinding.getRoot().getContext()));
        //三级联动效果
        pvOptions2.setPicker((ArrayList) options2Items);
        pvOptions2.setCyclic(false, false, false);
        //设置默认选中的三级项目
        pvOptions2.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        pvOptions2.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (options1 != chooseTag2Index) {
                    clearCheckedThirdLabels();//选择了不同的二级标签，清空已选择的三级标签
                    chooseTag2Index = options1;
                    setChoosedSecondLabel(options2Items.get(options1));
                    setThirdSkillLabels();
                }
            }
        });
        pvOptions2.show();
    }

    /**
     * 选择一级标签（选择行业）(打开一级标签列表操作)
     *
     * @param v
     */
    public void chooseMainSkillLabel(View v) {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.REGISTER_EXCLUSIVE_SKILLS_CHOOSE_INDUSTRY);
        isChooseMainLabel = true;
        Collection<AllSkillLablesBean.Tag_1> tag1Coll = allSkillLablesBean.mapTag_1.values();
        tag1Arr = new AllSkillLablesBean.Tag_1[tag1Coll.size()];
        tag1Coll.toArray(tag1Arr);
        optionalMainLabels = new String[tag1Arr.length];
        for (int i = 0; i < tag1Arr.length; i++) {
            optionalMainLabels[i] = tag1Arr[i].tag;
        }
        initpicker1();

    }

    AllSkillLablesBean.Tag_2[] tag2Arr;
    private int chooseTag2Index = -1;

    /**
     * 选择二级标签（选择岗位）(打开二级标签列表操作)
     *
     * @param v
     */
    public void chooseSecondSkillLabel(View v) {
        MobclickAgent.onEvent(CommonUtils.getContext(), CustomEventAnalyticsUtils.EventID.REGISTER_EXCLUSIVE_SKILLS_CHOOSE_STATION);
        initpicker2();
    }


    /**
     * 当重新选择了不同的一级或者二级标签的时候，需要清空已选的三级标签（因为只能选择一个类别下的三级标签）
     */
    private void clearCheckedThirdLabels() {
        checkedThirdLabelsCount = 0;
        choosedThirdLabels.clear();
        selectThirdLabels.clear();
    }

    private int chooseSkillLayerVisibility = View.INVISIBLE;//选择行业（一级标签）或岗位（二级标签）的浮层是否显示，默认为隐藏
    private String choosedMainLabel;
    private String choosedSecondLabel;

    @Bindable
    public int getChooseSkillLayerVisibility() {
        return chooseSkillLayerVisibility;
    }

    public void setChooseSkillLayerVisibility(int chooseSkillLayerVisibility) {
        this.chooseSkillLayerVisibility = chooseSkillLayerVisibility;
        notifyPropertyChanged(BR.chooseSkillLayerVisibility);
    }

    @Bindable
    public String getChoosedMainLabel() {
        return choosedMainLabel;
    }

    public void setChoosedMainLabel(String choosedMainLabel) {
        this.choosedMainLabel = choosedMainLabel;
        notifyPropertyChanged(BR.choosedMainLabel);
    }

    @Bindable
    public String getChoosedSecondLabel() {
        return choosedSecondLabel;
    }

    public void setChoosedSecondLabel(String choosedSecondLabel) {
        this.choosedSecondLabel = choosedSecondLabel;
        notifyPropertyChanged(BR.choosedSecondLabel);
    }
}
