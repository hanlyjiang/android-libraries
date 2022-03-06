package com.github.hanlyjiang.lib.common.di.framework.module;

import android.app.Application;
import android.content.Context;
import com.github.hanlyjiang.lib.common.di.test.TestDiMvpActivity;
import com.github.hanlyjiang.lib.common.di.test.TestDiMvpActivityPresenter;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

/**
 * @author hanlyjiang on 2022/3/6-9:16 PM
 * @version 1.0
 */
@Module(
        subcomponents = TestDiMvpActivityModule.MvpComponent.class
)
public interface TestDiMvpActivityModule {

    @Module
    class MvpModule {
        TestDiMvpActivityPresenter.TestDiView testDiView;

        public MvpModule(TestDiMvpActivityPresenter.TestDiView testDiView) {
            this.testDiView = testDiView;
        }

        @Provides
        public TestDiMvpActivityPresenter.TestDiView providerView() {
            return testDiView;
        }

    }

    @Subcomponent(modules = MvpModule.class)
    interface MvpComponent {

        void inject(TestDiMvpActivity activity);

        @Subcomponent.Builder
        interface Builder {
            MvpComponent build();

            Builder module(MvpModule mvpModule);
        }
    }
}
