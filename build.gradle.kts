/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java Library project to get you started.
 * For more details take a look at the Java Libraries chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.5.1/userguide/java_library_plugin.html
 */

plugins {
    // Apply the java-library plugin to add support for Java Library
    `java-library`
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    api(group = "com.squareup.okhttp3", name = "okhttp", version = "4.0.1")
    implementation(group = "me.duncte123", name = "reliqua", version = "2.4.8")

    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
}