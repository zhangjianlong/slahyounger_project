package com.slash.youth.v2.feature.edit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.core.op.lib.weight.imgselector.MultiImageSelector;
import com.slash.youth.R;
import com.slash.youth.databinding.ActPersonaleditBinding;
import com.slash.youth.v2.base.BackActivity;
import com.slash.youth.v2.base.BaseActivity;
import com.slash.youth.v2.di.components.DaggerPersonalEditComponent;
import com.slash.youth.v2.di.components.PersonalEditComponent;
import com.slash.youth.v2.di.modules.PersonalEditModule;

import com.core.op.lib.utils.inject.AfterViews;
import com.core.op.lib.utils.inject.BeforeViews;
import com.core.op.lib.utils.inject.RootView;

import java.util.ArrayList;

import javax.inject.Inject;

@RootView(R.layout.act_personaledit)
public final class PersonalEditActivity extends BackActivity<PersonalEditViewModel, ActPersonaleditBinding> {

    PersonalEditComponent component;

    public final static void instance(Context context) {
        context.startActivity(new Intent(context, PersonalEditActivity.class));
    }

    @BeforeViews
    void beforViews() {
        component = DaggerPersonalEditComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .personalEditModule(new PersonalEditModule())
                .build();
        component.inject(this);
    }

    @AfterViews
    void afterViews() {
        binding.toolbar.toolbar.inflateMenu(R.menu.menu_userinfo_save);
        binding.toolbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (!viewModel.checkData()) {
                    return true;
                }
                viewModel.saveInfo();
                viewModel.saveCompany();
                viewModel.saveSex();
                viewModel.saveLocation();
                viewModel.saveNeedTag();
                viewModel.saveProTag();
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MultiImageSelector.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> paths = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (paths != null && paths.size() > 0)
                    viewModel.uploadImage(paths.get(0));
            }
        }
    }

    @Override
    protected Toolbar setToolBar() {
        return binding.toolbar.toolbar;
    }
}
