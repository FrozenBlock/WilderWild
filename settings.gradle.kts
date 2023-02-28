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

val allowLocalModUse = true
val allowLocalModInConsoleMode = true

val androidInjectedInvokedFromIde by extra("android.injected.invoked.from.ide")
val xpcServiceName by extra("XPC_SERVICE_NAME")
val ideaInitialDirectory by extra("IDEA_INITIAL_DIRECTORY")

val isIDE = androidInjectedInvokedFromIde != "" || (System.getenv(xpcServiceName) ?: "").contains("intellij") || (System.getenv(xpcServiceName) ?: "").contains(".idea") || System.getenv(ideaInitialDirectory) != null
val github = System.getenv("GITHUB_WORKSPACE") != ""

fun localRepository(mod: String, projectFileName: String) {
	println("Attempting to include local mod $mod")
	var path = "../$mod"
    var file = File(path)

    val prefixedModName = ":$mod"

    if (allowLocalModUse && (isIDE || allowLocalModInConsoleMode)) {
		if (github) {
			path = System.getenv("GITHUB_WORKSPACE") + "/$mod"
			file = File(path)
			println("Running on GitHub")
		}
        if (file.exists()) {
			//includeBuild(path) {
			//	buildNeeded = !file("$path/build/libs/FrozenLib-1.1.14-Fabric-1.19.2.jar").exists()
			//}

            include(prefixedModName)
            project(prefixedModName).projectDir = file
            project(prefixedModName).buildFileName = "./build.gradle"
			println("Included local mod $mod")
        } else {
			println("Local mod $mod not found")
		}
	}
}
