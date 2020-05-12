import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
* Add plugins from the web
* Adding kotlin plug is super mandatory, without it, the entire build.gradle.kts file won't compile.
*/
plugins {
    id("org.springframework.boot") version "2.2.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

/* Project details */
group = "com.lovika"
version = "0.0.1-SNAPSHOT"
description = "kotlintestplugin"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

/* Add libraries from the web */
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    /*
        For adding libraries with their dependencies code as well, use api instead of implementation, like:
        api("com.something:libraryA:1.0.3")
     */

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

/*
    Gets a collection of all tasks of type "Test". Let's say it returned two tasks A and B.
    This then executes the code written inside the braces whenever com.lovika.tasks A and B execute.
 */
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

/* Create a fatJar */
tasks.withType<Jar>() {
    manifest {
        attributes("Main-Class': 'com.lovika.gradle.kotlindsl.Application.kt")
    }

    from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
}


/* Create custom plugin with the build script */
class MyPlugin1 : Plugin<Project> {
    override fun apply(project: Project) {
        // Add a task that uses configuration from the extension object
        project.task("welcome") {
            doLast {
                println("Welcome to this project: Running task-welcome")
            }
        }
    }
}

/* Apply the plugin created above */
apply<MyPlugin1>()

/* Executes task "printContent" before predefined task "processResources */
tasks.named("processResources") {
    dependsOn("welcome")
}

/* Plugin imported from another module */
apply<com.lovika.plugins.PrintingPlugin>()

tasks.named<com.lovika.tasks.Printer>("printContent") {
    userId = "lovikasaxena"
    content = "Hey, I'm creating my own Task to print"
    fileName = "/myFile.txt"
}

/* Executes task "printContent" before predefined task "processResources */
tasks.named("classes") {
    dependsOn("printContent")
}
