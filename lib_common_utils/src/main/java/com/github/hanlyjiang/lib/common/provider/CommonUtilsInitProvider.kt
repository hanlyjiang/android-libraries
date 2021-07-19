package com.github.hanlyjiang.lib.common.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.github.hanlyjiang.lib.common.helper.AppStatusHelper
import org.jetbrains.annotations.ApiStatus

/**
 * 必要工具初始化
 * @author hanlyjiang 2021/7/19 9:24 下午
 * @version 1.0
 */
@ApiStatus.Internal
class CommonUtilsInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        // 在此处进行初始化
        context?.let { AppStatusHelper.init(it) }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}