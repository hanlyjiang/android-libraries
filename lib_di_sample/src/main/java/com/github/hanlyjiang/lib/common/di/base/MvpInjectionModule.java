/*
 * Copyright (C) 2016 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.hanlyjiang.lib.common.di.base;

import dagger.Module;
import dagger.internal.Beta;
import dagger.multibindings.Multibinds;

import java.util.Map;
// TODO: 暂未使用

/**
 * Contains bindings to ensure the usability of {@code dagger.android} framework classes. This
 * module should be installed in the component that is used to inject the {@link
 * android.app.Application} class.
 */
@Beta
@Module
public abstract class MvpInjectionModule {
    @Multibinds
    abstract Map<Class<?>, MvpInjector.Factory<?>> classKeyedInjectorFactories();

    @Multibinds
    abstract Map<String, MvpInjector.Factory<?>> stringKeyedInjectorFactories();

    private MvpInjectionModule() {
    }
}
