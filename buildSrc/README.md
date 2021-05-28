# 项目说明

本仓库用于编写gradle相关的辅助任务，现有如下工具插件：
1. AndroidMavenPubPlugin : 用于简化Android库上传到Maven中心仓库的配置步骤；
2. PluginAssetsCopyPlugin : 用于简化插件框架的插件内置宿主的步骤；



## 使用buildSrc中的插件
```kotlin
import io.hanlyjiang.gradle.android.AndroidMavenPubPlugin
// 引入 AndroidMavenPubPlugin 插件
apply<AndroidMavenPubPlugin>()
```