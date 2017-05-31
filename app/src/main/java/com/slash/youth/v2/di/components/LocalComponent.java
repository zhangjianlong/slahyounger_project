package com.slash.youth.v2.di.components;

import com.core.op.lib.di.PerActivity;
import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.LabelModule;
import com.slash.youth.v2.di.modules.LocalModule;
import com.slash.youth.v2.feature.label.LabelActivity;
import com.slash.youth.v2.feature.local.LocalActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, LocalModule.class})
public interface LocalComponent extends ActivityComponent {
    void inject(LocalActivity activity);
}