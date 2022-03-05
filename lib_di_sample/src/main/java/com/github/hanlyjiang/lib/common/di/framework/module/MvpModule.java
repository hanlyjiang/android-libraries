package com.github.hanlyjiang.lib.common.di.framework.module;

import android.app.Activity;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.hanlyjiang.lib.common.di.framework.mvp.AndroidProvider;
import com.github.hanlyjiang.lib.common.di.framework.mvp.MvpContainer;
import com.github.hanlyjiang.lib.common.di.framework.scope.MvpScope;
import com.github.hanlyjiang.lib.common.di.test.TestDiMvpActivity;
import com.github.hanlyjiang.lib.common.di.test.TestDiPresenter;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

/**
* MVP 组件注册
* @author hanlyjiang on 2022/3/5-11:16 PM
* @version 1.0
*/
@Module(subcomponents = {
        MvpModule.MvpComponent.class,
})
public interface MvpModule {
    @Module
    abstract class MvpProvider {

        @MvpScope
        @Provides
        static TestDiPresenter.TestDiView bindTestDiView(@Nullable AndroidProvider<Activity> activityProvider) {
            if (activityProvider != null) {
                return (TestDiPresenter.TestDiView) activityProvider.get();
            } else {
                throw new NullPointerException("Please provider activity provider when build component!");
            }
        }

        /**
         * 提供 TestDiMvpActivity 的注入接口
         * @return TestDiMvpActivity
         */
        @ContributesAndroidInjector
        abstract TestDiMvpActivity testDiMvpActivity();

    }


    @MvpScope
    @Subcomponent(modules = {
            AndroidInjectionModule.class,
            MvpProvider.class,
    })
    interface MvpComponent {

        MvpContainer inject(MvpContainer mvpContainer);

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Builder activityProvider(@Nullable AndroidProvider<Activity> activityProvider);

            @BindsInstance
            Builder fragmentProvider(@Nullable AndroidProvider<Fragment> fragmentProvider);

            MvpComponent build();
        }
    }


}
