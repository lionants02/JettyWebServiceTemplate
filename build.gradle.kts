/*
 * Copyright (c) 2019 NSTDA
 *   National Science and Technology Development Agency, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.70"
    id("org.jmailen.kotlinter") version "1.26.0"
    id("com.moonlitdoor.git-version") version "0.1.1"
    id("org.jetbrains.dokka") version "0.10.1"
}

group = "nstda.hii.webservice"
version = "0.1" //gitVersion

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    //Application dependency block

    dokkaRuntime("org.jetbrains.dokka:dokka-fatjar:0.10.1")

    implementation(kotlin("stdlib-jdk8"))
    implementation("args4j:args4j:2.33")

    val log4jVersion = "2.13.2"
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
    implementation("io.github.microutils:kotlin-logging:1.7.9")

    implementation("redis.clients:jedis:3.0.1")

    implementation("com.google.code.gson:gson:2.8.5")

    val fuelVersion = "2.2.2"
    implementation("com.github.kittinunf.fuel:fuel:$fuelVersion")
    implementation("com.github.kittinunf.fuel:fuel-gson:$fuelVersion")

    implementation("com.auth0:java-jwt:3.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")
    implementation("commons-codec:commons-codec:1.12")
    implementation("com.fatboyindustrial.gson-javatime-serialisers:gson-javatime-serialisers:1.1.1")
    testImplementation("com.github.fppt:jedis-mock:0.1.13")
}

dependencies {
    //Core framework dependency block
    val jerseyVersion = "2.30.1"
    implementation("org.glassfish.jersey.core:jersey-common:$jerseyVersion")
    implementation("org.glassfish.jersey.inject:jersey-hk2:$jerseyVersion")
    implementation("org.glassfish.jersey.containers:jersey-container-servlet-core:$jerseyVersion")
    implementation("org.glassfish.jersey.containers:jersey-container-jetty-servlet:$jerseyVersion")
    // Remove jersey-media-json-jackson production
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:$jerseyVersion")
    testImplementation("org.glassfish.jersey.test-framework:jersey-test-framework-core:$jerseyVersion")
    testImplementation("org.glassfish.jersey.test-framework.providers:jersey-test-framework-provider-grizzly2:$jerseyVersion")

    val jettyVersion = "9.4.28.v20200408"
    implementation("org.eclipse.jetty:jetty-server:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-servlet:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-http:$jettyVersion")

    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.28.2")
    testImplementation("org.amshove.kluent:kluent:1.53")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}

tasks.named<Jar>("jar") {
    configurations.compileClasspath.get().forEach { if (it.isDirectory) from(it) else from(zipTree(it)) }

    this.setProperty("archiveFileName","webservice.jar")

    manifest {
        attributes["Main-Class"] = "nstda.hii.webservice.Main"
    }

    exclude(
        "META-INF/*.RSA",
        "META-INF/*.SF",
        "META-INF/*.DSA",
        "META-INF/DEPENDENCIES",
        "META-INF/NOTICE*",
        "META-INF/LICENSE*",
        "about.html"
    )
}
tasks.dokka {
    outputFormat = "javadoc"
    outputDirectory = "$buildDir/javadoc"
}

