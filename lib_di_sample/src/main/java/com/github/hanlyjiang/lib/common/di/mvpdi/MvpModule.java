package com.github.hanlyjiang.lib.common.di.mvpdi;

import android.app.Activity;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.hanlyjiang.lib.common.di.MvpContainer;
import com.github.hanlyjiang.lib.common.di.instance.TestDiMvpActivity;
import com.github.hanlyjiang.lib.common.di.instance.TestDiPresenter;
import com.github.hanlyjiang.lib.common.di.scope.ActivityScope;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {
        MvpModule.MvpComponent.class,
})
public interface MvpModule {
    @Module
    abstract class MvpProvider {

        @ActivityScope
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


    @ActivityScope
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
