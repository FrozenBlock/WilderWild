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
    maven("https://maven.quiltmc.org/repository/release") {
        name = "Quilt"
    }
    maven("https://maven.shedaniel.me/")
    exclusiveContent {
        forRepository {
            maven {
                name = "Sponge"
                url = uri("https://repo.spongepowered.org/repository/maven-public")
            }
        }
        filter { includeGroupAndSubgroups("org.spongepowered") }
    }
    maven {
        name = "BlameJared"
        url = uri("https://maven.blamejared.com")
    }
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://thedarkcolour.github.io/KotlinForForge/") {
        name = "KotlinForForge"
        content { includeGroup("thedarkcolour") }
    }
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
