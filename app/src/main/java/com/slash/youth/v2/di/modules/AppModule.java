package com.slash.youth.v2.di.modules;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slash.youth.data.ApiClient;
import com.slash.youth.data.UrlRoot;
import com.slash.youth.data.api.ApiOption;
import com.slash.youth.data.executor.JobExecutor;
import com.slash.youth.data.repository.LoginRepositoryImp;
import com.slash.youth.data.repository.MainRepositoryImp;
import com.slash.youth.data.repository.MessageRepositoryImp;
import com.slash.youth.data.repository.TaskRepositoryImp;
import com.slash.youth.domain.executor.PostExecutionThread;
import com.slash.youth.domain.executor.ThreadExecutor;
import com.slash.youth.domain.repository.LoginRepository;
import com.slash.youth.domain.repository.MainRepository;
import com.slash.youth.domain.repository.MessageRepository;
import com.slash.youth.domain.repository.TaskRepository;
import com.slash.youth.global.SlashApplication;
import com.slash.youth.v2.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 16/3/22.
 */
@Module
public class AppModule {
    private SlashApplication application;

    public AppModule(SlashApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    SlashApplication provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    ApiClient provideApiClient() {
        return ApiOption.Builder.instance(application).url(UrlRoot.HOST).build().create(ApiClient.class);
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    MainRepository mainRepository(MainRepositoryImp mainRepository) {
        return mainRepository;
    }

    @Provides
    @Singleton
    LoginRepository loginRepository(LoginRepositoryImp loginRepository) {
        return loginRepository;
    }

    @Provides
    @Singleton
    TaskRepository taskRepository(TaskRepositoryImp taskRepositoryImp) {
        return taskRepositoryImp;
    }

    @Provides
    @Singleton
    MessageRepository messageRepository(MessageRepositoryImp messageRepository) {
        return messageRepository;
    }

    @Provides
    @Singleton
    Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

}
