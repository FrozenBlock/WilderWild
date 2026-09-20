plugins {
    id("net.frozenblock.triangle.neoforge")
    id("org.quiltmc.gradle.licenser")
    checkstyle
}

checkstyle {
    configFile = rootProject.file("checkstyle.xml")
    toolVersion = "10.20.2"
}

val mod_id: String by project
val mod_version: String by project
val subproject_prefix: String by project
val minecraft_version: String by project
val maven_group: String by project
val archives_base_name: String by project

val frozenlib_version: String by project
val cloth_config_version: String by project
val terrablender_version_neoforge: String by project
val biolith_version: String by project

val sodium_version: String by project
val run_sodium: String by project
val shouldRunSodium = run_sodium == "true"

val iris_version: String by project
val run_iris: String by project
val shouldRunIris = run_iris == "true"

val neoforgeSnapshotMaven = findProperty("neoforge_snapshot_maven") as String?

base {
    archivesName.set(archives_base_name)
}

group = maven_group

tasks.jar {
    archiveClassifier.set("neoforge")
}

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
    dependOn(project(":$subproject_prefix-common"))
    accessWidener(project(":$subproject_prefix-common"))
}

neoForge {
    accessTransformers {} // Required for transitive AW to apply!
}

dependencies {
    // FrozenLib
    api("net.frozenblock:frozenlib-neoforge:$frozenlib_version")?.let {
        accessTransformers(it)
        interfaceInjectionData(it)
    }

    // Cloth Config
    implementation("me.shedaniel.cloth:cloth-config-neoforge:$cloth_config_version")

    // TerraBlender
    compileOnly("maven.modrinth:terrablender:$terrablender_version_neoforge")

    // Biolith
    compileOnly("com.terraformersmc:biolith-neoforge:$biolith_version")

    // Sodium
    if (shouldRunSodium) {
        implementation("net.caffeinemc:sodium-neoforge-mod:$sodium_version")
        implementation("net.caffeinemc:sodium-neoforge:$sodium_version")
    } else {
        compileOnly("net.caffeinemc:sodium-neoforge-mod:$sodium_version")
        compileOnly("net.caffeinemc:sodium-neoforge:$sodium_version")
    }

    // Iris
    if (shouldRunIris)
        implementation("maven.modrinth:iris:$iris_version-neoforge")
    else
        compileOnly("maven.modrinth:iris:$iris_version-neoforge")
}

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

tasks {
    license {
        if (licenseChecks) {
            rule(rootProject.file("codeformat/HEADER"))

            include("**/*.java")
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

val sourcesJar: Jar by tasks
val javadocJar: Jar by tasks

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

val changelogText = run {
    val split = rootProject.file("CHANGELOG.md").readText().split("-----------------")
    check(split.size == 2) { "Malformed changelog" }
    split[1].trim()
}

upload {
    maven {
        name.set("$mod_id-neoforge")
    }

    forEach {
        changelog.set(changelogText)
    }

    curseforge {
        dependencies {
            required("frozenlib")
            optional("cloth-config")
            optional("biolith")
            optional("simple-copper-pipes")
            optional("trailier-tales")
            optional("glowtone")
            optional("the-copperier-age")
        }
    }

    modrinth {
        dependencies {
            required("frozenlib")
            optional("cloth-config")
            optional("biolith")
            optional("simple-copper-pipes")
            optional("trailier-tales")
            optional("glowtone")
            optional("the-copperier-age")
        }
    }
}
