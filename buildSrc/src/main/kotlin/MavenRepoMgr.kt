import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object MavenRepoMgr {

    const val GradlePlugin = "https://maven.aliyun.com/repository/gradle-plugin"
    const val JCenter = "https://maven.aliyun.com/repository/jcenter"
    const val Google = "https://maven.aliyun.com/repository/google"
    const val Maven = "https://maven.aliyun.com/repository/public"

    fun applyAll(repoHandler: RepositoryHandler) {
        repoHandler.maven(GradlePlugin)
        repoHandler.maven(JCenter)
        repoHandler.maven(Google)
        repoHandler.maven(Maven)
        //        google()
        //        jcenter()
        //        mavenCentral()
    }
}