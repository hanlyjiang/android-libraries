package com.github.hanlyjiang.lib.common.di.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.hanlyjiang.lib.common.di.BR;
import com.github.hanlyjiang.lib.common.di.databinding.ActivityFragmentEmptyInfoBinding;
import com.github.hanlyjiang.lib.common.di.framework.mvp.MvpInjectable;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * Mvp Fragment DI Test
 *
 * @author hanlyjiang on 2022/3/5-11:21 PM
 * @version 1.0
 */
public class TestDiMvpFragment extends Fragment implements TestDiMvpFragmentPresenter.View, MvpInjectable {

    @Inject
    TestSingleton testSingleton;
    @Inject
    TestObj testObj;

    @Inject
    TestDiMvpFragmentPresenter presenter;
    private com.github.hanlyjiang.lib.common.di.databinding.ActivityFragmentEmptyInfoBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentEmptyInfoBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean injectSuccess = Test.assertInject(this, testSingleton, testObj);
        binding.setVariable(BR.info, injectSuccess ? "inject success" : "inject failed!");
        presenter.assertInject();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

}
