/*
 *    Copyright 2019 Duncan Sterken
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import java.util.*

plugins {
    idea
    java
    `java-library`
    `maven-publish`
}

group = "org.menudocs"
version = "1.1.${getBuildNum()}"
val archivesBaseName = "paste-client-java"

repositories {
    mavenCentral()
    maven("https://duncte123.jfrog.io/artifactory/maven")
}

dependencies {
    api(group = "com.squareup.okhttp3", name = "okhttp", version = "3.14.9")
    api(group = "org.json", name = "json", version = "20210307")
    api(group = "me.duncte123", name = "reliqua", version = "2.5.1") {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

val compileJava: JavaCompile by tasks
val javadoc: Javadoc by tasks
val jar: Jar by tasks
val build: Task by tasks
val publish: Task by tasks
val clean: Task by tasks
val test: Task by tasks
val check: Task by tasks

val sourcesJar = task<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allJava)
}

val javadocJar = task<Jar>("javadocJar") {
    dependsOn(javadoc)
    archiveClassifier.set("javadoc")
    from(javadoc.destinationDir)
}

publishing {
    repositories {
        maven {
            name = "jfrog"
            url = uri("https://duncte123.jfrog.io/artifactory/maven/")
            credentials {
                username = System.getenv("JFROG_USERNAME")
                password = System.getenv("JFROG_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("jfrog") {
            from(components["java"])

            artifactId = archivesBaseName
            groupId = project.group as String
            version = project.version as String

            artifact(javadocJar)
            artifact(sourcesJar)
        }
    }
}

build.apply {
    dependsOn(jar)
    dependsOn(javadocJar)
    dependsOn(sourcesJar)

    jar.mustRunAfter(clean)
    javadocJar.mustRunAfter(jar)
    sourcesJar.mustRunAfter(javadocJar)
}

publish.apply {
    dependsOn(build)

    onlyIf {
        System.getenv("JFROG_USERNAME") != null && System.getenv("JFROG_TOKEN") != null
    }
}

fun getBuildNum(): String {
    return System.getenv("GITHUB_RUN_NUMBER") ?: "dev"
}
