package io.hanlyjiang.gradle.android

import org.gradle.api.provider.Property

/**
 * InlineApkToAssetsPlugin 的扩展配置
 * @author hanlyjiang 5/27/21 11:01 PM
 * @version 1.0
 */
interface InlineApkToAssetsPluginExtension {
    /**
     * Host project name，apk会被拷贝到这个项目的assets目录中，默认为app
     */
    val hostProjectName: Property<String>
}