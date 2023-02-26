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

localRepository("FrozenLib", "WilderWild")

val androidInjectedInvokedFromIde by extra("android.injected.invoked.from.ide")
val xpcServiceName by extra("XPC_SERVICE_NAME")
val ideaInitialDirectory by extra("IDEA_INITIAL_DIRECTORY")
val isIDE = !androidInjectedInvokedFromIde.equals(null) || (System.getenv(xpcServiceName) ?: "").contains("intellij") || (System.getenv(xpcServiceName) ?: "").contains(".idea") || System.getenv(ideaInitialDirectory) != null

fun localRepository(mod: String, projectFileName: String) {
	val allowLocalModUse = true
	val allowLocalModInConsoleMode = true

	val path = "../$mod"
    val file = File(path)

    val pathGitHub = "../$projectFileName/$mod"
    val fileGitHub = File(pathGitHub)

    val prefixedModName = ":$mod"

	if (allowLocalModUse && (isIDE || allowLocalModInConsoleMode) && file.exists()) {
		include(prefixedModName)
        project(prefixedModName).projectDir = file
        project(prefixedModName).buildFileName = "./build.gradle"
	} else if (allowLocalModUse && (isIDE || allowLocalModInConsoleMode) && fileGitHub.exists()) {
		include(prefixedModName)
        project(prefixedModName).projectDir = fileGitHub
        project(prefixedModName).buildFileName = "./build.gradle"
	}
}
