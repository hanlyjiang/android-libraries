package com.github.hanlyjiang.lib.common.di.framework.mvp;

import com.github.hanlyjiang.lib.common.di.framework.module.ActivityModule;
import com.github.hanlyjiang.lib.common.di.framework.module.FragmentModule;
import com.github.hanlyjiang.lib.common.di.framework.module.ServiceModule;

/**
 * Activity 或 Fragment 或  Service 实现此接口以标识类是否是可以被注入的，同时也需要在
 * {@link ActivityModule  ActivityModule}
 * 或 {@link FragmentModule FragmentModule}
 * 或 {@link ServiceModule ServiceModule}
 * 中注册（提供一个 @@ContributesAndroidInjector 注解，并且返回对应类型组件）
 *
 * @author hanlyjiang on 2018/6/17-13:19.
 * @version 1.0
 */
public interface MvpInjectable {
}
