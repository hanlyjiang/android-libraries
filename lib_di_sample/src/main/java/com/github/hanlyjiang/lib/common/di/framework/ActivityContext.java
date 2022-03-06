package com.github.hanlyjiang.lib.common.di.framework;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * 标记ActivityContext，与 Context 进行区分
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ActivityContext {
}
