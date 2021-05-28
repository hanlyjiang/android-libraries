plugins {
    `java-library`
    id("kotlin")
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.15.0"
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    val android_gradle_build_version: String by extra
    // 添加android相关build tools依赖，以便使用 android gradle 相关的API
//    implementation("com.android.tools.build:gradle:$android_gradle_build_version")
    implementation("com.android.tools.build:gradle:4.1.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${project.properties["kotlin_version"].toString()}")
}

// 用于指定所有的group
group = "com.github.hanlyjiang"
// 用于指定当前仓库本身的版本
version = "0.0.4"


// 多个插件时，gradlePlugin需要放置到pluginBundle前面，避免出现pluginBundle中找不到插件config的情况
gradlePlugin {
    plugins {

        create("AndroidMavenPubPlugin") {
            id = "com.github.hanlyjiang.android_maven_pub"
            implementationClass = "io.hanlyjiang.gradle.android.AndroidMavenPubPlugin"
        }

        create("InlineApkToAssetsPlugin") {
            id = "com.github.hanlyjiang.inline_apk_to_assets"
            implementationClass = "io.hanlyjiang.gradle.android.InlineApkToAssetsPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/hanlyjiang/android-libraries/blob/master/gradlePlugins"
    vcsUrl = "https://github.com/hanlyjiang/android-libraries.git"
    //单个插件时，使用这里的定义
    //    tags = listOf("android", "library", "maven")

    // 定义多个插件时需要
    // ref: https://plugins.gradle.org/docs/publish-plugin
    (plugins){

        "AndroidMavenPubPlugin" {
            displayName = "Android maven publish plugin"
            description = "Plugin for simplify publishing android library to maven center，" +
                    "visit https://github.com/hanlyjiang/android-libraries/blob/master/gradlePlugins/doc/AndroidMavenPubPlugin使用说明.md for how to use."
            tags = listOf("android", "library", "maven")
            version = "0.0.4"
            group = "com.github.hanlyjiang"
        }

        "InlineApkToAssetsPlugin" {
            displayName = "Copy App Module's APK files to HOST APP"
            description = "Plugin for auto copy plugin's apk to host app assets dir"
            tags = listOf("android", "library", "plugin")
            version = "0.0.3"
            group = "com.github.hanlyjiang"
        }
    }
}



publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
    }
}