import org.gradle.api.publish.maven.MavenPom
import io.hanlyjiang.gradle.android.AndroidMavenPubPlugin
import io.hanlyjiang.gradle.android.AndroidMavenPubPluginExtension

plugins {
    id("com.android.library")
    id("signing")
    `maven-publish`
//    kotlin("android")
//    kotlin("android.extensions")
}



android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0.1-SNAPSHOT")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

apply<AndroidMavenPubPlugin>()

configure<AndroidMavenPubPluginExtension> {
    groupId.set("com.github.hanlyjiang")
    artifactId.set("android_common_utils")
    mavenPomAction.set(Action<MavenPom> {
        name.set("Android Common Utils Lib")
        description.set("Android Common Utils Library For HJ")
        url.set("https://github.com/hanlyjiang/lib_common_utils")
        properties.set(mapOf(
                "myProp" to "value",
                "prop.with.dots" to "anotherValue"
        ))
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
            connection.set("scm:git:git://github.com/hanlyjiang/lib_common_utils.git")
            developerConnection.set("scm:git:ssh://github.com/hanlyjiang/lib_common_utils.git")
            url.set("https://github.com/hanlyjiang/lib_common_utils")
        }
    })
}

