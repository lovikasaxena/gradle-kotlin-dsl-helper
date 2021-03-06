package com.gradle.kotlindsl.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class FileCreator: DefaultTask() {

    /* Task needs an empty constructor, 
    hence properties not defined in the primary constructor */
    lateinit var userId: String
    lateinit var content: String
    lateinit var fileName: String

    @TaskAction /* This annotation marks a method to be a Gradle task */
    fun perform() {
        val pathname = getProject().getRootDir().getAbsolutePath() + fileName
        File(pathname).writeText(content)

        println("Content printed to file successfully by user: $userId")
    }
}
