plugins {
    id("com.possible-triangle.neoforge")
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
    flatDir {
        dirs("libs")
    }
}

neoforge {
    dependOn(project(":ww-common"))
    accessWidener(project(":ww-common"))
    injectInterfaces(rootProject.file("common/src/main/resources/interfaces.json"))
}

neoForge {
    accessTransformers {} // Required for transitive AW to apply!
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

dependencies {
    api("net.frozenblock:frozenlib-neoforge:${frozenlib_version}")
    accessTransformers("net.frozenblock:frozenlib-neoforge:${frozenlib_version}") // Required for transitive AW to apply!

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

fun getModVersion(): String {
    return "$mod_version-mc$minecraft_version"
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}
