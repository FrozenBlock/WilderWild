plugins {
    `java-library`
    `maven-publish`
}

val mod_id: String by project
val mod_name: String by project
val mod_author: String by project
val minecraft_version: String by project
val mod_version: String by project
val java_version: String by project

version = "$mod_version-mc$minecraft_version"

base {
    archivesName = "${mod_id}-${project.name}-${minecraft_version}"
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(java_version.toInt())
    withSourcesJar()
    withJavadocJar()
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

tasks.named<Jar>("sourcesJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${mod_name}" }
    }
}

tasks.named<Jar>("jar") {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${mod_name}" }
    }
    manifest {
        attributes(
            mapOf(
                "Specification-Title"    to mod_name,
                "Specification-Vendor"   to mod_author,
                "Specification-Version"  to archiveVersion.get(),
                "Implementation-Title"   to project.name,
                "Implementation-Version" to archiveVersion.get(),
                "Implementation-Vendor"  to mod_author,
                "Built-On-Minecraft"     to minecraft_version
            )
        )
    }
}
