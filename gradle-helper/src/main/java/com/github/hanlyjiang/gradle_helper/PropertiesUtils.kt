package com.github.hanlyjiang.gradle_helper

import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 *
 * @author hanlyjiang on 2022/3/12-12:12 AM
 * @version 1.0
 */
/**
 * Get boolean Properties from local and project properties
 * order:
 * - local.properties
 * - project properties
 */
object PropertiesUtils {
    fun getProperties(project: Project, name: String?, defaultValue: Boolean): Boolean {
        var value: String? = null
        val localProp = project.rootProject.file("local.properties")
        if (localProp.exists()) {
            val p = Properties()
            try {
                p.load(FileInputStream(localProp))
                value = p[name] as String?
            } catch (e: IOException) {
                project.logger.log(LogLevel.ERROR, "open local.properties failed!")
            }
        }
        if (value == null) {
            value = project.properties[name] as String?
        }
        return if (value == null) defaultValue else "true" == value
    }
}
