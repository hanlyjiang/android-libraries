package com.github.hanlyjiang.lib.common.di.instance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.hanlyjiang.lib.common.di.Injectable;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class TestDiFragment extends Fragment implements Injectable {

    @Inject
    TestSingleton testSingleton;
    @Inject
    TestObj testObj;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.d(getClass().getSimpleName(), "testSingleton == null: " + (testSingleton == null));
        Log.d(getClass().getSimpleName(), "testObj == null: " + (testObj == null));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
