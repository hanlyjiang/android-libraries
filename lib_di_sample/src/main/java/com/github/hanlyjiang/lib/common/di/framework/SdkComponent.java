package com.github.hanlyjiang.lib.common.di.framework;


import android.app.Application;
import android.content.Context;
import com.github.hanlyjiang.lib.common.di.framework.module.*;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

import javax.inject.Singleton;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                NormalObjModule.class,
                ActivityModule.class,
                FragmentModule.class,
                ServiceModule.class,
                MvpModule.class
        }
)
public interface SdkComponent {

    void inject(SdkContainer sdkContainer);

    MvpModule.MvpComponent.Builder mvpComponentBuilder();

    @Component.Builder
    interface Builder {

        /**
         * 用于提供 Context 类对象（使用 applicationContext）
         *
         * @param appContext applicationContext
         * @return
         */
        @BindsInstance
        Builder context(Context appContext);

        /**
         * 用于提供 Application 对象
         *
         * @param application
         * @return
         */
        @BindsInstance
        Builder application(Application application);


        SdkComponent build();
    }

}
