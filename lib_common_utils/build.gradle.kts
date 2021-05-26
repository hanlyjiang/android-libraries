import org.gradle.api.publish.maven.MavenPom

plugins {
    id("com.android.library")
    id("signing")
    `maven-publish`
//    kotlin("android")
//    kotlin("android.extensions")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0.0-SNAPSHOT")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

tasks.register("javadoc", Javadoc::class.java) {
    group = "publishing"
    dependsOn("assemble")
    source = android.sourceSets["main"].java.getSourceFiles()
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
//            addStringOption("Xdoclint:none")
            links?.add("https://developer.android.google.cn/reference/")
            links?.add("http://docs.oracle.com/javase/8/docs/api/")
        }
    }
}

tasks.register("jarSource", Jar::class.java) {
    group = "publishing"
    from(android.sourceSets["main"].java.srcDirs)
    archiveClassifier.set("sources")
}

tasks.register("jarJavadoc", Jar::class.java) {
    group = "publishing"
    dependsOn("javadoc")
    val javadoc: Javadoc = tasks.getByName("javadoc") as Javadoc
    from(javadoc.destinationDir)
    archiveClassifier.set("javadoc")
}

fun getMyPom(): Action<in MavenPom> {
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


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.getByName("release"))
                groupId = "com.github.hanlyjiang"
                artifactId = "android_common_utils"
                version = android.defaultConfig.versionName
                pom(getMyPom())
                // 添加javadoc
                artifact(tasks.getByName("jarJavadoc") as Jar)
                // 添加source
                // artifact(tasks.getByName("jarSource") as Jar)
            }
        }

        repositories {
            val ossrhCredentials = Action<PasswordCredentials> {
                username = properties["ossrhUsername"].toString()
                password = properties["ossrhPassword"].toString()
            }
            // sonar的仓库，地址根据项目的版本号来确定是snapshot还是正式仓库
            maven {
                name = "Sonartype"

                val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
                url = if (android.defaultConfig.versionName.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                credentials(ossrhCredentials)
                // snapshot的地址：
                // https://oss.sonatype.org/content/repositories/snapshots/com/github/hanlyjiang/android_common_utils/
            }
            // 项目本地的仓库
            maven {
                name = "ProjectLocal"

                val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
                val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
                url = if (android.defaultConfig.versionName.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            }
        }
    }
    // https://stackoverflow.com/questions/54654376/why-is-publishing-function-not-being-found-in-my-custom-gradle-kts-file-within


    signing {
        sign(publishing.publications.getByName("release"))
    }

}