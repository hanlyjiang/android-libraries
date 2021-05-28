# InlineApkToAssetsPlugin使用说明

## 插件用途

引入插件后，在该模块编译时（`assembleDebug`/`assembleRelease`) ，会自动将该模块的 apk 文件拷贝到定义的另外一个 app 模块的 assets 目录中。

## 用法

### 引入插件

需要使用的 application 模块的 build.gradle.kts  中引入插件：

```kotlin
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")

    // 引入我们的gradle插件
    id("com.github.hanlyjiang.inline_apk_to_assets") version ("0.0.4") apply (false)
}
```

### 配置

同样还是上面的 build.gradle.kts 文件中，配置

```kotlin
android {
    
}

// 在配置了 android 段之后，应用我们的插件
apply(plugin = "com.github.hanlyjiang.inline_apk_to_assets")
// 定义插件的配置
configure<io.hanlyjiang.gradle.android.InlineApkToAssetsPluginExtension> {
    // 设置主项目的名称，也就是我们的 apk 需要拷贝到的项目的名称
    hostProjectName.set("app")
}
```

### 使用

正常 `assembleDebug`/`assembleRelease` 即可；

完成后会有类似如下输出：

```shell
> Task :plugin_01:copyApkToHostAssetsDebug
:+++> Copy /Users/hanlyjiang/Wksp/personal/learn/android/HJAPF/plugin_01/build/outputs/apk/debug/plugin_01-debug.apk to /Users/hanlyjiang/Wksp/personal/learn/android/HJAPF/app/src/main/assets
:+++> Copy Success!!!

BUILD SUCCESSFUL in 5s
28 actionable tasks: 2 executed, 26 up-to-date
10:56:52 AM: Task execution finished 'plugin_01:assembleDebug'.
```

`app/src/main/assets` 中的文件会去掉 `-debug` （`-variant`）的名称，如 `plugin_01-debug.apk` 在 assets 会被重命名为 `plugin_01.apk` 