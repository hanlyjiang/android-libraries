plugins {
//    id("com.android.library")
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm").version("1.3.61")
    // 方便使用kotlin开发构建逻辑：
    // https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin
    id("signing")
    `maven-publish`
    `kotlin-dsl`
}



//tasks.register("javadoc", Javadoc::class.java) {
//    group = "publishing"
//    dependsOn("assemble")
//    source = android.sourceSets["main"].java.getSourceFiles()
//    classpath += project.files(android.bootClasspath + java.io.File.pathSeparator)
//    if (JavaVersion.current().isJava9Compatible) {
//        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
//    }
//    android.libraryVariants.forEach { libraryVariant ->
//        classpath += libraryVariant.javaCompileProvider.get().classpath
//    }
//    options.apply {
//        encoding("UTF-8")
//        charset("UTF-8")
//        isFailOnError = false
//
//        (this as StandardJavadocDocletOptions).apply {
////            addStringOption("Xdoclint:none")
//            links?.add("https://developer.android.google.cn/reference/")
//            links?.add("http://docs.oracle.com/javase/8/docs/api/")
//        }
//    }
//}
//
//tasks.register("jarSource", Jar::class.java) {
//    group = "publishing"
//    from(android.sourceSets["main"].java.srcDirs)
//    archiveClassifier.set("sources")
//}
//
//tasks.register("jarJavadoc", Jar::class.java) {
//    group = "publishing"
//    dependsOn("javadoc")
//    val javadoc: Javadoc = tasks.getByName("javadoc") as Javadoc
//    from(javadoc.destinationDir)
//    archiveClassifier.set("javadoc")
//}
