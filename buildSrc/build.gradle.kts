plugins {
    java
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm").version("1.3.61")
    // 方便使用kotlin开发构建逻辑：
    // https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin
    `kotlin-dsl`
    signing
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.15.0"
}

group = "com.github.hanlyjiang"
version = "0.0.2"

pluginBundle {
    website = "https://github.com/hanlyjiang/android_maven_pub_plugin"
    vcsUrl = "https://github.com/hanlyjiang/android_maven_pub_plugin.git"
    tags = listOf("android", "library", "maven")
}

gradlePlugin {
    plugins {
        create("AndroidMavenPubPlugin") {
            id = "com.github.hanlyjiang.android_maven_pub"
            displayName = "AndroidMavenPubPlugin"
            description = "Plugin for simplify publishing android library to maven center"
            implementationClass = "io.hanlyjiang.gradle.android.AndroidMavenPubPlugin"
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

repositories {
    google()
    jcenter()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    // 添加android相关build tools依赖，以便使用 android gradle 相关的API
    implementation("com.android.tools.build:gradle:4.1.3")
}