plugins {
    id("java")
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm").version("1.3.61")
}

group = "io.github.hanlyjiang.gradle"
version = "0.0.2"


repositories {
    google()
    jcenter()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    // 添加android相关build tools依赖，以便使用 android gradle 相关的API
    implementation("com.android.tools.build:gradle:4.1.3")
}