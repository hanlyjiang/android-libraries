package io.hanlyjiang.gradle.android

import org.gradle.api.Action
import org.gradle.api.provider.Property
import org.gradle.api.publish.maven.MavenPom

/**
 * AndroidMavenPubPlugin 的配置扩展
 * @author hanlyjiang 5/27/21 4:30 PM
 * @version 1.0
 */
interface AndroidMavenPubPluginExtension {
    /**
     * Maven pom action 配置，用于提供自定义的pom信息
     */
    val mavenPomAction: Property<Action<MavenPom>>

    /**
     * 指定要基于的android配置的名称
     */
    val fromAndroidPubName: Property<String>

    /**
     * 指定maven仓库的 Group id
     */
    val groupId: Property<String>

    /**
     * 指定Library 的 Artifact id
     */
    val artifactId: Property<String>

    /**
     * Releases repo 地址
     */
    val releasesRepoUrl: Property<String>

    /**
     * Snapshots repo 地址
     */
    val snapshotsRepoUrl: Property<String>

    /**
     * 是否上传源码jar
     */
    val includeSourceJar: Property<Boolean>

    /**
     * 是否上传javadoc的jar
     */
    val includeJavadocJar: Property<Boolean>

}