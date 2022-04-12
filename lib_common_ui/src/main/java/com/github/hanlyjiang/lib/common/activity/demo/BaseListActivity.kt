package com.github.hanlyjiang.lib.common.activity.demo

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.hanlyjiang.lib.common.ui.R

/**
 * 简单的列表界面，默认垂直列表，一般用于构建测试入口页面
 *
 * @constructor Create empty Base list activity
 */
abstract class BaseListActivity<T, VH : RecyclerView.ViewHolder?> : DemoBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_list)
        findViewById<RecyclerView>(R.id.recyclerView).run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = SimpleListAdapter(getQuickAdapter())
        }
        supportActionBar?.run {
            getTitleText()?.let {
                title = it
            }
        }
    }

    fun getTitleText(): String? {
        return null
    }

    /**
     * Get recycler view
     *
     * @return
     */
    fun getRecyclerView(): RecyclerView {
        return findViewById<RecyclerView>(R.id.recyclerView)
    }

    /**
     * QuickAdapter返回
     *
     * @return QuickAdapter
     */
    abstract fun getQuickAdapter(): QuickAdapter<T, VH>

    interface QuickAdapter<T, VH> {
        fun bindViewHolder(data: T, holder: VH)
        fun createViewHolder(parent: ViewGroup, viewType: Int): VH
        fun getDataList(): List<T>
    }

    internal class SimpleListAdapter<T, VH : RecyclerView.ViewHolder?>(private val quickAdapter: QuickAdapter<T, VH>) :
        RecyclerView.Adapter<VH>() {

        private val dataList: MutableList<T> = mutableListOf()

        init {
            dataList.addAll(quickAdapter.getDataList())
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return quickAdapter.createViewHolder(parent, viewType)
        }


        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = dataList[position]
            quickAdapter.bindViewHolder(item, holder)
        }

    }
}