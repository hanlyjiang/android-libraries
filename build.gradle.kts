// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.32")
    val android_gradle_build_version by extra("4.1.3")
    repositories {
        // build model 卡住： https://blog.csdn.net/weixin_37119423/article/details/111500493
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/jcenter") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/google") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/public") }
        //        google()
        //        jcenter()
        //        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$android_gradle_build_version")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/jcenter") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }

        // 加入项目临时仓库，方便测试
        maven {
            name = "ProjectLocal-Snapshots"
            setUrl(File(rootProject.rootDir, "local-maven-repo${File.separator}snapshots"))
        }
        maven {
            name = "ProjectLocal-Release"
            setUrl(File(rootProject.rootDir, "local-maven-repo${File.separator}release"))
        }

        // 加入 maven snapshot 仓库及 release 仓库
        maven {
            name = "Sonatype-Snapshots"
            setUrl("https://oss.sonatype.org/content/repositories/snapshots")
//            setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots")
            // snapshot可以不用用户名密码
            // 查看自己的snapshot版本： https://oss.sonatype.org/content/repositories/snapshots/com/github/hanlyjiang/
//            credentials(PasswordCredentials::class.java) {
//                username = property("ossrhUsername").toString()
//                password = property("ossrhPassword").toString()
//            }
        }
        maven {
            name = "Sonatype-Staging"
            setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
//            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials(PasswordCredentials::class.java) {
                username = property("ossrhUsername").toString()
                password = property("ossrhPassword").toString()
            }
        }

        // 官方仓库
        google()
        mavenCentral()
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    group = "build"
    delete(rootProject.buildDir)
}