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
    id("com.github.hanlyjiang.android_maven_pub") version ("0.0.10") apply (false)
    id("kotlin-android")
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
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation(project(mapOf("path" to ":lib_common_utils")))
    DependenciesMgr.applyTestDependencies(this)

    // StringRes 注解
    implementation("androidx.appcompat:appcompat:1.3.0")
    // SnackBar 需要
    implementation("com.google.android.material:material:1.4.0")
    implementation("org.jetbrains:annotations:21.0.1")

    val daggerVersion = "2.40.5"
    // https://github.com/google/dagger
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    //    implementation "com.google.dagger:dagger-android:2.16"
    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
    // if you use the support libraries
    kapt("com.google.dagger:dagger-android-processor:$daggerVersion")
}

apply(plugin = "com.github.hanlyjiang.android_maven_pub")

configure<io.hanlyjiang.gradle.android.AndroidMavenPubPluginExtension> {
    groupId.set("com.github.hanlyjiang")
    artifactId.set("lib-di-sample")
    projectLocalRepoPath.set("local-maven-repo")
    versionName.set("0.0.1-SNAPSHOT")
    mavenPomAction.set(Action<MavenPom> {
        name.set("lib-di-sample")
        description.set("lib-di-sample")
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