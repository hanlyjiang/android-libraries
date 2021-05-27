package com.github.hanlyjiang.lib.common.utils;

import java.util.UUID;

/**
 * UUID 工具
 *
 * @author hanlyjiang on 2018/7/6-16:20.
 * @version 1.0
 */
public class UUIDUtils {

    /**
     * 生成一个随机的uuid（使用 {@link UUID#randomUUID()}生成）
     *
     * @return UUID 字符串，没有"-"
     */
    public static String newUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
