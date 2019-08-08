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

import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import java.util.*

plugins {
    idea
    java
    `java-library`
    `maven-publish`

    id("com.jfrog.bintray") version "1.8.4"
}

group = "org.menudocs"
version = "1.0.${getBuildNum()}"
val archivesBaseName = "paste-client-java"

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    api(group = "com.squareup.okhttp3", name = "okhttp", version = "4.0.1")
    api(group = "org.json", name = "json", version = "20180813")
    implementation(group = "me.duncte123", name = "reliqua", version = "2.4.8")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

val bintrayUpload: BintrayUploadTask by tasks
val compileJava: JavaCompile by tasks
val javadoc: Javadoc by tasks
val jar: Jar by tasks
val build: Task by tasks
val clean: Task by tasks
val test: Task by tasks
val check: Task by tasks

val sourcesJar = task<Jar>("sourcesJar") {
    classifier = "sources"
    from(sourceSets["main"].allJava)
}

val javadocJar = task<Jar>("javadocJar") {
    dependsOn(javadoc)
    classifier = "javadoc"
    from(javadoc.destinationDir)
}

publishing {
    publications {
        register("BintrayUpload", MavenPublication::class) {
            from(components["java"])

            artifactId = archivesBaseName
            groupId = project.group as String
            version = project.version as String

            artifact(javadocJar)
            artifact(sourcesJar)
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    setPublications("BintrayUpload")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "paste-client-java"
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/MenuDocs/paste-client-java.git"
        publish = true
        version(delegateClosureOf<BintrayExtension.VersionConfig>  {
            name = project.version as String
            released = Date().toString()
        })
    })
}

build.apply {
    dependsOn(jar)
    dependsOn(javadocJar)
    dependsOn(sourcesJar)

    jar.mustRunAfter(clean)
    javadocJar.mustRunAfter(jar)
    sourcesJar.mustRunAfter(javadocJar)
}

bintrayUpload.apply {
    dependsOn(clean)
    dependsOn(build)
    build.mustRunAfter(clean)

    onlyIf { System.getenv("BINTRAY_USER") != null }
    onlyIf { System.getenv("BINTRAY_KEY") != null }
}

fun getBuildNum(): String {
    return System.getenv("CIRCLE_BUILD_NUM") ?: "dev"
}
