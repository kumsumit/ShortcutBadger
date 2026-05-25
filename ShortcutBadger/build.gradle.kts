plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
    signing
}

group = "me.leolin"
version = "1.1.8"

android {
    namespace = "me.leolin.shortcutbadger"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("proguard-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

val isReleaseBuild = !version.toString().contains("SNAPSHOT", ignoreCase = true)
val isRemotePublish = gradle.startParameter.taskNames.any {
    it.equals("publish", ignoreCase = true) ||
        it.endsWith(":publish", ignoreCase = true) ||
        it.contains("Sonatype", ignoreCase = true)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = project.group.toString()
            artifactId = "ShortcutBadger"
            version = project.version.toString()

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("ShortcutBadger")
                packaging = "aar"
                description.set("The ShortcutBadger makes your Android App show the count of unread messages as a badge on your App shortcut!")
                url.set("https://github.com/leolin310148/ShortcutBadger")

                scm {
                    url.set("https://github.com/leolin310148/ShortcutBadger")
                    connection.set("scm:git:https://github.com/leolin310148/ShortcutBadger.git")
                    developerConnection.set("scm:git:ssh://git@github.com/leolin310148/ShortcutBadger.git")
                }

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("leolin310148")
                        name.set("Leo Lin")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "sonatype"
            url = uri(
                if (isReleaseBuild) {
                    providers.gradleProperty("RELEASE_REPOSITORY_URL")
                        .orElse("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                        .get()
                } else {
                    providers.gradleProperty("SNAPSHOT_REPOSITORY_URL")
                        .orElse("https://oss.sonatype.org/content/repositories/snapshots/")
                        .get()
                }
            )
            credentials {
                username = providers.gradleProperty("NEXUS_USERNAME").orElse("").get()
                password = providers.gradleProperty("NEXUS_PASSWORD").orElse("").get()
            }
        }
    }
}

signing {
    isRequired = isReleaseBuild && isRemotePublish
    sign(publishing.publications["release"])
}
