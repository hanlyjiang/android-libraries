package cn.hanlyjiang.hjapf;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.github.hanlyjiang.lib.common.activity.demo.DemoListActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.hanlyjiang.cutils.UtilsLauncherActivity;


public class MainActivity extends DemoListActivity<Class<? extends Activity>> {

    @NotNull
    @Override
    public List<Item<Class<? extends Activity>>> getDataList() {
        List<Item<Class<? extends Activity>>> items = new ArrayList<>();
        items.add(new Item<>(
                "演示Android-common-utils的库", "Utils库演示入口",
                UtilsLauncherActivity.class
        ));
        items.add(new Item<>(
                "JUST TEST", "入口测试",
                MainActivity.class
        ));
        return items;
    }

    @Override
    public void onItemClick(View itemView, Item<Class<? extends Activity>> data) {
        startActivity(new Intent(this, data.getPayload()));
    }
}