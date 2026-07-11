plugins {
    id("com.possible-triangle.core") version("1.4-CUSTOM-SNAPSHOT")
    id("com.possible-triangle.common") version("1.4-CUSTOM-SNAPSHOT") apply(false)
    id("com.possible-triangle.fabric") version("1.4-CUSTOM-SNAPSHOT") apply(false)
    id("com.possible-triangle.neoforge") version("1.4-CUSTOM-SNAPSHOT") apply(false)
    id("net.mehvahdjukaar.candlelight") version("+") apply(false)

    id("org.ajoberstar.grgit") version("+") apply(false)
    id("org.quiltmc.gradle.licenser") version("+") apply(false)
    id("me.modmuss50.mod-publish-plugin") version("+") apply(false)
    id("com.gradleup.shadow") version("+") apply(false)
    checkstyle
}

checkstyle {
    configFile = rootProject.file("checkstyle.xml")
    toolVersion = "10.20.2"
}

val min_fabric_loader_version: String by project
val frozenlib_version: String by project

mod {
    additional.add("fabric_loader_version", ">=$min_fabric_loader_version")
    additional.add("minecraft_version", "~26.2-")
    additional.add("frozenlib_version", ">=${frozenlib_version.split('-').firstOrNull()}-")
    additional.add("protocol_version")
    additional.add("mod_description")
    additional.add("mod_credits")
    additional.add("mod_license")
    additional.add("mod_homepage")
    additional.add("mod_authors")
    additional.add("mod_github")
}

subprojects {
    apply(plugin = "com.possible-triangle.core")
    apply(plugin = "net.mehvahdjukaar.candlelight")

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xmaxerrs", "4000"))
        options.release.set(25)
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    dependencies {
        compileOnly("net.mehvahdjukaar:candlelight:+")
        compileOnly("net.frozenblock:frozenlib-common:${frozenlib_version}")
    }

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(25)
        withSourcesJar()
        withJavadocJar()
    }

    if (project.name != "ww-common") {
        afterEvaluate {
            tasks.findByName("compileJava")?.dependsOn(":ww-common:candleLightTransform")
        }
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
        maven("https://maven.caffeinemc.net/releases") {
            name = "CaffeineMC"
        }
        maven("https://maven.terraformersmc.com/releases") {
            name = "TerraformersMC (Biolith)"
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
}
