package com.github.hanlyjiang.lib.common.di.test.mvp;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleRegistry;
import com.github.hanlyjiang.lib.common.di.framework.mvp.MvpInjectable;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MvpActivity<P extends BasePresenter, V extends BaseView> extends AppCompatActivity implements MvpInjectable {
    private List<LifecycleObserver> mLifecycleObservers = new CopyOnWriteArrayList<>();

    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectPresenter();
        if (mPresenter != null)
            addLifecycleObserver(mPresenter);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    /**
     * 注入Presenter
     */
    protected void injectPresenter() {

    }

    /**
     * 是否自动绑定view
     *
     * @return
     */
    protected boolean isAutoBindView() {
        return true;
    }

    /**
     * 添加生命周期观察
     *
     * @param lifecycleObserver
     */
    protected void addLifecycleObserver(LifecycleObserver lifecycleObserver) {
        if (lifecycleObserver == null)
            return;
        if (!mLifecycleObservers.contains(lifecycleObserver))
            mLifecycleObservers.add(lifecycleObserver);
        getLifecycle().addObserver(lifecycleObserver);
    }

    /**
     * 移除生命周期观察
     *
     * @param lifecycleObserver
     */
    protected void removeLifecycleObserver(LifecycleObserver lifecycleObserver) {
        if (lifecycleObserver == null)
            return;
        if (mLifecycleObservers.contains(lifecycleObserver)) {
            mLifecycleObservers.remove(lifecycleObserver);
        }
        getLifecycle().removeObserver(lifecycleObserver);
    }

    /**
     * 移除所有生命周期观察
     */
    protected void removeAllLifecycleObserver() {
        if (mLifecycleObservers.size() > 0) {
            for (LifecycleObserver lifecycleObserver : mLifecycleObservers) {
                getLifecycle().removeObserver(lifecycleObserver);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Lifecycle lifecycle = getLifecycle();
        if (lifecycle instanceof LifecycleRegistry) {
            LifecycleRegistry lifecycleRegistry = (LifecycleRegistry) lifecycle;
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        }
        removeAllLifecycleObserver();
    }
}
