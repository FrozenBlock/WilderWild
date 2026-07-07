pluginManagement {
	repositories {
		mavenLocal()
		maven {
			name = "Quilt"
			setUrl("https://maven.quiltmc.org/repository/release/")
		}
		maven {
			name = "Quilt Snapshot"
			setUrl("https://maven.quiltmc.org/repository/snapshot/")
		}
		maven {
			name = "Fabric"
			setUrl("https://maven.fabricmc.net/")
		}
        maven("https://maven.architectury.dev/") {
            name = "Architectury"
        }
		maven {
			name = "NeoForged"
			setUrl("https://maven.neoforged.net/releases")
		}
		maven {
			name = "Forge"
			setUrl("https://files.minecraftforge.net/maven/")
		}
		maven {
			name = "Jitpack"
			setUrl("https://jitpack.io/")
		}
		mavenCentral()
		gradlePluginPortal()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "Wilder Wild"

includeBuild("build-logic")

object Constants {
    const val FABRIC: Boolean = true
    const val NEOFORGE: Boolean = false
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

localRepository("cloth-config", "me.shedaniel.cloth:cloth-config-fabric", kotlin = false, enabled = false)
localRepository("SimpleCopperPipesMC", "maven.modrinth:simple-copper-pipes", kotlin = false, enabled = false)

localRepository("FrozenLib", "net.frozenblock:frozenlib", prefix = "flib", multi = true, enabled = true)

fun localRepository(repo: String, dependencySub: String, prefix: String = "", multi: Boolean = true, kotlin: Boolean = true, enabled: Boolean) {
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

	if (allowLocalRepoUse && (isIDE || allowLocalRepoInConsoleMode)) {
		if (github) {
			path = repo
			file = File(path)
			println("Running on GitHub")
		}
		if (file.exists()) {
			includeBuild(path) {
				dependencySubstitution {
                    val allSuffixes = mutableListOf("common")
                    if (Constants.FABRIC) allSuffixes.add("fabric")
                    if (Constants.NEOFORGE) allSuffixes.add("neoforge")
					if (multi && allSuffixes.isNotEmpty()) {
                        for (suffix in allSuffixes) {
                            substitute(module("$dependencySub-$suffix")).using(project(":$prefix-$suffix"))
                        }
					} else {
                        val projectPath = if (dependencySub.isNotEmpty()) {
                            if (prefix.isNotEmpty()) ":$prefix-$dependencySub" else ":$dependencySub"
                        } else ":"
                        substitute(module(dependencySub)).using(project(projectPath))
					}
				}
			}
			println("Included local repo $repo")
		} else {
			println("Local repo $repo not found")
		}
	}
}
