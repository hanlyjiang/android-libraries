package com.github.hanlyjiang.lib.common.activity.demo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.hanlyjiang.lib.common.ui.R;

import java.util.List;

/**
 * 垂直列表示例基础Activity
 * <br>
 * 实现 {@link #getDataList()} 方法返回数据即可快速实现一个测试列表
 *
 * @author hanlyjiang
 * @param <P> 数据payload类型
 */
public  abstract class DemoListActivity<P> extends BaseListActivity<DemoListActivity.Item<P>, DemoListActivity.ViewHolder>
        implements BaseListActivity.QuickAdapter<DemoListActivity.Item<P>, DemoListActivity.ViewHolder> {

    /**
     * 入口数据返回
     *
     * @return 入口数据列表
     */
    @NonNull
    @Override
    public abstract List<Item<P>> getDataList();

    /**
     * Item 点击事件自定义，默认打开对应的 Activity
     *
     * @param itemView itemView
     * @param data 数据
     */
    public abstract void onItemClick(View itemView, Item<P> data);

    @Override
    public void bindViewHolder(Item<P> data, ViewHolder holder) {
        TextView tv1 = holder.itemView.findViewById(android.R.id.text1);
        TextView tv2 = holder.itemView.findViewById(android.R.id.text2);
        tv1.setText(data.name);
        tv2.setText(data.desc);
        holder.itemView.setOnClickListener(v -> onItemClick(holder.itemView, data));
    }

    @Override
    public ViewHolder createViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = getLayoutInflater()
                .inflate(R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(itemView);
    }


    @NonNull
    @Override
    public QuickAdapter<Item<P>, ViewHolder> getQuickAdapter() {
        return this;
    }

    public static class Item<P> {
        String desc;
        String name;
        P payload;

        public Item(String desc, String name, P payload) {
            this.desc = desc;
            this.name = name;
            this.payload = payload;
        }

        public String getDesc() {
            return desc;
        }

        public String getName() {
            return name;
        }

        public P getPayload() {
            return payload;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}