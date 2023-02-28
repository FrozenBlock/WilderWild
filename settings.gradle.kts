pluginManagement {
	repositories {
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

rootProject.name = "Wilder Wild"

localRepository("FrozenLib", "WilderWild", "FrozenLib-1.1.15-Fabric-1.19.2.jar", "maven.modrinth:frozenlib")

fun localRepository(repo: String, projectFileName: String, jarName: String, dependencySub: String) {
	println("Attempting to include local repo $repo")

	val allowLocalRepoUse = true
	val allowLocalRepoInConsoleMode = true

	val androidInjectedInvokedFromIde by extra("android.injected.invoked.from.ide")
	val xpcServiceName by extra("XPC_SERVICE_NAME")
	val ideaInitialDirectory by extra("IDEA_INITIAL_DIRECTORY")

	val isIDE = androidInjectedInvokedFromIde != "" || (System.getenv(xpcServiceName) ?: "").contains("intellij") || (System.getenv(xpcServiceName) ?: "").contains(".idea") || System.getenv(ideaInitialDirectory) != null
	val github = System.getenv("GITHUB_WORKSPACE") != ""

	var path = "../$repo"
    var file = File(path)

    val prefixedRepoName = ":$repo"

    if (allowLocalRepoUse && (isIDE || allowLocalRepoInConsoleMode)) {
		if (github) {
			path = System.getenv("GITHUB_WORKSPACE") + "/$repo"
			file = File(path)
			println("Running on GitHub")
		}
        if (file.exists()) {
			includeBuild(path) {
				//buildNeeded = !file("$path/build/libs/$jarName").exists()
				dependencySubstitution {
					if (dependencySub != null) {
						substitute(module(dependencySub)).using(project(":"))
					}
				}
			}
			println("Included local repo $repo")
        } else {
			println("Local repo $repo not found")
		}
	}
}
