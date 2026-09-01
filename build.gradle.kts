import org.kohsuke.github.GHReleaseBuilder
import org.kohsuke.github.GitHub

plugins {
    id("net.frozenblock.triangle.core") version("+")
    id("net.frozenblock.triangle.common") version("+") apply(false)
    id("net.frozenblock.triangle.fabric") version("+") apply(false)
    id("net.frozenblock.triangle.neoforge") version("+") apply(false)
    id("net.frozenblock.candlelight") version("+") apply(false)

    id("org.quiltmc.gradle.licenser") version("+") apply(false)
    id("com.gradleup.shadow") version("+") apply(false)
    checkstyle
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.kohsuke:github-api:1.326")
    }
}

checkstyle {
    configFile = rootProject.file("checkstyle.xml")
    toolVersion = "10.20.2"
}

val min_fabric_loader_version: String by project
val frozenlib_version: String by project

val changelogText = run {
    val split = file("CHANGELOG.md").readText().split("-----------------")
    check(split.size == 2) { "Malformed changelog" }
    split[1].trim()
}

fun mainJarTask(project: Project) =
    if (project.tasks.names.contains("shadowJar")) project.tasks.named("shadowJar")
    else project.tasks.named("jar")

val githubRelease by tasks.registering {
    val fabricJar = mainJarTask(project(":ww-fabric"))
    val neoforgeJar = mainJarTask(project(":ww-neoforge"))
    dependsOn(fabricJar, neoforgeJar)

    val token = env["GITHUB_TOKEN"]
    val repository = mod.repository.get()
    val tag = project(":ww-fabric").version.toString()
    val releaseTitle = "Wilder Wild $tag"
    val isPrerelease = mod.releaseType.get() != "release"
    val commitish = env["GITHUB_SHA"]

    onlyIf { !token.isNullOrEmpty() }

    doLast {
        val github = GitHub.connectUsingOAuth(token)
        val repo = github.getRepository(repository)

        repo.getReleaseByTagName(tag)?.delete()

        val releaseBuilder = GHReleaseBuilder(repo, tag)
        releaseBuilder.name(releaseTitle)
        releaseBuilder.body(changelogText)
        releaseBuilder.prerelease(isPrerelease)
        if (commitish != null) releaseBuilder.commitish(commitish)

        val release = releaseBuilder.create()
        release.uploadAsset(fabricJar.get().outputs.files.singleFile, "application/java-archive")
        release.uploadAsset(neoforgeJar.get().outputs.files.singleFile, "application/java-archive")
    }
}

val publishMod by tasks.registering {
    dependsOn(tasks.named("upload"))
    dependsOn(githubRelease)
}

subprojects {
    apply(plugin = "net.frozenblock.triangle.core")
    apply(plugin = "net.frozenblock.candlelight")

    mod {
        additional.add("fabric_loader_version", ">=$min_fabric_loader_version")
        additional.add("frozenlib_version", ">=${frozenlib_version.split('-').firstOrNull()}-")
        additional.add("protocol_version")
        additional.add("mod_description")
        additional.add("mod_credits")
        additional.add("mod_license")
        additional.add("mod_homepage")
        additional.add("mod_authors")
        additional.add("mod_github")
    }

    val mavenUrl = env["MAVEN_URL"]
    val mavenUsername = env["MAVEN_USERNAME"]
    val mavenPassword = env["MAVEN_PASSWORD"]

    if (mavenUrl != null && mavenUsername != null && mavenPassword != null) {
        upload {
            maven {
                repositories {
                    maven(mavenUrl) {
                        name = "FrozenBlock"
                        credentials {
                            username = mavenUsername
                            password = mavenPassword
                        }
                    }
                }
            }
        }
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xmaxerrs", "4000"))
        options.release.set(25)
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    dependencies {
        compileOnly("net.frozenblock:candlelight:+")
        compileOnly("net.frozenblock:frozenlib-common:${frozenlib_version}")
    }

    repositories {
        maven("https://maven.frozenblock.net/release") {
            name = "FrozenBlock"
        }
        maven("https://maven.frozenblock.net/snapshot") {
            name = "FrozenBlock Snapshot"
        }

        exclusiveContent {
            forRepository {
                maven("https://repo.spongepowered.org/repository/maven-public") {
                    name = "Sponge"
                }
            }
            filter { includeGroupAndSubgroups("org.spongepowered") }
        }
        maven("https://maven.minecraftforge.net/") {
            name = "Forge"
        }
        maven("https://thedarkcolour.github.io/KotlinForForge/") {
            name = "KotlinForForge"
            content {
                includeGroup("thedarkcolour")
            }
        }
        maven("https://registry.somethingcatchy.net/repository/maven-releases/") { // Candlelight & Triangle
            name = "SomethingCatchy (MehVahdJukaar)"
        }

        maven("https://maven.quiltmc.org/repository/release") {
            name = "Quilt"
        }
        maven("https://maven.blamejared.com") {
            name = "BlameJared"
        }
        maven("https://maven.jamieswhiteshirt.com/libs-release") {
            name = "JamiesWhiteShirt"
            content {
                includeGroup("com.jamieswhiteshirt")
            }
        }
        maven("https://maven.shedaniel.me/") {
            name = "Shedaniel"
        }
        maven("https://maven.frozenblock.net/caffeinemc") {
            name = "CaffeineMC"
            content {
                includeGroup("net.caffeinemc")
            }
        }
        maven("https://maven.terraformersmc.com") {
            name = "TerraformersMC"
            content {
                includeGroup("com.terraformersmc")
            }
        }

        exclusiveContent {
            forRepository {
                maven("https://api.modrinth.com/maven") {
                    name = "Modrinth"
                }
            }
            filter {
                includeGroup("maven.modrinth")
            }
        }
        maven("https://jitpack.io") {
            name = "Jitpack"
        }
        mavenCentral()
    }

    tasks {
        withType(JavaCompile::class) {
            options.encoding = "UTF-8"
            options.release.set(25)
            options.isFork = true
            options.isIncremental = true
        }

        withType(Test::class) {
            maxParallelForks = Runtime.getRuntime().availableProcessors().div(2)
        }
    }
}
