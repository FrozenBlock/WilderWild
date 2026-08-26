import com.possible_triangle.gradle.settings.localRepository
import com.possible_triangle.gradle.settings.ResolutionStrategy

pluginManagement {
	repositories {
		mavenLocal()
        maven("https://maven.quiltmc.org/repository/release") {
            name = "Quilt"
        }
        maven("https://maven.fabricmc.net") {
            name = "Fabric"
        }
        maven("https://maven.neoforged.net/releases") {
            name = "NeoForged"
        }
        maven("https://jitpack.io") {
            name = "Jitpack"
        }
        maven("https://registry.somethingcatchy.net/repository/maven-releases/") { // Candlelight & Triangle
            name = "SomethingCatchy (MehVahdJukaar)"
        }
        maven("https://maven.frozenblock.net/snapshot") {
            name = "FrozenBlock Snapshot"
        }
		mavenCentral()
		gradlePluginPortal()
	}
}

val neoforgeSnapshotMaven = settings.providers.gradleProperty("neoforge_snapshot_maven").orNull
if (!neoforgeSnapshotMaven.isNullOrBlank()) {
    pluginManagement {
        repositories {
            maven(neoforgeSnapshotMaven) { name = "NeoForge Snapshots" }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("+")
    id("net.frozenblock.triangle.helper") version("+")
}

helper {
    versionStrategy = ResolutionStrategy.SNAPSHOT
}

rootProject.name = "Wilder Wild"

object Constants {
    const val FABRIC: Boolean = true
    const val NEOFORGE: Boolean = true
}

include("ww-common")
project(":ww-common").projectDir = file("common")

if (Constants.FABRIC) {
    include("ww-fabric")
    project(":ww-fabric").projectDir = file("fabric")
}

if (Constants.NEOFORGE) {
    include("ww-neoforge")
    project(":ww-neoforge").projectDir = file("neoforge")
}

localRepository("cloth-config", "me.shedaniel.cloth:cloth-config-fabric", enabled = false)
localRepository("SimpleCopperPipesMC", "maven.modrinth:simple-copper-pipes", enabled = false)

localRepository("FrozenLib",
    "net.frozenblock:frozenlib",
    prefix = "flib",
    multi = true,
    enabled = true
)

localPluginRepository(
    "GradleHelper",
    enabled = true
)

fun localPluginRepository(repo: String, enabled: Boolean = true) {
    if (!enabled) return
    println("Attempting to include local plugin build $repo")

    val github = System.getenv("GITHUB_ACTIONS") == "true"

    var path = "../$repo"
    var file = File(path)

    if (github) {
        path = repo
        file = File(path)
        println("Running on GitHub")
    }

    if (file.exists()) {
        pluginManagement {
            includeBuild(path)
        }
        println("Included local plugin build $repo")
    } else {
        println("Local plugin build $repo not found")
    }
}
