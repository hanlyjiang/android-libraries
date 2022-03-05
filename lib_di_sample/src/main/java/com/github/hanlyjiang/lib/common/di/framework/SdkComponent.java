package com.github.hanlyjiang.lib.common.di.framework;


import android.app.Application;
import com.github.hanlyjiang.lib.common.di.framework.module.*;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

import javax.inject.Singleton;

/**
 * 库模块中的Root Component
 */
@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                ActivityModule.class,
                FragmentModule.class,
                ServiceModule.class,
                NormalObjModule.class,
                // MVP 组件
                MvpModule.class
        }
)
public interface SdkComponent {

    void inject(SdkContainer sdkContainer);

    MvpModule.MvpComponent.Builder mvpComponentBuilder();

    @Component.Builder
    interface Builder {

        /**
         * 用于提供 Application 对象
         *
         * @param application Application
         * @return Builder
         */
        @BindsInstance
        Builder application(Application application);


        SdkComponent build();
    }

}
