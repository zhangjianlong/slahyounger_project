package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.LabelModule;
import com.slash.youth.v2.feature.label.LabelActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, LabelModule.class})
public interface LabelComponent extends ActivityComponent {
    void inject(LabelActivity activity);
}