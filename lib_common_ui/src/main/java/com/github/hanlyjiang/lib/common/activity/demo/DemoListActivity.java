package com.github.hanlyjiang.lib.common.activity.demo;

import android.app.Activity;
import android.content.Intent;
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
 */
public abstract class DemoListActivity extends BaseListActivity<DemoListActivity.Item, DemoListActivity.ViewHolder>
        implements BaseListActivity.QuickAdapter<DemoListActivity.Item, DemoListActivity.ViewHolder> {

    /**
     * 入口数据返回
     *
     * @return 入口数据列表
     */
    @NonNull
    @Override
    public abstract List<Item> getDataList();

    @Override
    public void bindViewHolder(Item data, ViewHolder holder) {
        TextView tv1 = holder.itemView.findViewById(android.R.id.text1);
        TextView tv2 = holder.itemView.findViewById(android.R.id.text2);
        tv1.setText(data.name);
        tv2.setText(data.desc);
        holder.itemView.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), data.activityClazz)));
    }

    @Override
    public ViewHolder createViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = getLayoutInflater()
                .inflate(R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(itemView);
    }


    @NonNull
    @Override
    public QuickAdapter<Item, ViewHolder> getQuickAdapter() {
        return this;
    }

    public static class Item {
        String desc;
        String name;
        Class<? extends Activity> activityClazz;

        public Item(String desc, String name, Class<? extends Activity> activityClazz) {
            this.desc = desc;
            this.name = name;
            this.activityClazz = activityClazz;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}