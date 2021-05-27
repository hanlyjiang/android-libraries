package io.hanlyjiang.gradle.android

import org.gradle.api.Plugin
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

/**
 * 对插件进行函数及属性的扩展
 * @author hanlyjiang 5/27/21 12:11 PM
 * @version 1.0
 */
val <T> Plugin<T>.logger: Logger
    get() = Logging.getLogger(this.javaClass.simpleName)

fun <T> Plugin<T>.logLifecycle(msg: String) {
    logger.log(LogLevel.LIFECYCLE, ":+++> $msg")
}

fun <T> Plugin<T>.logError(msg: String) {
    logger.log(LogLevel.ERROR, ":+++> $msg")
}