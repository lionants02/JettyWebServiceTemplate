/*
 * MIT License
 *
 * Copyright (c) 2022 NSTDA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "1.7.20"
    id("org.jmailen.kotlinter") version "3.12.0"
    id("com.moonlitdoor.git-version") version "0.1.1"
    id("org.jetbrains.dokka") version "1.7.10"
}

group = "nstda.hii.webservice"
version = "0.1" //gitVersion

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    //Application dependency block

    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.10")

    implementation(kotlin("stdlib-jdk8"))
    implementation("args4j:args4j:2.33")

    val log4jVersion = "2.19.0"
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.0")

    implementation("redis.clients:jedis:4.2.3")

    implementation("com.google.code.gson:gson:2.9.0")

    val fuelVersion = "2.3.1"
    implementation("com.github.kittinunf.fuel:fuel:$fuelVersion")
    implementation("com.github.kittinunf.fuel:fuel-gson:$fuelVersion")

    implementation("com.auth0:java-jwt:4.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("commons-codec:commons-codec:1.15")
    implementation("com.fatboyindustrial.gson-javatime-serialisers:gson-javatime-serialisers:1.1.2")
    testImplementation("com.github.fppt:jedis-mock:1.0.4")
}

dependencies {
    //Core framework dependency block
    val jerseyVersion = "3.0.8"
    implementation("org.glassfish.jersey.core:jersey-common:$jerseyVersion")
    implementation("org.glassfish.jersey.inject:jersey-hk2:$jerseyVersion")
    implementation("org.glassfish.jersey.containers:jersey-container-servlet-core:$jerseyVersion")
    implementation("org.glassfish.jersey.containers:jersey-container-jetty-servlet:$jerseyVersion")
    // Remove jersey-media-json-jackson production
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:$jerseyVersion")
    testImplementation("org.glassfish.jersey.test-framework:jersey-test-framework-core:$jerseyVersion")
    testImplementation("org.glassfish.jersey.test-framework.providers:jersey-test-framework-provider-grizzly2:$jerseyVersion")

    val jettyVersion = "11.0.12"
    implementation("org.eclipse.jetty:jetty-server:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-servlet:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-http:$jettyVersion")

    //testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.amshove.kluent:kluent:1.68")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.named<Jar>("jar") {

    manifest {
        attributes["Implementation-Title"] = "HII Webservice example"
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

tasks.test {
    useJUnitPlatform()
}
