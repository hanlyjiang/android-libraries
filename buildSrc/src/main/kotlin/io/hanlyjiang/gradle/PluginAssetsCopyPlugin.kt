package io.hanlyjiang.gradle

import com.android.build.api.dsl.ApkExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.Copy
import java.io.File

/**
 * 插件APK拷贝到宿主的assets目录
 * @author hanlyjiang 5/24/21 2:27 PM
 * @version 1.0
 */
class PluginAssetsCopyPlugin : Plugin<Project> {

    private val logger: Logger = Logging.getLogger("PluginAssetsCopyPlugin")

    override fun apply(target: Project) {
        target.afterEvaluate {
            initial(target.rootProject, target)
        }
    }

    private fun log(msg: String) {
        logger.log(LogLevel.LIFECYCLE, ":+++> $msg")
    }

    private fun initial(_root: Project, _subProject: Project) {
        _subProject.run {
            log("project.afterEvaluate")
            if (this.extensions.getByName("android") !is ApkExtension) {
                return
            }
            val android = (this as ExtensionAware).extensions.getByName("android") as BaseAppModuleExtension
            log("变体size： ${android.applicationVariants.size}")
            android.applicationVariants.all { variant ->
                log("开始遍历 applicationVariants， variant:${variant.name}, project: ${this.name}")
                var apkFilePath = ""
                variant.outputs.forEach {
                    log("${it.baseName},${it.dirName},${it.name}, ${it.outputFile}")
                    // 获取APK输出路径
                    apkFilePath = it.outputFile.absolutePath
                }
                if (apkFilePath.isNotEmpty()) {
                    createCopyApkTask(apkFilePath, variant, _subProject, _root)
                }
            }
        }
    }

    private fun createCopyApkTask(apkPath: String, variant: ApplicationVariant, subProject: Project, rootProject: Project) {
        log("createCopyApkTask variant:${variant.name}, project: ${subProject.name}")
        val hostProject = rootProject.project("app")
        val android = hostProject.extensions.getByName("android")
        if (android !is com.android.build.gradle.AppExtension) {
            return
        }
        val assetSrcDirs = android.sourceSets.getByName("main").assets.srcDirs
        var hostProjectAssetsDir = ""
        if (assetSrcDirs.isNotEmpty()) {
            hostProjectAssetsDir = assetSrcDirs.first().absolutePath
        }
        if (hostProjectAssetsDir.isEmpty()) {
            return
        }
        val copyTask = subProject.tasks.register("copyApkToHostAssets${variant.name.capitalize()}", Copy::class.java) {
            it.apply {
                group = "custom"
//                dependsOn("assemble${variant.name.capitalize()}")
                val fileName = File(apkPath).name.replace("-${variant.name}", "")
                from(apkPath)
                into(hostProjectAssetsDir)
                rename {
                    it.replace("-${variant.name}", "")
                }
                doLast {
                    log("Copy $apkPath to $hostProjectAssetsDir")
                    if (File(hostProjectAssetsDir, fileName).isFile) {
                        log("Copy Success!!!")
                    } else {
                        log("Copy Failed!!! ")
                    }
                }
            }
        }
        subProject.tasks.getByName("assemble${variant.name.capitalize()}").setFinalizedBy(mutableListOf(copyTask))
    }
}