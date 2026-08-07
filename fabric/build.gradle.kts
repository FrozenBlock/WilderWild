plugins {
    id("net.frozenblock.triangle.fabric")
    id("org.quiltmc.gradle.licenser")
    checkstyle
}

checkstyle {
    configFile = rootProject.file("checkstyle.xml")
    toolVersion = "10.20.2"
}

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val fabric_loader_version: String by project
val min_fabric_loader_version: String by project

val mod_id: String by project
val mod_version: String by project
val minecraft_version: String by project
val protocol_version: String by project
val maven_group: String by project
val archives_base_name: String by project

val fabric_api_version: String by project
val frozenlib_version: String by project

val modmenu_version: String by project
val cloth_config_version: String by project
val copperpipes_version: String by project
val terrablender_version: String by project

val biolith_version: String by project
val run_biolith: String by project
val shouldRunBiolith = run_biolith == "true"

val sodium_version: String by project
val run_sodium: String by project
val shouldRunSodium = run_sodium == "true"

base {
    archivesName = archives_base_name
}

val release = findProperty("releaseType") == "stable"

version = getModVersion()
group = maven_group

tasks.jar {
    archiveClassifier.set("fabric")
}

fabric {
    dependOn(project(":ww-common"))
    accessWidener(project(":ww-common"))
    dataGen {
        owner = project(":ww-common")
        splitSourceSet("datagen")
    }
}

loom {
    enableTransitiveAccessWideners = true
    interfaceInjection {
        enableDependencyInterfaceInjection = true
    }
}

repositories {
    flatDir {
        dirs("libs")
    }
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
val loaderVariants = setOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements", "includeInternal", "modCompileClasspath")
configurations.all {
    if (name in loaderVariants) {
        attributes {
            attribute(loaderAttribute, "fabric")
        }
    }
}
sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant ->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "fabric")
            }
        }
    }
}

dependencies {
    implementation("net.fabricmc:fabric-loader:$fabric_loader_version")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabric_api_version")

    // FrozenLib
    api("net.frozenblock:frozenlib-fabric:${frozenlib_version}")

    // Simple Copper Pipes
    compileOnlyApi("maven.modrinth:simple-copper-pipes:${copperpipes_version}")

    // Mod Menu
    implementation("com.terraformersmc:modmenu:$modmenu_version")

    // Cloth Config
    implementation("me.shedaniel.cloth:cloth-config-fabric:$cloth_config_version") {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "com.terraformersmc")
    }

    // TerraBlender
    compileOnly("maven.modrinth:terrablender:${terrablender_version}")

    // Biolith
    if (shouldRunBiolith)
        implementation("com.terraformersmc:biolith-fabric:${biolith_version}")
    else
        compileOnly("com.terraformersmc:biolith-fabric:${biolith_version}")

    // Sodium
    if (shouldRunSodium)
        implementation("net.caffeinemc:sodium-fabric:${sodium_version}")
    else
        compileOnly("net.caffeinemc:sodium-fabric:${sodium_version}")
}

tasks {
    processResources {
        val properties = mapOf(
            "mod_id" to mod_id,
            "version" to version,
            "protocol_version" to protocol_version,
            "minecraft_version" to "~26.3-",

            "fabric_loader_version" to ">=$min_fabric_loader_version",
            "fabric_api_version" to ">=$fabric_api_version",
            "frozenlib_version" to ">=${frozenlib_version.split('-').firstOrNull()}-"
        )

        properties.forEach { (a, b) -> inputs.property(a, b) }

        filesNotMatching(
            listOf(
                "**/*.java",
                "**/sounds.json",
                "**/lang/*.json",
                "**/.cache/*",
                "**/*.accesswidener",
                "**/*.classtweaker",
                "**/*.cfg",
                "**/*.nbt",
                "**/*.png",
                "**/*.ogg",
                "**/*.mixins.json",
                "**/*.zip"
            )
        ) {
            expand(properties)
        }
    }

    license {
        if (licenseChecks) {
            rule(rootProject.file("codeformat/HEADER"))

            include("**/*.java")
        }
    }
}

val applyLicenses: Task by tasks
val test: Task by tasks
val runClient: Task by tasks

val sourcesJar: Jar by tasks
val javadocJar: Jar by tasks

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

fun getModVersion(): String {
    var version = "$mod_version-mc$minecraft_version"

    if (!release) {
        version += "-unstable"
    }

    return version
}

val changelogText = run {
    val split = rootProject.file("CHANGELOG.md").readText().split("-----------------")
    check(split.size == 2) { "Malformed changelog" }
    split[1].trim()
}

upload {
    maven {
        name.set("wilderwild-fabric")
    }

    forEach {
        changelog = changelogText
    }

    curseforge {
        dependencies {
            required("fabric-api")
            required("frozenlib")
            optional("modmenu")
            optional("cloth-config")
            optional("simple-copper-pipes")
            optional("trailier-tales")
            optional("glowtone")
            optional("the-copperier-age")
        }
    }

    modrinth {
        dependencies {
            required("fabric-api")
            required("frozenlib")
            optional("modmenu")
            optional("cloth-config")
            optional("simple-copper-pipes")
            optional("trailier-tales")
            optional("glowtone")
            optional("the-copperier-age")
        }
    }
}
