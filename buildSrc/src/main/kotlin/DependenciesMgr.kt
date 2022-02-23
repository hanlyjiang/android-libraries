import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.maven

/**
 * 依赖管理简单集合处理
 */
object DependenciesMgr {
    fun DependencyHandler.`androidTestImplementation`(dependencyNotation: Any): Dependency? =
        add("androidTestImplementation", dependencyNotation)

    fun DependencyHandler.`testImplementation`(dependencyNotation: Any): Dependency? =
        add("testImplementation", dependencyNotation);

    fun applyTestDependencies(depHandler: DependencyHandler) {
        depHandler.apply {
            testImplementation("junit:junit:4.13.2")
            androidTestImplementation("androidx.test.ext:junit:1.1.3")
            androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
        }
    }

    fun appJunitTest(depHandler: DependencyHandler) {
        depHandler.apply {
            testImplementation("junit:junit:4.13.2")
            androidTestImplementation("androidx.test.ext:junit:1.1.3")
            androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
        }
    }


}