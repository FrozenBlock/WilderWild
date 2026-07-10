plugins {
    id("com.possible-triangle.core")
    id("com.possible-triangle.common") apply(false)
    id("com.possible-triangle.fabric") apply(false)
    id("com.possible-triangle.neoforge") apply(false)
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

mod {
    additional.add("fabric_loader_version")
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
    }

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(25)
        withSourcesJar()
        withJavadocJar()
    }

    repositories {
        maven("https://maven.frozenblock.net/release") {
            name = "FrozenBlock"
        }
        maven("https://maven.quiltmc.org/repository/release") {
            name = "Quilt"
        }
        maven("https://maven.shedaniel.me/")
        exclusiveContent {
            forRepository {
                maven("https://repo.spongepowered.org/repository/maven-public") {
                    name = "Sponge"
                }
            }
            filter { includeGroupAndSubgroups("org.spongepowered") }
        }
        maven("https://maven.blamejared.com") {
            name = "BlameJared"
        }
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://thedarkcolour.github.io/KotlinForForge/") {
            name = "KotlinForForge"
            content {
                includeGroup("thedarkcolour")
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
        maven("https://registry.somethingcatchy.net/repository/maven-releases/") { // Candlelight & Triangle
            name = "SomethingCatchy (MehVahdJukaar)"
        }
    }
}
