import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.findPlugin
import org.gradle.kotlin.dsl.typeOf
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec

const val MavenPubPluginId = "com.github.hanlyjiang.android_maven_pub"

const val MavenPubPluginVersion = "0.0.10"

object AndroidMavenPubHelper {
    fun apply(pluginsConfig: PluginDependenciesSpec) {
        pluginsConfig.apply {
            id("signing")
            id("maven-publish")
            id(MavenPubPluginId) version (MavenPubPluginVersion) apply (false)
        }
    }

    inline fun <reified T : Any> Project.configure(noinline configuration: T.() -> Unit): Unit =
        typeOf<T>().let { type ->
            convention.findByType(type)?.let(configuration)
                ?: convention.findPlugin<T>()?.let(configuration)
                ?: convention.configure(type, configuration)
        }
}