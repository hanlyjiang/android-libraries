import io.hanlyjiang.gradle.android.GitHelper


plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    // 解决 viewBinding 找不到BR
    kotlin("kapt")
    // 引入我们的gradle插件
    AndroidMavenPubHelper.apply(this)
}

// 引入buildSrc中的插件
//apply<io.hanlyjiang.gradle.android.AndroidMavenPubPlugin>()

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(30)

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

    buildFeatures {
        dataBinding = true
    }

    dataBinding {
        isEnabled = true
    }

}

dependencies {
    // StringRes 注解
    implementation("androidx.appcompat:appcompat:1.3.0")
    // SnackBar 需要
    implementation("com.google.android.material:material:1.4.0")
    implementation("org.jetbrains:annotations:21.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    //dependencies {
//
//    implementation 'androidx.core:core-ktx:1.3.2'
//    implementation 'androidx.appcompat:appcompat:1.2.0'
//    implementation 'com.google.android.material:material:1.3.0'
//    testImplementation 'junit:junit:4.+'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
//}
}

GitHelper.createShowGitRepoInfoTask(tasks)

apply(plugin = "com.github.hanlyjiang.android_maven_pub")

configure<io.hanlyjiang.gradle.android.AndroidMavenPubPluginExtension> {
    groupId.set("com.github.hanlyjiang")
    artifactId.set("pro-dialog")
    projectLocalRepoPath.set("local-maven-repo")
    versionName.set("0.0.1-SNAPSHOT")
    mavenPomAction.set(Action<MavenPom> {
        name.set("Android Dialog")
        description.set("Android Dialog")
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
