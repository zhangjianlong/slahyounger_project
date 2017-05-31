package com.slash.youth.v2.feature.label;


import android.databinding.ObservableField;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;
import com.core.op.bindingadapter.common.ItemViewSelector;
import com.core.op.lib.base.BAViewModel;
import com.core.op.lib.command.ReplyCommand;
import com.core.op.lib.di.PerActivity;
import com.core.op.lib.messenger.Messenger;
import com.slash.youth.BR;
import com.slash.youth.R;
import com.slash.youth.databinding.ActLabelBinding;
import com.slash.youth.domain.bean.LabelBean;
import com.slash.youth.domain.interactor.task.CreateLableUseCase;
import com.slash.youth.domain.interactor.task.CustomLableUseCase;
import com.slash.youth.domain.interactor.task.DeleteLableUseCase;
import com.slash.youth.domain.interactor.task.LableUseCase;
import com.slash.youth.v2.feature.dialog.label.LabelDialog;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

import static android.R.attr.data;
import static com.slash.youth.ui.activity.CityLocationActivity.map;

@PerActivity
public class LabelViewModel extends BAViewModel<ActLabelBinding> {

    public String title;
    LabelDialog dialog;

    public final ItemViewSelector<LabelSItemViewModel> secondItemView = new BaseItemViewSelector<LabelSItemViewModel>() {
        @Override
        public void select(ItemView itemView, int position, LabelSItemViewModel item) {
            if(item.isCustom){
                itemView.set(BR.viewModel, R.layout.item_label_new);
            }else{
                itemView.set(BR.viewModel, R.layout.item_label_hot);
            }
        }
    };
    public final List<LabelSItemViewModel> secondItemViewModels = new ArrayList<>();

