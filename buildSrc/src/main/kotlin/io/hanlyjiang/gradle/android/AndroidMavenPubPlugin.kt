package io.hanlyjiang.gradle.android

import com.android.build.gradle.LibraryExtension
import org.gradle.api.*
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.publish.Publication
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.StandardJavadocDocletOptions
import org.gradle.kotlin.dsl.create
import org.gradle.plugins.signing.SigningExtension
import java.io.File

/**
 * Android 库发布到Maven仓库的辅助插件
 * @author hanlyjiang 5/27/21 11:27 AM
 * @version 1.0
 */
class AndroidMavenPubPlugin : Plugin<Project> {

    companion object {
        const val TaskName_jarSource = "jarSource"
        const val TaskName_jarJavadoc = "jarJavadoc"
        const val TaskName_javadoc = "javadoc"
        const val TaskGroup = "publishing"
        private const val SourceSet = "main"
        private const val Default_fromAndroidPubName = "release"
        private const val USAGE = """
            --- AndroidMavenPubPlugin 插件使用说明 ---
            # 使用步骤：
            1. 引入插件;
            2. 配置(gradle配置及maven仓库配置）；
            3. 执行任务；
            请参考使用文档：https://github.com/hanlyjiang
            ------ ------ ------ ------ ------ ------
        """
    }

    private lateinit var pluginExtension: AndroidMavenPubPluginExtension

    override fun apply(project: Project) {
        logLifecycle(USAGE)
        logLifecycle("Apply")
        pluginExtension = project.extensions.create("android_pom")
        if (!project.isAndroidLibProject()) {
            logError("不是android library项目，不执行任务扩展")
            return
        }
        // 注册一个帮助任务
        project.tasks.register("${AndroidMavenPubPlugin::class.java.simpleName}-help") {
            group = TaskGroup
            doFirst {
                logLifecycle(USAGE)
            }
        }
        registerJarTasks(project)
        project.afterEvaluate {
            checkExtension(pluginExtension)
            configMavenPublishing(project)
            configMavenSigning(project)
        }
        return
    }

    private fun checkExtension(pluginExtension: AndroidMavenPubPluginExtension) {
        if (!pluginExtension.fromAndroidPubName.isPresent) {
            logError("未选择 fromAndroidPubName，设置为release")
            pluginExtension.fromAndroidPubName.set(Default_fromAndroidPubName)
        }
        if (!pluginExtension.mavenPomAction.isPresent) {
            logError("未配置 mavenPomAction！！！")
            throw GradleException("未配置 mavenPomAction！！！")
        }
        if (!pluginExtension.groupId.isPresent) {
            logError("未配置 groupId！！！")
            throw GradleException("未配置 groupId！！！")
        }
        if (!pluginExtension.artifactId.isPresent) {
            throw GradleException("未配置 artifactId！！！")
        }
    }

    private fun configMavenSigning(project: Project) {
        project.extensions.configure("signing", Action<SigningExtension> {
            val publishing = project.extensions.getByName(PublishingExtension.NAME) as PublishingExtension
            sign(publishing.publications.getByName("release"))
        })
    }

    /**
     * Config maven publishing
     *
     * @param project  项目
     */
    private fun configMavenPublishing(project: Project) {
        val android = project.getAndroidExtensionAsLibrary()
        project.extensions.configure(PublishingExtension.NAME, createPublishingAction(project, android))
    }

