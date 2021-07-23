package cn.hanlyjiang.cutils

import android.app.Activity
import android.content.Intent
import android.view.View
import com.github.hanlyjiang.lib.common.activity.demo.DemoListActivity
import kotlin.reflect.KClass

/**
 * 工具类的测试入口
 * @author hanlyjiang 2021/7/23 11:52 下午
 * @version 1.0
 */
class UtilsLauncherActivity : DemoListActivity<KClass<out Activity>>() {

    override fun onItemClick(itemView: View?, data: Item<KClass<out Activity>>?) {
        startActivity(Intent(this,data?.payload?.java))
    }

    override fun getDataList(): List<Item<KClass<out Activity>>> {
        return mutableListOf<Item<KClass<out Activity>>>().apply {
            Item<KClass<out Activity>>(
                "UtilsLauncherActivity", "", UtilsLauncherActivity::class
            ).also {
                add(it)
            }
        }
    }
}