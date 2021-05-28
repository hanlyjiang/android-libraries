package io.hanlyjiang.gradle.android

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.create
import java.io.File

/**
 * 插件APK拷贝到宿主的assets目录
 * @author hanlyjiang 5/24/21 2:27 PM
 * @version 1.0
 */
class InlineApkToAssetsPlugin : Plugin<Project> {

    private lateinit var pluginExtension: InlineApkToAssetsPluginExtension

    override fun apply(project: Project) {
        pluginExtension = project.extensions.create("inline_apk_to_assets")
        project.afterEvaluate {
            initial(project.rootProject, project)
        }
    }

    private fun log(msg: String) {
        logLifecycle(msg)
    }

    private fun initial(_root: Project, _subProject: Project) {
        _subProject.run {
            log("project.afterEvaluate")
            if (!isAndroidAppProject()) {
                return
            }
            val android = getAndroidExtensionAsApp()
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
                apkFilePath.isNotEmpty()
            }
        }
    }

    private fun getHostProjectPath(): String {
        return if (pluginExtension.hostProjectName.isPresent) {
            pluginExtension.hostProjectName.get()
        } else {
            "app"
        }
    }

    private fun createCopyApkTask(
        apkPath: String,
        variant: ApplicationVariant,
        subProject: Project,
        rootProject: Project
    ) {
        log("createCopyApkTask variant:${variant.name}, project: ${subProject.name}")
        val hostProject = rootProject.project(getHostProjectPath())
        if (!hostProject.isAndroidAppProject()) {
            logError("定义的Host App 不是application类型的项目")
            return
        }
        val assetSrcDirs =
            hostProject.getAndroidExtensionAsApp().sourceSets.getByName("main").assets.srcDirs
        var hostProjectAssetsDir = ""
        if (assetSrcDirs.isNotEmpty()) {
            hostProjectAssetsDir = assetSrcDirs.first().absolutePath
        }
        if (hostProjectAssetsDir.isEmpty()) {
            return
        }
        val copyTask = subProject.tasks.register(
            "copyApkToHostAssets${variant.name.capitalize()}",
            Copy::class.java
        ) {
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
        subProject.tasks.getByName("assemble${variant.name.capitalize()}")
            .setFinalizedBy(mutableListOf(copyTask))
    }

}