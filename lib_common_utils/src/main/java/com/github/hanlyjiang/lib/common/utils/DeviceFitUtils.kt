package com.github.hanlyjiang.lib.common.utils

/**
 * 设备型号识别工具
 * @author hanlyjiang 2021/7/16 7:31 下午
 * @version 1.0
 */
object DeviceFitUtils {

    private const val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
    private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private const val CLASS_SYSTEM_PROPERTIES = "android.os.SystemProperties"

    /**
     * 是否为MIUI系统
     *
     * @return true - 是MIUI；false -非MIUI设备
     */
    fun isMiui(): Boolean {
        return getSystemProperties(KEY_MIUI_VERSION_CODE).isNotBlank()
                || getSystemProperties(KEY_MIUI_VERSION_NAME).isNotBlank()
    }

    private fun getSystemProperties(key: String, defaultValue: String = ""): String {
        return RefInvoker.invokeStaticMethod(
            CLASS_SYSTEM_PROPERTIES,
            "get",
            arrayOf(String::class.java, String::class.java),
            arrayOf(key, defaultValue)
        ).toString()
    }

    /**
     * Get miui version code
     *
     * @return -1 - 非MIUI设备/获取失败； > 0 正常版本号，如： 11 - 对应 MIUI12
     */
    fun getMiuiVersionCode(): Int {
        getSystemProperties(KEY_MIUI_VERSION_CODE).run {
            return if (isBlank()) {
                -1
            } else {
                this.toInt()
            }
        }
    }

    /**
     * Get miui version name
     *
     * @return "" - 非MIUI设备/获取失败； 正常版本名称，如： V125 - 对应 MIUI 12.5
     */
    fun getMiuiVersionName(): String {
        return getSystemProperties(KEY_MIUI_VERSION_NAME)
    }

}