package com.lovika.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.lovika.tasks.Printer

open class PrintingPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        /* Register our defined task with the plugin*/
        project.tasks
                .create("printContent", Printer::class.java)
                .run {
                    /* Giving default values */
                    userId = "UNKNOWN"
                    content = "Print me"
                }
    }
}