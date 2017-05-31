//package com.slash.youth.v2;
//
//import android.app.Application;
//
//import com.core.op.lib.AppException;
//import com.facebook.stetho.Stetho;
//import com.orhanobut.logger.LogLevel;
//import com.orhanobut.logger.Logger;
//import com.slash.youth.BuildConfig;
//import com.slash.youth.v2.di.components.AppComponent;
//import com.slash.youth.v2.di.components.DaggerAppComponent;
//import com.slash.youth.v2.di.modules.AppModule;
//
//import timber.log.Timber;
//
///**
// * @author op
// * @version 1.0
// * @description
// * @createDate 2016/8/10
// */
//public class MainApplication extends Application {
//
//    private AppComponent appComponent;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Thread.setDefaultUncaughtExceptionHandler(AppException
//                .getAppExceptionHandler(this));
//
////        LeakCanary.install(this);
//
//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//            Timber.tag("YTP");
//        }
//        Stetho.initializeWithDefaults(this);
//
//        Logger.init("YTP")                 // default PRETTYLOGGER or use just init()
//                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
//                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
//                .methodOffset(2);
//
//        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
//
//    }
//
//    public AppComponent getAppComponent() {
//        return appComponent;
//    }
//}
