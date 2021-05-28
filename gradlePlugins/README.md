# Android Gradle Plugins Dev

## 使用

包含几个Android Gradle 插件，具体查看插件对应的文档：

* [AndroidMavenPubPlugin使用说明](doc/AndroidMavenPubPlugin使用说明.md) ： 简化 Android Library 发布到 Maven Center 仓库的配置；
* [InlineApkToAssetsPlugin插件使用说明](InlineApkToAssetsPlugin使用说明.md) ： 自动拷贝 apk 到指定 Application 模块的 assets 目录，可简化插件化开发中内置插件或者测试过程中的手动操作；

## 插件开发

### 测试插件

提升版本号，将插件发布到本地 maven 缓存中，然后在引入的 module 中同时也提升版本号即可；

通过如下命令可以将插件发布到本地 maven 缓存：

```shell
./gradlew gradlePlugins:publishAndroidMavenPubPluginPluginMarkerMavenPublicationToMavenLocal
```

然后在引入的 bulid 脚本中提升版本号：

```kotlin
// 引入我们本地仓库中的gradle插件
id("com.github.hanlyjiang.android_maven_pub") version ("0.0.4") apply (false)
```

重新同步引入了插件的模块即可生效；

### 调试插件

先在插件代码中打上断点，然后在 Android Studio 的 Gradle 工具窗口中，选中引入了插件的模块的对应的任务，右键，选择 `Debug xxxx` 执行即可。执行到断点处就会停下来；

* debug 初始化阶段的流程可以选择 `tasks` 任务来 debug
* debug 任务执行的则选择对应的任务来 debug

触发断点调试后的调试流程和 Android / Java 开发调试无异；

### 发布插件

执行 publishPlugins 任务即可发布插件到 [Gradle Plugin Portal](https://plugins.gradle.org/)

```shell
./gradlew gradlePlugins:publishPlugins
```

完成后访问 [插件页面](https://plugins.gradle.org/u/hanlyjiang) 即可看到发布的插件

