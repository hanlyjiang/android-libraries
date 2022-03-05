package com.github.hanlyjiang.lib.common.di.framework.mvp;

import javax.inject.Provider;

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
