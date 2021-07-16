package com.github.hanlyjiang.lib.common

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.hanlyjiang.lib.common.utils.DeviceFitUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 * @author hanlyjiang 2021/7/16 7:45 下午
 * @version 1.0
 */
@RunWith(AndroidJUnit4::class)
class DeviceFitUtilsTest {

    @Test
    fun testIsMiui() {
        Log.d("DeviceFitUtilsTest", "DeviceFitUtilsTest.testIsMiui:${DeviceFitUtils.isMiui()}")
        Log.d(
            "DeviceFitUtilsTest",
            "DeviceFitUtilsTest.getMiuiVersionCode:${DeviceFitUtils.getMiuiVersionCode()}"
        )
        Log.d(
            "DeviceFitUtilsTest",
            "DeviceFitUtilsTest.getMiuiVersionName:${DeviceFitUtils.getMiuiVersionName()}"
        )
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.github.hanlyjiang.lib.common.test", appContext.packageName)
    }
}