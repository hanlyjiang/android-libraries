plugins {
    `java-library`
    id("kotlin")
    `java-gradle-plugin`
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.15.0"
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    val android_gradle_build_version:String by extra
    // 添加android相关build tools依赖，以便使用 android gradle 相关的API
//    implementation("com.android.tools.build:gradle:$android_gradle_build_version")
    implementation("com.android.tools.build:gradle:4.1.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${project.properties["kotlin_version"].toString()}")
}

group = "com.github.hanlyjiang"
version = "0.0.3"

pluginBundle {
    website = "https://github.com/hanlyjiang/android_maven_pub_plugin"
    vcsUrl = "https://github.com/hanlyjiang/android_maven_pub_plugin.git"
    tags = listOf("android", "library", "maven")
}

gradlePlugin {
    plugins {
        create("AndroidMavenPubPlugin") {
            id = "com.github.hanlyjiang.android_maven_pub"
            displayName = "Android maven publish plugin"
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