plugins {
    java
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm").version("1.3.61")
    // 方便使用kotlin开发构建逻辑：
    // https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin
    `kotlin-dsl`
}


repositories {
    // 引入本地的gradle插件repo
    maven {
        url = uri("../local-plugin-repository")
    }
    maven { url = uri("https://maven.aliyun.com/repository/google") }
    maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    google()
    jcenter()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    // 添加android相关build tools依赖，以便使用 android gradle 相关的API
    implementation("com.android.tools.build:gradle:4.1.3")

    // 引入我们自己的插件库（本地无法直接引入项目）
    // 由于buildSrc项目会被所有的项目都引用，所以我们只能使用compileOnly来引入（不过这么引入什么用都没有）
    // 否则可能会产生下面的错误：
    /*
    Error resolving plugin [id: 'com.github.hanlyjiang.android_maven_pub', version: '0.0.3', apply: false]
    > Plugin request for plugin already on the classpath must not include a version
     */
//    compileOnly("com.github.hanlyjiang:gradlePlugins:0.0.3")
}