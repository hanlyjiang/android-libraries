package cn.hanlyjiang.hjapf;

import com.github.hanlyjiang.lib.common.activity.demo.DemoListActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends DemoListActivity {

    @NotNull
    @Override
    public List<Item> getDataList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(
                "入口测试",
                "JUST TEST",
                MainActivity.class
        ));
        items.add(new Item(
                "入口测试",
                "JUST TEST",
                MainActivity.class
        ));
        return items;
    }

}