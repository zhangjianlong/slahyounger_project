package com.slash.youth.v2.di.components;

import dagger.Component;


import com.slash.youth.v2.di.modules.ActivityModule;
import com.slash.youth.v2.di.modules.SearchModule;
import com.slash.youth.v2.feature.search.SearchActivity;
import com.core.op.lib.di.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, SearchModule.class})
public interface SearchComponent extends ActivityComponent {
    void inject(SearchActivity activity);
}