    public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.item_label);
    public final List<LabelItemViewModel> itemViewModels = new ArrayList<>();

    public final ItemView flowItemView = ItemView.of(BR.viewModel, R.layout.item_flow);
    public final List<LabelFItemViewModel> flowItemViewModels = new ArrayList<>();

    public final ReplyCommand customClick = new ReplyCommand(()->{
        if(!dialog.isShowing()){
            dialog.show();
        }
    });

    public ObservableField<SpannableStringBuilder> selectedText = new ObservableField<>();

    LableUseCase lableUseCase;

    CustomLableUseCase customLableUseCase;

    CreateLableUseCase createLableUseCase;
    DeleteLableUseCase deleteLableUseCase;
    List<LabelBean> lableBeens = new ArrayList<>();

    LabelItemViewModel selectedModel;
    List<LabelBean> selecteds = new ArrayList<>();
    int index = 0;

    @Inject
    public LabelViewModel(RxAppCompatActivity activity,
                          LableUseCase lableUseCase,
                          CustomLableUseCase customLableUseCase,
                          CreateLableUseCase createLableUseCase,
                          DeleteLableUseCase deleteLableUseCase,
                          LabelDialog dialog) {
        super(activity);
        this.lableUseCase = lableUseCase;
        this.customLableUseCase = customLableUseCase;
        this.createLableUseCase = createLableUseCase;
        this.deleteLableUseCase = deleteLableUseCase;
        this.dialog = dialog;
        title = activity.getString(R.string.app_label_title);
        updataText(0);
    }

    @Override
    public void afterViews() {
        Messenger.getDefault().register(this,MessageKey.LABEL_SELECT_STAIR, Integer.class, a -> {
            upadataView((int)a);
        });

        Messenger.getDefault().register(this,MessageKey.LABEL_SELECT_SCOND, Bundle.class, a -> {
            LabelBean labelBean = secondItemViewModels.get(a.getInt("index")).data;
            if(a.getBoolean("checked")){
                if(selecteds.size()<3 && !selecteds.contains(labelBean)){
                    selecteds.add(labelBean);
                }
                updataText(selecteds.size());
                updataFlow();
            }else{
                List<LabelBean> list = new  ArrayList<>();
                list.addAll(selecteds);
                index = 0;
                selecteds.clear();
                Observable.from(list)
                        .filter(b->{
                            return b.getF1() != labelBean.getF1();
                        })
                        .subscribe(data->{
                            selecteds.add(data);
                        },error->{

                        },()->{
                            updataText(selecteds.size());
                            updataFlow();
                        });

            }
        });

        Messenger.getDefault().register(this,MessageKey.LABEL_FLOW_DELETE, Integer.class, a -> {
            selecteds.remove((selecteds.size()-1)-a);
            updataSecond();
            updataText(selecteds.size());
            updataFlow();
        });

        Messenger.getDefault().register(this,MessageKey.LABEL_NEW, String.class, a -> {
            Map<String ,String> map = new HashMap<String, String>();
            map.put("f1",selectedModel.data.getId()+"");
            map.put("tags",a);
            createLableUseCase.execute().compose(activity.bindToLifecycle())
                    .subscribe(data->{
                        loadData();
                    });
        });

        Messenger.getDefault().register(this,MessageKey.LABEL_DELETE, Integer.class, a -> {

            Map<String ,String> map = new HashMap<String, String>();
            map.put("f1",selectedModel.data.getId()+"");
            map.put("tags",lableBeens.get((int)a).getTag());
            deleteLableUseCase.execute().compose(activity.bindToLifecycle())
                    .subscribe(data->{
                        lableBeens.remove((int)a);
                        updataSecond();
                    });
        });

        loadData();
    }

    private void loadData() {
        lableUseCase.execute().compose(activity.bindToLifecycle())
                .doOnNext(d->{
                    lableBeens.addAll(d);
                })
                .flatMap(a->{
                    return customLableUseCase.execute();
                })
                .subscribe(data -> {
                    lableBeens.addAll(data);
                }, error -> {
                    if(lableBeens.size()>0){
                        upadataView(0);
                    }
                }, () -> {
                    upadataView(0);
                });
    }

    private void upadataView(int selected){
        itemViewModels.clear();
        index = 0;
        getLableBeens(0)
                .subscribe(data -> {
                    if (index == selected) {
                        selectedModel = new LabelItemViewModel(activity, data, index, true);
                        itemViewModels.add(selectedModel);
                    } else {
                        itemViewModels.add(new LabelItemViewModel(activity, data, index, false));
                    }
                    index++;
                }, error -> {
                }, () -> {
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                    updataSecond();
                });
    }
    private void updataSecond(){
        index = 0;
        secondItemViewModels.clear();
        getLableBeens(selectedModel.data.getId())
                .subscribe(data -> {
                    boolean isChecked = false;
                    for (LabelBean bean:selecteds){
                        if(bean.getF1() == data.getF1()){
                            isChecked =true;
                            break;
                        }
                    }
                    secondItemViewModels.add(new LabelSItemViewModel(activity, data, index, isChecked));
                    index++;
                },error->{
                },()->{
                    binding.secondRecyclerView.getAdapter().notifyDataSetChanged();
                });
    }

    private void updataText(int selecteds){
        SpannableStringBuilder builder = new SpannableStringBuilder(String.format(activity.getString(R.string.app_label_selected),selecteds,3-selecteds));
        ForegroundColorSpan span = new ForegroundColorSpan(activity.getResources().getColor(R.color.app_theme_colorPrimary));
        builder.setSpan(span, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span, 11, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        selectedText.set(builder);
    }

    private void updataFlow(){
        flowItemViewModels.clear();
        index = 0;
        Observable.from(selecteds)
                .subscribe(data->{
                    flowItemViewModels.add(new LabelFItemViewModel(activity,data,index));
                },error->{
                },()->{
                    binding.flowlayout.notifyChange();
                });
    }

    private Observable<LabelBean> getLableBeens(int id) {
        return Observable.from(lableBeens)
                .filter(data -> {
                    return data.getF1() == id;
                });
    }
}