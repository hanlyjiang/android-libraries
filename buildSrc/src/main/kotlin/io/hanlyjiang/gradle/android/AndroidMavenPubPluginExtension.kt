package io.hanlyjiang.gradle.android

import org.gradle.api.Action
import org.gradle.api.provider.Property
import org.gradle.api.publish.maven.MavenPom

/**
 *
 * @author hanlyjiang 5/27/21 4:30 PM
 * @version 1.0
 */
interface AndroidMavenPubPluginExtension {
    val mavenPomAction: Property<Action<MavenPom>>

    val fromAndroidPubName: Property<String>
    val groupId: Property<String>
    val artifactId: Property<String>

    /**
     * Releases repo 地址
     */
    val releasesRepoUrl: Property<String>

    /**
     * Snapshots repo 地址
     */
    val snapshotsRepoUrl: Property<String>

}