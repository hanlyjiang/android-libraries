package io.hanlyjiang.gradle.android

import org.gradle.api.tasks.TaskContainer


object GitHelper {
    /**
     * 创建输出 git Repo 信息的任务
     */
    fun createShowGitRepoInfoTask(tasks: TaskContainer) {
        tasks.create("showGitRepoInfo") {
            group = "help"
            doLast {
                println("${getGitBranch()}/${getGitRevision()}")
            }
        }
    }


    fun String.execute(): String {
        val process = Runtime.getRuntime().exec(this)
        return with(process.inputStream.bufferedReader()) {
            readText()
        }
    }

    /**
     * Get git revision with work tree status
     *
     * @return
     */
    fun getGitRevision(): String {
        val rev = "git rev-parse --short HEAD".execute().trim()
        val stat = "git diff --stat".execute().trim()
        return if (stat.isEmpty()) {
            rev
        } else {
            "$rev-dirty"
        }
    }

    /**
     * Get git branch name
     *
     * @return
     */
    fun getGitBranch(): String {
        return "git rev-parse --abbrev-ref HEAD".execute().trim()
    }
}

