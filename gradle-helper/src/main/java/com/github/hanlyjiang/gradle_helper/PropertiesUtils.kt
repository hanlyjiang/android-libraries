package com.github.hanlyjiang.gradle_helper

import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.util.*


/**
 * Get boolean Properties from local and project properties
 *
 * order:
 * - local.properties
 * - project properties
 *  @author hanlyjiang on 2022/3/12-12:12 AM
 *  @version 1.1
 */
object PropertiesUtils {

    /**
     * Get Boolean prop from local and project properties
     *
     * @param project Project to get search properties
     * @param name properties name
     * @param defaultValue defaultValue if properties not found in all properties
     * @return properties(if found) or defaultValue
     */
    fun getBoolProperties(project: Project, name: String, defaultValue: Boolean): Boolean {
        val value: String? = getPropValue(project, name)
        return value?.toBoolean() ?: defaultValue
    }

    /**
     * Get Int prop from local and project properties
     *
     * @param project Project to get search properties
     * @param name properties name
     * @param defaultValue defaultValue if properties not found in all properties
     * @return properties(if found) or defaultValue
     */
    fun getIntProperties(project: Project, name: String, defaultValue: Int): Int {
        val value: String? = getPropValue(project, name)
        return value?.toInt() ?: defaultValue
    }

    /**
     * Get String prop from local and project properties
     *
     * @param project Project to get search properties
     * @param name properties name
     * @param defaultValue defaultValue if properties not found in all properties
     * @return properties(if found) or defaultValue
     */
    fun getStringProperties(project: Project, name: String, defaultValue: String): String {
        val value: String? = getPropValue(project, name)
        return value ?: defaultValue
    }

    /**
     * Write properties to local.properties
     *
     * @param project project to locate local.properties file
     * @param name properties name
     * @param value properties value
     * @return  write result
     */
    fun writeLocalProperties(project: Project, name: String, value: String): Boolean {
        val localProp = project.rootProject.file("local.properties")
        if (!localProp.exists()) {
            return false
        }
        val p = Properties()
        try {
            FileInputStream(localProp).use {
                p.load(it)
                it.close()
            }
            p.setProperty(name, value)
            FileWriter(localProp).use {
                p.store(it, "Write $name")
            }
        } catch (e: IOException) {
            project.logger.log(LogLevel.ERROR, "open local.properties failed!")
            return false
        }
        return true
    }


    private fun getPropValue(project: Project, name: String?): String? {
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
        return value
    }
}
