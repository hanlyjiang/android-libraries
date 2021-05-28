# AndroidMavenPubPlugin 使用说明

本插件用于简化Android库上传到Maven中心仓库的配置，避免每个project的gradle中都放置一份重复较多的配置；

插件有如下功能：
1. 添加上传到Maven中心仓库的 publish 任务；
2. 支持配置上传时是否包含 javadoc 及源码；

## 使用步骤

### 引入插件

在需要使用的模块的build脚本中，引入我们的插件，同时引入 `maven-publish` 插件和` signing` 插件。

```kotlin
plugins {
    id("com.android.library")
    
    // 引入signing插件
    id("signing")
    // 引入maven-publish插件
    `maven-publish`
    // 引入 android_maven_pub 插件，注意这里设置 apply 为 false，表示引入但是不应用，我们需要放在android配置段定义之后再应用
    id("com.github.hanlyjiang.android_maven_pub") version ("0.0.4") apply (false)
}
```

### 配置 gradle 

在引入  AndroidMavenPubPlugin 插件之后，我们可以对插件进行配置

```kotlin
android {
    
}

// 需要先应用插件，在android配置完成之后，建议放在脚本最下方
apply(plugin = "com.github.hanlyjiang.android_maven_pub")

configure<io.hanlyjiang.gradle.android.AndroidMavenPubPluginExtension> {
    groupId.set("com.github.hanlyjiang")
    artifactId.set("android_common_utils")
    mavenPomAction.set(Action<MavenPom> {
        name.set("Android Common Utils Lib")
        description.set("Android Common Utils Library For HJ")
        url.set("https://github.com/hanlyjiang/lib_common_utils")
        properties.set(
            mapOf(
                "myProp" to "value",
                "prop.with.dots" to "anotherValue"
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
            connection.set("scm:git:git://github.com/hanlyjiang/lib_common_utils.git")
            developerConnection.set("scm:git:ssh://github.com/hanlyjiang/lib_common_utils.git")
            url.set("https://github.com/hanlyjiang/lib_common_utils")
        }
    })
}
```

> ⚠️**注意**： 
>
> * 可以看到我们这里并没有定义版本号，版本号从 `android.defaultConfig.versionName` 中取 ；
>
> * 同时，如果版本号结尾为 `-SNAPSHOT`，则会发布到 snapshot 仓库，如果没有，则发布到 release 仓库；

### maven仓库的属性文件配置

上传到maven center中心仓库需要进行一些账号申请和key的生成操作，可以参考[Jcenter 停止服务，说一说我们的迁移方案 - InfoQ 写作平台](https://xie.infoq.cn/article/e2345e367a139f37fc2fc0bbb) 来完成，我们需要将最后获取到的认证信息：

按如下配置，将对应的 value 的值更改为自己的账号及 key 的对应值，然后填入到 ` ~/.gradle/gradle.properties` 中即可

```properties
# 配置maven中心仓库访问账号
ossrhUsername=sonatype jira 账号的用户名
ossrhPassword=sonatype jira 账号的密码

# 配置签名信息
signing.keyId=公钥 ID 的后 8 位
signing.password=钥匙串的密码
signing.secretKeyRingFile=导出的 gpg 文件路径 如： /Users/hanlyjiang/.gnupg/secring.gpg 
```

> ⚠️注意： 这里的 key 是固定的，不要修改

### 执行上传任务

经过上面的配置，我们同步下 gradle ，对应引入了插件的项目中会生成若干任务：

![image-20210528094511334](images/image-20210528094511334.png)

其中：

* `jarJavadoc`, `jarSource`,`javadoc` 为我们生成的辅助任务
* `publish` 及 `generate` 开头的为 `mave-publish` 插件生成的任务，我们执行 `publish` 相关的任务即可发布 maven 库；

`publish ` 相关的任务有如下几个：

| Task Name                                         | Description                                                  |
| ------------------------------------------------- | ------------------------------------------------------------ |
| publish                                           | 等于执行了下面的所有任务                                     |
| publishAllPublicationsToProjectLocalRepository    | 将由此项目产生的所有Maven库发布到 ProjectLocal 存储库。 ProjectLocal 定义为当前项目的 build 目录的 repos 目录中，有两个子目录 `snapshots`及 `release`， 方便查看将要发布的生成物； |
| publishAllPublicationsToSonartypeRepository       | 将由此项目产生的所有 Maven 库发布到 Sonartype 存储库。       |
| publishReleasePublicationToMavenLocal             | 将由此项目产生的名为`release`的 Maven库发布到本机 maven 缓存库。 |
| publishReleasePublicationToProjectLocalRepository | 将由此项目产生的名为`release`的 Maven库发布到本机 ProjectLocal 库。 |
| publishReleasePublicationToSonartypeRepository    | 将由此项目产生的名为`release`的 Maven库发布到本机 Sonartype 库。 |
| publishToMavenLocal                               | 将由此项目产生的所有 Maven 库发布到 本机 maven 缓存库。      |

**说明：**

上面的任务由 `maven-publish` 的插件生成，该插件生成任务的规则使用 Publications 和 Repo 来组合实现，其中：

* publication 是我们定义的发布库；`android_maven_pub` 插件中，我们定义了一个名为`release` 的配置；
* repo 是我们定义的 maven 仓库的位置，`android_maven_pub` 定义了两个仓库的位置，分别名为 ProjectLocal 和 Sonartype ，另外`maven-publish`会给我们加上一个 MavenLocal 的配置（指向本机 maven 仓库缓存目录）
  * 其中 ProjectLocal 指向引入了该插件的项目（模块）的 `build/repos` 目录
  * `Sonartype` 则默认指向 maven 中心仓库的地址，可以通过配置来更改；

## 可配置项目说明

| 配置字段           | 说明                                                   | 默认值                                                       |
| ------------------ | ------------------------------------------------------ | ------------------------------------------------------------ |
| groupId            | maven 的 group id，需要设置为自己申请的                | 无默认值-必需填写                                            |
| artifactId         | library 的 id                                          | 无默认值-必需填写                                            |
| mavenPomAction     | 用于配置 pom 信息的字段                                | 无默认值-必需填写                                            |
| fromAndroidPubName | 表示发布的 android 的 aar 的类型，`release` 或 `debug` | `release`                                                    |
| releasesRepoUrl    | maven release 仓库的上传地址                           | `https://oss.sonatype.org/service/local/staging/deploy/maven2` |
| snapshotsRepoUrl   | maven snapshots 仓库的上传地址                         | `https://oss.sonatype.org/content/repositories/snapshots/`   |
| includeSourceJar   | 是否上传源码                                           | `true`                                                       |
| includeJavadocJar  | 是否上传 javadoc                                       | `true`                                                       |


## 常见问题

### 发布到 snapshot 

我们根据版本号来选择发布到的是 snapshot 还是 release 仓库，版本号从 `android->defaultConfig - versionName` 中读取，如：

```kotlin
android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0.0-SNAPSHOT")
    }
}
```

