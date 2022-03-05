package com.github.hanlyjiang.lib.common.di.framework.module;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * App 级别的对象提供
 * @author hanlyjiang on 2022/3/5-11:13 PM
 * @version 1.0
 */
@Module
public abstract class AppModule {

    /**
     * Provider Context for Object Graph
     * @param application Application
     * @return Context
     */
    @Singleton
    @Provides
    static Context providerContext(Application application){
        return application.getApplicationContext();
    }

}
