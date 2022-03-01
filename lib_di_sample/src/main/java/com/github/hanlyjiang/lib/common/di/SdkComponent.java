package com.github.hanlyjiang.lib.common.di;


import com.github.hanlyjiang.lib.common.di.module.ActivityModule;
import com.github.hanlyjiang.lib.common.di.module.FragmentModule;
import com.github.hanlyjiang.lib.common.di.module.NormalObjModule;
import com.github.hanlyjiang.lib.common.di.module.ServiceModule;
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
                ServiceModule.class
        }
)
public interface SdkComponent {

    void inject(SdkContainer sdkContainer);

    @Component.Builder
    interface Builder {

//        /**
//         * Application 对象
//         *
//         * @param application Application
//         * @return Builder
//         */
//        @BindsInstance
//        Builder application(Application application);

        SdkComponent build();
    }

}
