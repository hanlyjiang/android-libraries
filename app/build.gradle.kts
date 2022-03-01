plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
}

android {
    compileSdk = properties["_compileSdk"] as Int
    buildToolsVersion = properties["_buildTools"] as String
    defaultConfig {
        applicationId = "cn.hanlyjiang.hjapf"
        minSdk = properties["_minSdk"] as Int
        targetSdk = properties["_targetSdk"] as Int

        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    dataBinding {
        isEnabled = true
    }
}

dependencies {
    DependenciesMgr.applyTestDependencies(this)

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    // 引入我们自己的库
    implementation(project(path = ":apf-library"))
    implementation(project(path = ":lib_common_utils"))
    implementation(project(path = ":lib_common_ui"))
    implementation(project(path = ":lib_ui_prodialog"))
    implementation(project(path = ":lib_di_sample"))

//    implementation("com.github.hanlyjiang:android_common_utils:1.0.3-SNAPSHOT")
}