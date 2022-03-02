package com.github.hanlyjiang.lib.common.di.base;

import dagger.BindsInstance;
import dagger.android.AndroidInjection;
import dagger.android.ContributesAndroidInjector;
import dagger.android.DispatchingAndroidInjector;
// TODO: 暂未使用
/**
 * Performs members-injection for a concrete subtype of a <a
 * href="https://developer.android.com/guide/components/">core Android type</a> (e.g., {@link
 * android.app.Activity} or {@link android.app.Fragment}).
 *
 * <p>Commonly implemented by {@link dagger.Subcomponent}-annotated types whose {@link
 * dagger.Subcomponent.Factory} extends {@link MvpInjector.Factory}.
 *
 * @param <T> a concrete subtype of a core Android type
 * @see AndroidInjection
 * @see DispatchingAndroidInjector
 * @see ContributesAndroidInjector
 */
public interface MvpInjector<T> {

    /**
     * Injects the members of {@code instance}.
     */
    void inject(T instance);

    /**
     * Creates {@link MvpInjector}s for a concrete subtype of a core Android type.
     *
     * @param <T> the concrete type to be injected
     */
    interface Factory<T> {
        /**
         * Creates an {@link MvpInjector} for {@code instance}. This should be the same instance
         * that will be passed to {@link #inject(Object)}.
         */
        MvpInjector<T> create(@BindsInstance T instance);
    }

    /**
     * An adapter that lets the common {@link dagger.Subcomponent.Builder} pattern implement {@link
     * MvpInjector.Factory}.
     *
     * @param <T> the concrete type to be injected
     * @deprecated Prefer {@link MvpInjector.Factory} now that components can have {@link dagger.Component.Factory
     * factories} instead of builders
     */
    @Deprecated
    abstract class Builder<T> implements MvpInjector.Factory<T> {
        @Override
        public final MvpInjector<T> create(T instance) {
            seedInstance(instance);
            return build();
        }

        /**
         * Provides {@code instance} to be used in the binding graph of the built {@link
         * MvpInjector}. By default, this is used as a {@link BindsInstance} method, but it may be
         * overridden to provide any modules which need a reference to the activity.
         *
         * <p>This should be the same instance that will be passed to {@link #inject(Object)}.
         */
        @BindsInstance
        public abstract void seedInstance(T instance);

        /**
         * Returns a newly-constructed {@link MvpInjector}.
         */
        public abstract MvpInjector<T> build();
    }
}

