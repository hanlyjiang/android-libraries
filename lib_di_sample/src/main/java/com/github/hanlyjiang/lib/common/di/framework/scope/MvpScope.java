package com.github.hanlyjiang.lib.common.di.framework.scope;

import javax.inject.Scope;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * MVP 组件的生命周期 Scope 标记
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface MvpScope {
}


