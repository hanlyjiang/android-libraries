import io.hanlyjiang.gradle.android.GitHelper

plugins {
    id("com.android.library")
    id("signing")
    `maven-publish`
    kotlin("android")
    kotlin("android.extensions")
    // 解决 viewBinding 找不到BR
    kotlin("kapt")

    // 引入我们本地仓库中的gradle插件
    id("com.github.hanlyjiang.android_maven_pub") version ("0.0.13") apply (false)
}

// 引入buildSrc中的插件
//apply<io.hanlyjiang.gradle.android.AndroidMavenPubPlugin>()

android {
    compileSdk = properties["_compileSdk"] as Int
    buildToolsVersion = properties["_buildTools"] as String
    defaultConfig {
        minSdk = properties["_minSdk"] as Int
        targetSdk = properties["_targetSdk"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        dataBinding = true
    }

    dataBinding {
        isEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
}

dependencies {
    DependenciesMgr.applyTestDependencies(this)

    // StringRes 注解
    implementation("androidx.appcompat:appcompat:1.3.0")
    // SnackBar 需要
    implementation("com.google.android.material:material:1.4.0")
    implementation("org.jetbrains:annotations:21.0.1")
}

apply(plugin = "com.github.hanlyjiang.android_maven_pub")

configure<io.hanlyjiang.gradle.android.AndroidMavenPubPluginExtension> {
    groupId.set("com.github.hanlyjiang")
    artifactId.set("android-common-ui")
    projectLocalRepoPath.set("local-maven-repo")
    versionName.set("0.0.2-SNAPSHOT")
    mavenPomAction.set(Action<MavenPom> {
        name.set("Android Common UI Lib")
        description.set("Android Common UI Library For HJ")
        url.set("https://github.com/hanlyjiang/android-libraries/")
        properties.set(
            mapOf(
                "cvs_revision" to GitHelper.getGitRevision(),
                "cvs_branch" to GitHelper.getGitBranch()
            )
        )
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
            connection.set("scm:git:git://github.com/hanlyjiang/android-libraries.git")
            developerConnection.set("scm:git:ssh://github.com/hanlyjiang/android-libraries.git")
            url.set("https://github.com/hanlyjiang/android-libraries")
        }
    })
}

GitHelper.createShowGitRepoInfoTask(tasks)