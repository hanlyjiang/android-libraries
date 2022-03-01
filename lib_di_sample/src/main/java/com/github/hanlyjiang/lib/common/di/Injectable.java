package com.github.hanlyjiang.lib.common.di;

/**
 * Activity 或 Fragment 或  Service 实现此接口以标识类是否是可以被注入的，同时也需要在
 * {@link com.github.hanlyjiang.lib.common.di.module.ActivityModule  ActivityModule}
 * 或 {@link com.github.hanlyjiang.lib.common.di.module.FragmentModule FragmentModule}
 * 或 {@link com.github.hanlyjiang.lib.common.di.module.ServiceModule ServiceModule}
 * 中注册（提供一个 @@ContributesAndroidInjector 注解，并且返回对应类型组件）
 *
 * @author hanlyjiang on 2018/6/17-13:19.
 * @version 1.0
 */
public interface Injectable {
}
