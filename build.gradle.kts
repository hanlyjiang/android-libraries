buildscript {
    apply(from = "config.gradle")
    // 绑定当前project中的extra变量
    val androidGradleBuildVersion: String by extra
    val kotlinVersion: String by extra
    repositories {
        // build model 卡住： https://blog.csdn.net/weixin_37119423/article/details/111500493
        MavenRepoMgr.applyAll(this)
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$androidGradleBuildVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

allprojects {
    repositories {
        MavenRepoMgr.applyAll(this)
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
            setUrl(SonatypeUrlDef.Snapshot_Upload_Url)
            // snapshot可以不用用户名密码
            // 查看自己的snapshot版本： https://oss.sonatype.org/content/repositories/snapshots/com/github/hanlyjiang/
        }
        maven {
            name = "Sonatype-Staging"
            setUrl(SonatypeUrlDef.Release_Upload_Url)
            credentials(PasswordCredentials::class.java) {
                username = property("ossrhUsername").toString()
                password = property("ossrhPassword").toString()
            }
        }
        // 官方仓库
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class.java) {
    group = "build"
    delete(rootProject.buildDir)
}