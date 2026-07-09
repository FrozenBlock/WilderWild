plugins {
    id("ww-multiloader-loader")
    id("dev.architectury.loom-no-remap")
    id("org.quiltmc.gradle.licenser")
    checkstyle
}

checkstyle {
    configFile = rootProject.file("checkstyle.xml")
    toolVersion = "10.20.2"
}

val mod_id: String by project
val mod_version: String by project
val minecraft_version: String by project
val maven_group: String by project
val archives_base_name: String by project

val frozenlib_version: String by project
val cloth_config_version: String by project
val biolith_version: String by project

val neoforge_version: String by project
val neoforge_loader_version_range: String by project

val sodium_version: String by project
val run_sodium: String by project
val shouldRunSodium = run_sodium == "true"

val neoforgeSnapshotMaven = findProperty("neoforge_snapshot_maven") as String?

base {
    archivesName.set("$archives_base_name-neoforge")
}

version = getModVersion()
group = maven_group

repositories {
    maven("https://maven.neoforged.net/releases") { name = "NeoForged" }
    if (!neoforgeSnapshotMaven.isNullOrBlank()) {
        maven(neoforgeSnapshotMaven) { name = "NeoForge Snapshots" }
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
}

loom {
    accessWidenerPath = rootProject.file("common/src/main/resources/wilderwild.classtweaker")
    enableTransitiveAccessWideners = true

    interfaceInjection {
        enableDependencyInterfaceInjection = true
    }

    runs {
        named("client") {
            client()
            name("NeoForge Client")
            ideConfigGenerated(true)
            //gameDirectory.set(project.mkdir(project.file("runs/client")))
        }
        named("server") {
            server()
            name("NeoForge Server")
            ideConfigGenerated(true)
            project.file("runs/server").parentFile?.mkdirs()
            //gameDirectory.set(project.mkdir(project.file("runs/server")))
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    "neoForge"("net.neoforged:neoforge:$neoforge_version")

    api("net.frozenblock:frozenlib-neoforge:${frozenlib_version}")

    implementation("me.shedaniel.cloth:cloth-config-neoforge:${cloth_config_version}")
    compileOnly("com.terraformersmc:biolith-neoforge:${biolith_version}")

    // Sodium
    if (shouldRunSodium) {
        implementation("net.caffeinemc:sodium-neoforge-mod:${sodium_version}")
        implementation("net.caffeinemc:sodium-neoforge:${sodium_version}")
    } else {
        compileOnly("net.caffeinemc:sodium-neoforge-mod:${sodium_version}")
        compileOnly("net.caffeinemc:sodium-neoforge:${sodium_version}")
    }
}

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val applyLicenses: Task by tasks

tasks {
    license {
        if (licenseChecks) {
            rule(rootProject.file("codeformat/HEADER"))

            include("**/*.java")
        }
    }

    processResources {
        val properties = mapOf("mod_version" to getModVersion())
        inputs.properties(properties)
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(properties)
        }
    }

    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
        options.release = 25
        options.isFork = true
        options.isIncremental = true
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
val loaderVariants = setOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements")
configurations.all {
    if (name in loaderVariants) {
        attributes {
            attribute(loaderAttribute, "neoforge")
        }
    }
}
sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant ->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "neoforge")
            }
        }
    }
}

fun getModVersion(): String {
    return "$mod_version-mc$minecraft_version"
}
