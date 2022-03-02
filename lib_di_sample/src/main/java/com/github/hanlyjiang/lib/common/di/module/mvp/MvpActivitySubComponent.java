package com.github.hanlyjiang.lib.common.di.module.mvp;


import com.github.hanlyjiang.lib.common.di.instance.TestDiMvpActivity;
import com.github.hanlyjiang.lib.common.di.instance.TestDiPresenter;
import com.github.hanlyjiang.lib.common.di.scope.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        TestDiMvpActivityModule.class
})
public interface MvpActivitySubComponent {

    TestDiMvpActivity inject(TestDiMvpActivity activity);

    TestDiPresenter inject(TestDiPresenter presenter);
}
