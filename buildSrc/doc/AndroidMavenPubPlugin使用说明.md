# AndroidMavenPubPlugin 使用说明

本插件用于简化Android库上传到Maven中心仓库的配置，避免每个project的gradle中都放置一份重复较多的配置；

插件有如下功能：
1. 添加上传到Maven中心仓库的publish任务；
2. 支持配置上传时是否包含javadoc及源码；

## 使用步骤

### 引入插件

在需要使用的模块的build脚本中，引入我们的插件，同时引入maven-publish插件和signing 插件。

```kotlin
plugins {
    id("com.android.library")
    id("signing")
    `maven-publish`
}

import io.hanlyjiang.gradle.android.AndroidMavenPubPlugin
// 引入 AndroidMavenPubPlugin 插件
apply<AndroidMavenPubPlugin>()
```

### 配置

在引入  AndroidMavenPubPlugin 插件之后，我们可以对插件进行配置

```kotlin
apply<AndroidMavenPubPlugin>()

// 进行配置 
configure<AndroidMavenPubPluginExtension> {
    groupId.set("com.github.hanlyjiang")
    artifactId.set("android_common_utils")
    mavenPomAction.set(Action<MavenPom> {
        name.set("Android Common Utils Lib")
        description.set("Android Common Utils Library For HJ")
        url.set("https://github.com/hanlyjiang/lib_common_utils")
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

```


## 常见问题