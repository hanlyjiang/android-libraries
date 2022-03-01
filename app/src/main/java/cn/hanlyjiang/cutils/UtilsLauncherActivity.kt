package cn.hanlyjiang.cutils

import android.app.Activity
import android.content.Intent
import android.view.View
import cn.hanlyjiang.cutils.activity.CommonUIDemoActivity
import cn.hanlyjiang.cutils.activity.DeviceFitUtilsActivity
import com.github.hanlyjiang.lib.common.activity.demo.DemoListActivity
import com.github.hanlyjiang.lib.common.di.SdkInjector
import com.github.hanlyjiang.lib.common.di.instance.TestDiMainActivity

/**
 * 工具类的测试入口
 * @author hanlyjiang 2021/7/23 11:52 下午
 * @version 1.0
 */
class UtilsLauncherActivity : DemoListActivity<Class<out Activity>>() {

    override fun onItemClick(itemView: View?, data: Item<Class<out Activity>>?) {
        if (data?.payload?.name == TestDiMainActivity::class.java.name) {
            SdkInjector.init(application)
            startActivity(Intent(this, data?.payload))
        } else {
            startActivity(Intent(this, data?.payload))
        }
    }

    override fun getDataList(): List<Item<Class<out Activity>>> {
        return mutableListOf<Item<Class<out Activity>>>().apply {
            Item<Class<out Activity>>(
                "启动 DeviceFitUtilsActivity", "DeviceFitUtilsActivity", DeviceFitUtilsActivity::class.java
            ).also {
                add(it)
            }
            Item<Class<out Activity>>(
                "启动 CommonUIDemoActivity", "CommonUIDemoActivity", CommonUIDemoActivity::class.java
            ).also {
                add(it)
            }
            Item<Class<out Activity>>(
                "启动 TestDiMainActivity", "TestDiMainActivity", TestDiMainActivity::class.java
            ).also {
                add(it)

            }
        }
    }

    // 泛型中： out 表示只生产提供不写入，所以类型在生产时可以返回子类的（子类型开放）
    // in 表示可能需要写入，此时类型只能是父类或该类的 （子类型闭合）
}