package com.github.hanlyjiang.lib.common.di.module.mvp;

import com.github.hanlyjiang.lib.common.di.base.MvpInjector;
import com.github.hanlyjiang.lib.common.di.instance.TestDiMvpActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

// TODO: 暂未使用
@Module(
        subcomponents = MvpModule_TestDiMvpActivity.TestDiMvpActivitySubcomponent.class
)
public abstract class MvpModule_TestDiMvpActivity {
    private MvpModule_TestDiMvpActivity() {
    }

    @Binds
    @IntoMap
    @ClassKey(TestDiMvpActivity.class)
    abstract MvpInjector.Factory<?> bindAndroidInjectorFactory(
            TestDiMvpActivitySubcomponent.Factory builder);

    @Subcomponent
    public interface TestDiMvpActivitySubcomponent extends MvpInjector<TestDiMvpActivity> {
        @Subcomponent.Factory
        interface Factory extends MvpInjector.Factory<TestDiMvpActivity> {
        }
    }
}
