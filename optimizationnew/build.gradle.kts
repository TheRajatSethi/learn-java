plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.0"
    kotlin("plugin.noarg") version "2.1.20"
}

group = "rs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin & Kotlinx
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    implementation("com.charleskorn.kaml:kaml:0.73.0") // YAML

    // Database
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:2.1.10") // Used by jdbi
    implementation("org.jdbi:jdbi3-core:3.48.0")
    implementation("org.jdbi:jdbi3-sqlite:3.48.0")
    implementation("org.jdbi:jdbi3-kotlin:3.48.0")
    implementation("org.jdbi:jdbi3-kotlin-sqlobject:3.48.0")
    implementation("org.jdbi:jdbi3-sqlobject:3.48.0")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")

    // Logging
    implementation("org.slf4j:slf4j-simple:2.0.17")

    // TimeFold
    implementation("ai.timefold.solver:timefold-solver-core:1.20.0")

    // Testing
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(23)
}

// Plugin Configurations

noArg {
    annotation("ai.timefold.solver.core.api.domain.solution.PlanningSolution")
    annotation("ai.timefold.solver.core.api.domain.entity.PlanningEntity")
}
