plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")

    // 引入我们本地仓库中的gradle插件
    id("com.github.hanlyjiang.inline_apk_to_assets") version ("0.0.4") apply (false)
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
//    implementation("com.github.hanlyjiang:apf_library:1.0.1")
    implementation("com.github.hanlyjiang:apf_library-debug:1.0.1-SNAPSHOT")

}

apply(plugin = "com.github.hanlyjiang.inline_apk_to_assets")


//plugins.apply(io.hanlyjiang.gradle.android.PluginAssetsCopyPlugin::class.java)