    private fun createPublishingAction(project: Project, android: LibraryExtension) =
            Action<PublishingExtension> {
                publications {
                    val pubCreateAction: Action<in Publication> = Action<Publication> {
                        if (this is MavenPublication) {
                            this.apply {
                                from(project.components.getByName("release"))
                                groupId = pluginExtension.groupId.get()
                                artifactId = pluginExtension.artifactId.get()
                                version = android.defaultConfig.versionName
                                pom(pluginExtension.mavenPomAction.get())
                                pluginExtension.run {
                                    if (!includeJavadocJar.isPresent || includeJavadocJar.get()) {
                                        // 添加javadoc
                                        artifact(project.tasks.getByName(TaskName_jarJavadoc) as Jar)
                                    }
                                    if (!includeSourceJar.isPresent || includeSourceJar.get()) {
                                        // 添加source
                                        artifact(project.tasks.getByName(TaskName_jarSource) as Jar)
                                    }
                                }
                            }
                        }
                    }
                    this.create("release", MavenPublication::class.java, pubCreateAction)
                }

                repositories {
                    val ossrhCredentials = Action<PasswordCredentials> {
                        username = project.properties["ossrhUsername"].toString()
                        password = project.properties["ossrhPassword"].toString()
                    }
                    // sonar的仓库，地址根据项目的版本号来确定是snapshot还是正式仓库
                    maven {
                        name = "Sonartype"

                        val releasesRepoUrl = project.uri(getMavenReleaseUrl())
                        val snapshotsRepoUrl = project.uri(getMavenSnapshotsUrl())
                        url = if (android.defaultConfig.versionName.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                        credentials(ossrhCredentials)
                        // snapshot的地址：
                        // https://oss.sonatype.org/content/repositories/snapshots/com/github/hanlyjiang/android_common_utils/
                    }
                    // 项目本地的仓库
                    maven {
                        name = "ProjectLocal"

                        val releasesRepoUrl = project.uri(project.layout.buildDirectory.dir("repos/releases"))
                        val snapshotsRepoUrl = project.uri(project.layout.buildDirectory.dir("repos/snapshots"))
                        url = if (android.defaultConfig.versionName.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                    }
                }
            }

    private fun getMavenSnapshotsUrl(): String {
        pluginExtension.snapshotsRepoUrl.run {
            return if (isPresent) {
                get()
            } else {
                "https://oss.sonatype.org/content/repositories/snapshots/"
            }
        }
    }

    private fun getMavenReleaseUrl(): String {
        pluginExtension.snapshotsRepoUrl.run {
            return if (isPresent) {
                get()
            } else {
                "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            }
        }
    }

    private fun registerJarTasks(target: Project) {
        logLifecycle("registerTasks")
        val android = target.getAndroidExtensionAsLibrary()
        target.tasks.let { tasks ->
            tasks.register(TaskName_jarSource, Jar::class.java) {
                group = TaskGroup
                from(android.sourceSets.getByName(SourceSet).java.srcDirs)
                archiveClassifier.set("sources")
            }
            logLifecycle("已创建任务 $TaskGroup.$TaskName_jarSource")

            tasks.register(TaskName_jarJavadoc, Jar::class.java) {
                group = TaskGroup
                dependsOn(TaskName_javadoc)
                val javadoc: Javadoc = tasks.getByName(TaskName_javadoc) as Javadoc
                from(javadoc.destinationDir)
                archiveClassifier.set("javadoc")
            }
            logLifecycle("已创建任务 $TaskGroup.$TaskName_jarJavadoc")

            tasks.register(TaskName_javadoc, Javadoc::class.java) {
                group = TaskGroup
                dependsOn("assemble")
                source = android.sourceSets.getByName(SourceSet).java.getSourceFiles()
                classpath += project.files(android.bootClasspath + File.pathSeparator)
                if (JavaVersion.current().isJava9Compatible) {
                    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
                }
                android.libraryVariants.forEach { libraryVariant ->
                    classpath += libraryVariant.javaCompileProvider.get().classpath
                }
                options.apply {
                    encoding("UTF-8")
                    charset("UTF-8")
                    isFailOnError = false

                    (this as StandardJavadocDocletOptions).apply {
//                        addStringOption("Xdoclint:none")
                        links?.add("https://developer.android.google.cn/reference/")
                        links?.add("http://docs.oracle.com/javase/8/docs/api/")
                    }
                }
            }
            logLifecycle("已创建任务 $TaskGroup.$TaskName_javadoc")
        }
    }

    private fun getDefaultPom(): Action<in MavenPom> {
        return Action<MavenPom> {
            name.set("Android Common Utils Lib")
            description.set("Android Common Utils Library For HJ")
            url.set("https://github.com/hanlyjiang/lib_common_utils")
            properties.set(mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
            ))
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("hanlyjiang")
                    name.set("Hanly Jiang")
                    email.set("hanlyjiang@outlook.com")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/hanlyjiang/lib_common_utils.git")
                developerConnection.set("scm:git:ssh://github.com/hanlyjiang/lib_common_utils.git")
                url.set("https://github.com/hanlyjiang/lib_common_utils")
            }
        }
    }

}