import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/* Add plugins from the web */
plugins {
    id("org.springframework.boot") version "2.2.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

/* Project details */
group = "com.lovika"
version = "0.0.1-SNAPSHOT"
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
    tasks.withType<>
    Gets a collection of all tasks of type "Test". Let's say it returned two tasks A and B.
    This then executes the code written inside the braces whenever tasks A and B execute.
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
