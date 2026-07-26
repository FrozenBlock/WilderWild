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
    candlelight = true,
    enabled = true
)

localPluginRepository(
    "GradleHelper",
    enabled = true
)

fun localRepository(
    repo: String,
    dependencySub: String,
    prefix: String = "",
    multi: Boolean = true,
    candlelight: Boolean = false,
    enabled: Boolean
) {
	if (!enabled) return
	println("Attempting to include local repo $repo")

	val github = System.getenv("GITHUB_ACTIONS") == "true"

	val allowLocalRepoUse = true
	val allowLocalRepoInConsoleMode = true

	val androidInjectedInvokedFromIde by extra("android.injected.invoked.from.ide")
	val xpcServiceName by extra("XPC_SERVICE_NAME")
	val ideaInitialDirectory by extra("IDEA_INITIAL_DIRECTORY")

	val isIDE = androidInjectedInvokedFromIde != "" || (System.getenv(xpcServiceName) ?: "").contains("intellij") || (System.getenv(xpcServiceName) ?: "").contains(".idea") || System.getenv(ideaInitialDirectory) != null

	var path = "../$repo"
	var file = File(path)

	val allSuffixes = mutableListOf("common")
	if (Constants.FABRIC) allSuffixes.add("fabric")
	if (Constants.NEOFORGE) allSuffixes.add("neoforge")

	if (allowLocalRepoUse && (isIDE || allowLocalRepoInConsoleMode)) {
		if (github) {
			path = repo
			file = File(path)
			println("Running on GitHub")
		}
		if (file.exists()) {
			includeBuild(path) {
				dependencySubstitution {
					if (multi && allSuffixes.isNotEmpty()) {
                        for (suffix in allSuffixes) {
                            val project = if (prefix.isNotEmpty()) ":$prefix-$suffix" else ":$suffix"
                            substitute(module("$dependencySub-$suffix")).using(project(project))
                        }
					} else {
                        val projectPath = if (dependencySub.isNotEmpty()) {
                            if (prefix.isNotEmpty()) ":$prefix-$dependencySub" else ":$dependencySub"
                        } else ":"
                        substitute(module(dependencySub)).using(project(projectPath))
					}
				}
			}

			if (multi && candlelight) {
				gradle.rootProject {
					subprojects {
						val suffix = allSuffixes.find { project.name.endsWith("-$it") }
						if (suffix != null) {
							afterEvaluate {
								tasks.findByName("compileJava")?.dependsOn(
									gradle.includedBuild(repo).task(":$prefix-$suffix:candleLightTransform")
								)
							}
						}
					}
				}
			}

			println("Included local repo $repo")
		} else {
			println("Local repo $repo not found")
		}
	}
}

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
