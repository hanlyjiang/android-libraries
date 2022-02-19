package cn.hanlyjiang.cutils.activity

import android.view.View
import com.github.hanlyjiang.lib.common.activity.demo.DemoListActivity
import com.github.hanlyjiang.lib.common.ui.dialog.ProgressDialogBuilder
import com.github.hanlyjiang.lib.common.utils.SnackBarUtils

/**
 * 工具类的测试入口
 * @author hanlyjiang 2021/7/23 11:52 下午
 * @version 1.0
 */
class CommonUIDemoActivity : DemoListActivity<View.OnClickListener>() {

    override fun getDataList(): List<Item<View.OnClickListener>> {
        return mutableListOf<Item<View.OnClickListener>>().apply {
            add(newItem("显示对话框", "显示对话框") {
                ProgressDialogBuilder(this@CommonUIDemoActivity)
                    .setMessage("正在加载").show()
            })
            add(newItem("显示对话框-Dark", "显示对话框") {
                ProgressDialogBuilder(this@CommonUIDemoActivity)
                    .setMessage("正在加载").setIsDarkMode(true).show()
            })
        }
    }

    private fun showSnackBar(msg: Any) {
        SnackBarUtils.showSnackBar(this, "$msg")
    }

    private fun newItem(name: String, desc: String, onClickListener: View.OnClickListener) = Item(
        name,
        desc,
        onClickListener
    )

    override fun onItemClick(itemView: View?, data: Item<View.OnClickListener>?) {
        data?.payload?.onClick(itemView)
    }

    // 泛型中： out 表示只生产提供不写入，所以类型在生产时可以返回子类的（子类型开放）
    // in 表示可能需要写入，此时类型只能是父类或该类的 （子类型闭合）
}