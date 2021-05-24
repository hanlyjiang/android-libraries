plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "cn.hanlyjiang.plugin_01"
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
//initial(rootProject, project)

//fun initial(_root: Project, _subProject: Project) {
//    val logger: Logger = Logging.getLogger("MyCustomTask")
//    fun log(msg: String) {
//        logger.log(LogLevel.LIFECYCLE, ":+++> $msg")
//    }
//
//    fun createCopyApkTask(apkPath: String, variant: com.android.build.gradle.api.ApplicationVariant) {
//        log("createCopyApkTask variant:${variant.name}, project: ${_subProject.name}")
//        val hostProject = _root.project("app")
//        val android = hostProject.extensions["android"]
//        if (android !is com.android.build.gradle.AppExtension) {
//            return
//        }
//        val assetSrcDirs = android.sourceSets.getByName("main").assets.srcDirs
//        var hostProjectAssetsDir = ""
//        if (assetSrcDirs.isNotEmpty()) {
//            hostProjectAssetsDir = assetSrcDirs.first().absolutePath
//        }
//        if (hostProjectAssetsDir.isEmpty()) {
//            return
//        }
//        _subProject.tasks.create("copyApkToHostAssets${variant.name.capitalize()}") {
//            group = "custom"
//            dependsOn("assemble${variant.name.capitalize()}")
//            doLast {
//                log("Copy $apkPath to $hostProjectAssetsDir")
//                val fileName = File(apkPath).name.replace("-${variant.name}", "")
//                copy {
//                    from(apkPath)
//                    into(hostProjectAssetsDir)
//                    rename {
//                        it.replace("-${variant.name}", "")
//                    }
//                }
//                if (File(hostProjectAssetsDir, fileName).isFile) {
//                    log("Copy Success!!!")
//                } else {
//                    log("Copy Failed!!! ")
//                }
//            }
//        }
//    }
//    _subProject.afterEvaluate {
//        log("project.afterEvaluate")
//        if (this.extensions["android"] !is com.android.build.api.dsl.ApkExtension) {
//            return@afterEvaluate
//        }
//        val android = (this as ExtensionAware).extensions.getByName("android") as com.android.build.gradle.internal.dsl.BaseAppModuleExtension
//        log("变体size： ${android.applicationVariants.size}")
//        android.applicationVariants.all { variant ->
//            log("开始遍历 applicationVariants， variant:${variant.name}, project: ${this.name}")
//            var apkFilePath = ""
//            variant.outputs.forEach {
//                log("${it.baseName},${it.dirName},${it.name}, ${it.outputFile}")
//                apkFilePath = it.outputFile.absolutePath
//            }
//            if (apkFilePath.isNotEmpty()) {
//                createCopyApkTask(apkFilePath, variant)
//            }
//            true
//        }
//    }
//
//}




