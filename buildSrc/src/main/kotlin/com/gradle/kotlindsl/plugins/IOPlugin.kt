package com.gradle.kotlindsl.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.gradle.kotlindsl.tasks.FileCreator

open class IOPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        /* Register our defined task with the plugin*/
        project.tasks
                .create("createFile", FileCreator::class.java)
                .run {
                    /* Giving default values */
                    userId = "UNKNOWN"
                    content = "Print me"
                    fileName = "/myFile.txt"
                }
    }
}