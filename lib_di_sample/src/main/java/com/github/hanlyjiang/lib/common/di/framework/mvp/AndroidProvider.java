package com.github.hanlyjiang.lib.common.di.framework.mvp;

import javax.inject.Provider;

/**
 * Android 组件提供等，主要用于延迟提供
 * @param <T> 组件类型（Activity，Fragment）等
 */
public class AndroidProvider<T> implements Provider<T> {

    private final T item;

    public AndroidProvider(T item) {
        this.item = item;
    }

    @Override
    public T get() {
        return item;
    }

}
