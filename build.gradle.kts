// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.32")
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        maven {
            name = "Sonatype-Snapshots"
            setUrl("https://oss.sonatype.org/content/repositories/snapshots")
//            setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots")
            // snapshot可以不用用户名密码
            // 查看自己的snapshot版本： https://oss.sonatype.org/content/repositories/snapshots/com/github/hanlyjiang/
            credentials(PasswordCredentials::class.java) {
                username = property("ossrhUsername").toString()
                password = property("ossrhPassword").toString()
            }
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
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class.java) {
    group = "build"
    delete(rootProject.buildDir)
}