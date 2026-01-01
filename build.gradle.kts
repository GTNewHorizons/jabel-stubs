plugins {
    `java-library`
    `maven-publish`
    id("com.palantir.git-version") version "3.1.0"
}

val gitVersion: groovy.lang.Closure<String> by extra

group = "com.github.GTNewHorizons"
version = System.getenv("VERSION") ?: gitVersion()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "jabel-stubs"
            from(components["java"])
            pom {
                name.set("Jabel Stubs")
                description.set("Stub annotation for Jabel @Desugar compatibility when using JVM Downgrader")
                url.set("https://github.com/GTNewHorizons/jabel-stubs")
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("https://nexus.gtnewhorizons.com/repository/releases/")
            credentials {
                username = System.getenv("MAVEN_USER") ?: "NONE"
                password = System.getenv("MAVEN_PASSWORD") ?: "NONE"
            }
        }
    }
}
