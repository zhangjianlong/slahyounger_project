package com.slash.youth.v2.feature.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.slash.youth.R;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.databinding.ActSearchBinding;
import com.slash.youth.v2.di.components.DaggerSearchComponent;
import com.slash.youth.v2.di.components.SearchComponent;
import com.slash.youth.v2.di.modules.SearchModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import javax.inject.Inject;

import static com.slash.youth.R.id.action0;
import static com.slash.youth.R.id.serachview;

@RootView(R.layout.act_search)
public final class SearchActivity extends BaseActivity<SearchViewModel, ActSearchBinding> {

    SearchComponent component;

    public final static void instance(Context context) {
        instance(context, null);
    }

    public final static void instance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SearchActivity.class);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }

    @BeforeViews
    void beforViews() {
        component = DaggerSearchComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .searchModule(new SearchModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        binding.toolbar.inflateMenu(R.menu.menu_cancle);
        ImageView searchButton = (ImageView) binding.serachview.findViewById(R.id.search_mag_icon);
        searchButton.setImageDrawable(CommonUtils.getContext().getResources().getDrawable(R.mipmap.search_zoom_icon));
        binding.serachview.setIconifiedByDefault(false);
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) binding.serachview.findViewById(R.id.search_src_text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//14sp
        textView.setBackground(null);
        textView.setBackgroundColor(CommonUtils.getContext().getResources().getColor(R.color.cmbkb_transparent));
        textView.setTextColor(CommonUtils.getContext().getResources().getColor(R.color.app_text_black333));
        ImageView delButton = (ImageView) binding.serachview.findViewById(R.id.search_close_btn);
        delButton.setImageDrawable(CommonUtils.getContext().getResources().getDrawable(R.mipmap.search_del_icon));
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return false;
            }
        });
    }

    private void initView() {

    }
}
