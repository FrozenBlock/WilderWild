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

localRepository("FrozenLib", "maven.modrinth:frozenlib", true)


fun localRepository(repo: String, dependencySub: String, kotlin: Boolean) {
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

    val prefixedRepoName = ":$repo"

    if (allowLocalRepoUse && (isIDE || allowLocalRepoInConsoleMode)) {
        if (github) {
            path = repo
            file = File(path)
            println("Running on GitHub")
        }
        if (file.exists()) {
            /*includeBuild(path) {
                dependencySubstitution {
                    if (dependencySub != "") {
                        substitute(module(dependencySub)).using(project(":"))
                    }
                }
            }*/
            include(prefixedRepoName)
            project(prefixedRepoName).projectDir = file
            project(prefixedRepoName).buildFileName = "./build.gradle" + if (kotlin) ".kts" else ""
            println("Included local repo $repo")
        } else {
            println("Local repo $repo not found")
        }
    }
}
