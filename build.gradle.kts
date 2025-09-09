plugins {
    `java-library`
    `maven-publish`
    signing
}

group = "io.calimero"
version = "1.3.1"
val pkg = "org/usb4java"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

val darwin by tasks.registering(Jar::class) {
    val os = "darwin-aarch64"
    archiveClassifier.set(os)
    from("src/main/resources/$pkg/$os") {
        into("$pkg/$os")
    }
}

val darwinStatic by tasks.registering(Jar::class) {
    val os = "darwin-aarch64"
    archiveClassifier.set("$os-static")
    from("src/main/resources/static/$pkg/$os") {
        into("$pkg/$os")
    }
}

val win by tasks.registering(Jar::class) {
    val os = "win"
    archiveClassifier.set("$os-aarch64")
    from("src/main/resources/$pkg/${os}32-aarch64") {
        into("$pkg/${os}32-aarch64")
    }
}

val winStatic by tasks.registering(Jar::class) {
    val os = "win"
    archiveClassifier.set("$os-aarch64-static")
    from("src/main/resources/static/$pkg/${os}32-aarch64") {
        into("$pkg/${os}32-aarch64")
    }
}

tasks.named("build") {
    dependsOn("darwin", "darwinStatic", "win", "winStatic")
}

tasks.named<Jar>("jar") { // don't need default jar
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("mavenNative") {
            artifact(darwin.get()) {
                classifier = "darwin-aarch64"
            }
            artifact(darwinStatic.get()) {
                classifier = "darwin-aarch64-static"
            }
            artifact(win.get()) {
                classifier = "win-aarch64"
            }
            artifact(winStatic.get()) {
                classifier = "win-aarch64-static"
            }

            pom {
                name.set("usb4java AArch64 libraries")
                description.set("Native JNI libraries for macOS & Windows AArch64 used by usb4java")
                url.set("https://github.com/calimero-project/libusb4java-aarch64")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("LICENSE")
                    }
                }
                developers {
                    developer {
                        name.set("Boris Malinowsky")
                        email.set("b.malinowsky@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/calimero-project/libusb4java-aarch64.git")
                    url.set("https://github.com/calimero-project/libusb4java-aarch64.git")
                }
            }
        }
    }
    repositories {
        maven {
            name = "maven"
            val releasesRepoUrl = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://central.sonatype.com/repository/maven-snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)
        }
    }
}

signing {
    if (project.hasProperty("signing.keyId")) {
        sign(publishing.publications["mavenNative"])
    }
}
